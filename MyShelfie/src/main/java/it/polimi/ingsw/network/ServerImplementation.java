package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.JoinErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.*;
import it.polimi.ingsw.network.eventMessages.*;

import java.io.IOException;
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
    public void register(Client client) {
        Game game = playerGame.get(client).getGame();
        game.addObserver((observer, eventMessage) -> {
            try {
                /*if (eventMessage instanceof WinGameMessage) {
                    for (Map.Entry<Client, GameController> entry : playerGame.entrySet()) {
                        if (client.equals(entry.getKey())) {
                            playerGame.remove(entry.getKey(), entry.getValue());
                            currentGames.remove(game.getGameName(), entry.getValue());
                        }
                    }
                }

                 */
                client.update(new GameView(game), (EventMessage) eventMessage);
            } catch (RemoteException e) {
                System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update");
            }
        });
    }

    @Override
    public void update(Client client, EventMessage eventMessage) throws IOException {

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

            case GAME_SPECS -> {
                GameSpecsMessage gameSpecsMessage = (GameSpecsMessage) eventMessage;
                boolean isValidGameName = false;
                boolean isValidNumOfPlayers = false;
                if (!currentLobbyGameNames.contains(gameSpecsMessage.getGameName()) && !currentGames.containsKey(gameSpecsMessage.getGameName())) {
                    currentLobbyGameNames.add(gameSpecsMessage.getGameName());
                    isValidGameName = true;
                    if(gameSpecsMessage.getNumOfPlayers() > 1 && gameSpecsMessage.getNumOfPlayers() < 5){
                        Game game = new Game(Arrays.stream(NumberOfPlayers.values()).filter(x -> x.getValue() == gameSpecsMessage.getNumOfPlayers()).toList().get(0), gameSpecsMessage.getGameName());
                        GameController gameController = new GameController(this, game);
                        playerGame.put(client, gameController);
                        register(client);
                        currentGames.put(gameSpecsMessage.getGameName(), gameController);
                        // first player is directly added
                        gameController.getClients().add(client);
                        gameController.update(client, gameSpecsMessage);
                        isValidNumOfPlayers = true;
                    }
                }
                client.onMessage(new GameSpecsResponseMessage(isValidGameName, isValidNumOfPlayers));
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

            case TILE_POSITION, BOOKSHELF_COLUMN, ITEM_TILE_INDEX, END_TURN, FILL_BOOKSHELF, SWITCH_PHASE -> {
                playerGame.get(client).update(client, eventMessage); //prendo il controller associato al client e su di questo chiamo l'update passando
                                                                    // il client e l'event message che non deve essere castato ed il tipo di messaggio che
                                                                    // mando è di tipo TilePack message
                // quindi player turn--> update del client che chiama l'update del server che passa per il game controller il quale poi rinvia direttamente
                // il messaggio al client.
            }

            case DISCONNECT_CLIENT -> {
                DisconnectClientMessage disconnectMessage = (DisconnectClientMessage) eventMessage;
                // if client is not in any game
                if (!playerGame.containsKey(client)){
                    currentPlayersNicknames.remove(eventMessage.getNickname());
                    connectedClients.remove(eventMessage.getNickname());
                } else {
                    playerGame.get(client).getClients().remove(client);
                    String gameName = playerGame.get(client).getGame().getGameName();
                    GameController controller = currentGames.get(gameName);
                    Game game = controller.getGame();
                    playerGame.remove(client);


                    // if game has not started yet
                    if (currentLobbyGameNames.contains(gameName)){
                        currentPlayersNicknames.remove(eventMessage.getNickname());
                        connectedClients.remove(eventMessage.getNickname());
                        Player unsubscribed = game.getSubscribers().stream().filter(x->x.getName().equals(eventMessage.getNickname())).toList().get(0);
                        game.getSubscribers().remove(unsubscribed);

                        for (Map.Entry<Client, GameController> entry : playerGame.entrySet()){
                            if (entry.getValue().equals(controller)){
                                // todo: maybe add game.unsubscribe(String nickname)
                                entry.getKey().onMessage(new PlayerOfflineMessage(eventMessage.getNickname()));
                                int missingPlayers = game.getNumberOfPlayers().getValue()-game.getSubscribers().size();
                                entry.getKey().onMessage(new WaitingResponseMessage(missingPlayers));
                                }
                            }

                    } else {
                        // kill game
                        currentGames.remove(game.getGameName());

                            for (Map.Entry<Client, GameController> entry : playerGame.entrySet()){
                                if (entry.getValue().equals(controller)){
                                    // todo: maybe add game.unsubscribe(String nickname)
                                    Client c = entry.getKey();
                                    if (!entry.getKey().equals(client)) {

                                        c.onMessage(new PlayerOfflineMessage(eventMessage.getNickname()));
                                        c.onMessage(new KillGameMessage(eventMessage.getNickname()));
                                    }
                                    playerGame.remove(c);
                                }

                        }
                        for (Player player : game.getSubscribers()){
                            connectedClients.remove(player.getName());
                        }
                    }
                }
                client.onMessage(new DisconnectionResponseMessage());
            }
        }
    }
    @Override
    public void removeGameFromLobby(String gameName) {
        this.currentLobbyGameNames.remove(gameName);
    }

    @Override
    public void removeClient(Client client){

    }
    public Map<String, Client> getConnectedClients() {
        return connectedClients;
    }
}


/* todo disconnessioni durante la partita da rivedere assolutamente
// ancora in lobby, in match
if (playerGame != null) {
    if (playerGame.get(client) != null) {
        GameController controller = playerGame.get(client);
        // remove game name from set
        currentGames.remove(controller.getGame().getGameName());
        // let's notify the players
        for (Client player : connectedClients.values()) {
            player.onMessage(new PlayerOfflineMessage(eventMessage.getNickname()));
            player.onMessage(new KillGameMessage(eventMessage.getNickname()));
        }
        // remove <client, controller> in the game of the disconnected player and clients from map
        for (Player player : controller.getGame().getSubscribers()) {
            playerGame.remove(connectedClients.get(player.getName()));
            connectedClients.remove(player.getName());
        }

    } else {
        currentPlayersNicknames.remove(eventMessage.getNickname());
    }
}
 */