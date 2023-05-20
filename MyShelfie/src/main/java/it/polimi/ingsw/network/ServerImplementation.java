package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.network.MessagesToServer.errorMessages.JoinErrorMessage;
import it.polimi.ingsw.network.MessagesToServer.requestMessage.GameCreationResponseMessage;
import it.polimi.ingsw.network.MessagesToServer.requestMessage.GameNameResponseMessage;
import it.polimi.ingsw.network.MessagesToServer.requestMessage.JoinGameResponseMessage;
import it.polimi.ingsw.network.MessagesToServer.requestMessage.LoginResponseMessage;
import it.polimi.ingsw.network.eventMessages.*;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImplementation extends UnicastRemoteObject implements Server {

   // private Map<GameController, Game> currentGames = new HashMap<>();


    private Map<String,GameController> currentGames = new HashMap<>();
    private Set<String> currentPlayersNicknames = new HashSet<>();
    private Set<String> currentLobbyGameNames = new HashSet<>();
    private Map<String, Client> connectedClients = new HashMap<>();
    private Map<Client, GameController> playerGame = new HashMap<>();

    public ServerImplementation() throws RemoteException {
        super();
    }

    public ServerImplementation(int port) throws RemoteException {
        super(port);
    }

    public ServerImplementation(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    //TODO gestione di più client
    public void register(Client client) {
        Game game = playerGame.get(client).getGame();
        game.addObserver((observer, eventMessage) -> {
            try {
                client.update(new GameView(game), (EventMessage) eventMessage);
            } catch (RemoteException e) {
                System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update");
            }
        });
    }

    @Override
    public void update(Client client, EventMessage eventMessage) throws RemoteException {

        switch (eventMessage.getType()) {
            case NICKNAME -> {
                if (!currentPlayersNicknames.contains(eventMessage.getNickname())) {                                                              //case CREATOR_NICKNAME -> {
                    currentPlayersNicknames.add(eventMessage.getNickname());                                                                      //    boolean validNickname = false;
                    connectedClients.put(eventMessage.getNickname(), client);                                                                     //    if (!currentPlayersNicknames.contains(eventMessage.getNickName())) {
                    //  Set<String> availableGames = new HashSet<>(currentLobbyGameNames);                                                            //        validNickname = true;
                    //  if(currentLobbyGameNames.size()==0){                                                                                          //        currentPlayersNicknames.add(eventMessage.getNickName());
                    //      client.onMessage(new LoginResponseMessage(true,false, eventMessage.getNickName()));                                       //        connectedClients.put(eventMessage.getNickName(), client);
                    //  }                                                                                                                             //    }
                    client.onMessage(new LoginResponseMessage(true, eventMessage.getNickname()));                       //    if (validNickname) {
                }                                                                                                                                 //        client.onMessage(new CreatorLoginResponseMessage(eventMessage.getNickName(), validNickname));
                else                                                                                                                              //    } else
                    client.onMessage(new LoginResponseMessage(false));                                                                            //        client.onMessage(new CreatorLoginResponseMessage(validNickname));
            }                                                                                                                                     //}
            //this.gameController.update(client, eventMessage);
            case GAME_NAME -> {
                GameNameMessage gameNameMessage = (GameNameMessage) eventMessage;
                // da capire cosa modificare
                if (!currentLobbyGameNames.contains(gameNameMessage.getGameName()) && !currentGames.containsKey(gameNameMessage.getGameName())) {
                    currentLobbyGameNames.add(gameNameMessage.getGameName());
                    client.onMessage(new GameNameResponseMessage(gameNameMessage.getGameName(), true));
                } else {
                    client.onMessage(new GameNameResponseMessage(false));
                }
            }
            case GAME_CREATION -> {
                GameCreationMessage gameCreationMessage = (GameCreationMessage) eventMessage;
                boolean isValid = false;
                if (gameCreationMessage.getNumOfPlayers() > 1 && gameCreationMessage.getNumOfPlayers() < 5) {
                    Game game = new Game(Arrays.stream(NumberOfPlayers.values()).filter(x -> x.getValue() == gameCreationMessage.getNumOfPlayers()).toList().get(0), gameCreationMessage.getGameName());
                    GameController gameController = new GameController(this, game);
                    playerGame.put(client, gameController);
                    register(client);
                    currentGames.put(gameCreationMessage.getGameName(), gameController);
                    // first player is directly added
                    gameController.getClients().add(client);
                    gameController.update(client, gameCreationMessage);
                    isValid = true;
                }
                client.onMessage(new GameCreationResponseMessage(isValid));
            }

            case JOIN_GAME_REQUEST -> {
                JoinGameMessage joinGameMessage = (JoinGameMessage) eventMessage;
                Set<String> availableGames = new HashSet<>(currentLobbyGameNames);
                if (currentLobbyGameNames.isEmpty()) {
                    client.onMessage(new JoinErrorMessage(joinGameMessage.getNickname(), "no available games in lobby"));
                } else {
                    client.onMessage(new JoinGameResponseMessage(true, availableGames));

                }
            }
            // TODO check if game has been created
            case GAME_CHOICE -> {
                GameNameChoiceMessage gameNameChoiceMessage=(GameNameChoiceMessage) eventMessage;
                playerGame.put(client, currentGames.get(gameNameChoiceMessage.getGameChoice()));
                register(client);
                currentGames.get(gameNameChoiceMessage.getGameChoice()).getClients().add(client);
                currentGames.get(gameNameChoiceMessage.getGameChoice()).update(client,gameNameChoiceMessage);
            }

            case TILE_POSITION, BOOKSHELF_COLUMN, ITEM_TILE_INDEX, END_TURN, FILL_BOOSHELF -> {
                playerGame.get(client).update(client, eventMessage);
            }
        }
    }

    public void removeGameFromLobby(String gameName) {
        this.currentLobbyGameNames.remove(gameName);
    }

    public Map<String, Client> getConnectedClients() {
        return connectedClients;
    }
}


