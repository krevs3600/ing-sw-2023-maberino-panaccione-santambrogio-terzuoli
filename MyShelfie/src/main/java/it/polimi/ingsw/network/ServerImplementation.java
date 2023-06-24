package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.JoinErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ResumeGameErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.*;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.persistence.Storage;
import it.polimi.ingsw.view.cli.CLI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImplementation extends UnicastRemoteObject implements Server {

   // private Map<GameController, Game> currentGames = new HashMap<>();


    private final Map<String,GameController> currentGames = new HashMap<>();
    private final Set<String> currentPlayersNicknames = new HashSet<>();
    private final Set<String> currentLobbyGameNames = new HashSet<>();
    private final Map<String, Client> connectedClients = new HashMap<>();
    private final Map<Client, GameController> playerGame = new HashMap<>();

    private final List<GameController> savedGames = new ArrayList<>();

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
                if (eventMessage instanceof WinGameMessage) {
                    Storage storage = new Storage();
                    storage.delete();
                }
                client.update(new GameView(game), (EventMessage) eventMessage);
            } catch (RemoteException e) {
                System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update");
            }
        });
    }

    @Override
    public synchronized void update(Client client, EventMessage eventMessage) throws IOException {

        switch (eventMessage.getType()) {
            case NICKNAME -> {
                if (!currentPlayersNicknames.contains(eventMessage.getNickname())) {                                                              //case CREATOR_NICKNAME -> {
                    currentPlayersNicknames.add(eventMessage.getNickname());                                                                      //    boolean validNickname = false;
                    getConnectedClients().put(eventMessage.getNickname(), client);                                                                     //    if (!currentPlayersNicknames.contains(eventMessage.getNickName())) {
                    //  Set<String> availableGames = new HashSet<>(currentLobbyGameNames);                                                            //        validNickname = true;
                    //  if(currentLobbyGameNames.size()==0){                                                                                          //        currentPlayersNicknames.add(eventMessage.getNickName());
                    //      client.onMessage(new LoginResponseMessage(true,false, eventMessage.getNickName()));                                       //        connectedClients.put(eventMessage.getNickName(), client);
                    //  }
                    new Thread()
                    {
                        public void run() {
                            try {
                                while (true) {
                                    client.onMessage(new PingToClientMessage(eventMessage.getNickname()));
                                    System.out.println("Ti mando un bacino");
                                    Thread.sleep(5000);
                                }
                            } catch (RuntimeException | InterruptedException | IOException e) {
                                System.err.println("il bro sputa fatti");
                            }
                        }
                    }.start();
                    client.onMessage(new LoginResponseMessage(eventMessage.getNickname(), true));
                }                                                                                                                                 //        client.onMessage(new CreatorLoginResponseMessage(eventMessage.getNickName(), validNickname));
                else                                                                                                                              //    } else
                    client.onMessage(new LoginResponseMessage(eventMessage.getNickname(), false));                                                                            //        client.onMessage(new CreatorLoginResponseMessage(validNickname));
            }                                                                                                                                     //}
            //this.gameController.update(client, eventMessage);
            case GAME_NAME -> {
                GameNameMessage gameNameMessage = (GameNameMessage) eventMessage;
                // da capire cosa modificare
                if (!currentLobbyGameNames.contains(gameNameMessage.getGameName()) && !currentGames.containsKey(gameNameMessage.getGameName())) {
                    currentLobbyGameNames.add(gameNameMessage.getGameName());
                    client.onMessage(new GameNameResponseMessage(gameNameMessage.getNickname(),gameNameMessage.getGameName(), true));
                } else {
                    client.onMessage(new GameNameResponseMessage(eventMessage.getNickname(), gameNameMessage.getGameName(), false));
                }
            }
            case GAME_CREATION -> {
                GameCreationMessage gameCreationMessage = (GameCreationMessage) eventMessage;
                boolean isValid = false;
                if (gameCreationMessage.getNumOfPlayers() > 1 && gameCreationMessage.getNumOfPlayers() < 5) {
                    Game game = new Game(Arrays.stream(NumberOfPlayers.values()).filter(x -> x.getValue() == gameCreationMessage.getNumOfPlayers()).toList().get(0), gameCreationMessage.getGameName());
                    GameController gameController = new GameController(game);
                    playerGame.put(client, gameController);
                    register(client);
                    currentGames.put(gameCreationMessage.getGameName(), gameController);
                    // first player is directly added
                    gameController.update(client, gameCreationMessage);
                    isValid = true;
                }
                client.onMessage(new GameCreationResponseMessage(eventMessage.getNickname(), isValid));
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
                        GameController gameController = new GameController(game);
                        playerGame.put(client, gameController);
                        register(client);
                        currentGames.put(gameSpecsMessage.getGameName(), gameController);
                        // first player is directly added
                        gameController.update(client, gameSpecsMessage);
                        isValidNumOfPlayers = true;
                    }
                }
                client.onMessage(new GameSpecsResponseMessage(eventMessage.getNickname(), isValidGameName, isValidNumOfPlayers));
            }

            case JOIN_GAME_REQUEST -> {
                Set<String> availableGames = new HashSet<>(currentLobbyGameNames);
                if (currentLobbyGameNames.isEmpty()) {
                    client.onMessage(new JoinErrorMessage(eventMessage.getNickname(), "no available games in lobby"));
                } else {
                    client.onMessage(new JoinGameResponseMessage(eventMessage.getNickname(), availableGames));

                }
            }

            case RESUME_GAME_REQUEST -> {
                boolean validation = false;
                GameController savedGameController = null;
                for (GameController gameController: savedGames) {
                    for (Player player: gameController.getGame().getSubscribers()) {
                        if (player.getName().equals(eventMessage.getNickname())) {
                            validation = true;
                            savedGameController = gameController;
                            break;
                        }
                    }
                }

                if(savedGameController==null) {
                    Storage storage = new Storage();
                    savedGameController = storage.restore();
                    if(savedGameController!=null) {
                        savedGames.add(savedGameController);
                        for (Player player : savedGameController.getGame().getSubscribers()) {
                            if (player.getName().equals(eventMessage.getNickname())) {
                                validation = true;
                                break;
                            }
                        }
                    }
                }
                if (validation) {

                    if (!currentGames.containsKey(savedGameController.getGame().getGameName())) {
                        currentGames.put(savedGameController.getGame().getGameName(), savedGameController);
                    }
                    if (!playerGame.containsKey(client)) {
                        playerGame.put(client, savedGameController);
                        register(client);
                    }
                    boolean allClientsResumed = true;
                    int missingPlayers = 0;
                    for (Player checkPlayer : savedGameController.getGame().getSubscribers()) {
                        if (!getConnectedClients().containsKey(checkPlayer.getName())) {
                            allClientsResumed = false;
                            missingPlayers++;
                        }
                    }
                    if (allClientsResumed) {

                        savedGameController.update(client, new EndTurnMessage(eventMessage.getNickname()));
                    } else {
                        for (Player waitingPlayer : savedGameController.getGame().getSubscribers()) {
                            if (getConnectedClients().containsKey(waitingPlayer.getName())) {
                                getConnectedClients().get(waitingPlayer.getName()).onMessage(new WaitingResponseMessage(eventMessage.getNickname(), missingPlayers));
                            }
                        }
                    }
                }
                else {
                    client.onMessage(new ResumeGameErrorMessage(eventMessage.getNickname(), "there is no game to resume"));
                }
            }
            // TODO check if game has been created
            case GAME_CHOICE -> {
                GameNameChoiceMessage gameNameChoiceMessage=(GameNameChoiceMessage) eventMessage;
                GameController gameController = currentGames.get(gameNameChoiceMessage.getGameChoice());
                playerGame.put(client, gameController);
                register(client);
                gameController.update(client,gameNameChoiceMessage);
                if (gameController.getGame().getSubscribers().size() < gameController.getGame().getNumberOfPlayers().getValue()-1){
                    client.onMessage(new WaitingResponseMessage(eventMessage.getNickname(), gameController.getGame().getNumberOfPlayers().getValue()-gameController.getGame().getSubscribers().size()));
                }
                for (Player player : gameController.getGame().getSubscribers()){
                    // notify other clients that a new player has joined the game
                    if (!player.getName().equals(eventMessage.getNickname())){
                        getConnectedClients().get(player.getName()).onMessage(new PlayerJoinedLobbyMessage(eventMessage.getNickname()));
                    }

                    if (gameController.getGame().getSubscribers().size() < gameController.getGame().getNumberOfPlayers().getValue()){
                        getConnectedClients().get(player.getName()).onMessage(new WaitingResponseMessage(eventMessage.getNickname(), gameController.getGame().getNumberOfPlayers().getValue()-gameController.getGame().getSubscribers().size()));

                    }
                }

                if (gameController.getGame().getSubscribers().size() == gameController.getGame().getNumberOfPlayers().getValue()) {
                    removeGameFromLobby(gameNameChoiceMessage.getGameChoice());
                    gameController.update(client, new StartGameMessage(eventMessage.getNickname()));
                }
            }

            case TILE_POSITION, BOOKSHELF_COLUMN, ITEM_TILE_INDEX, FILL_BOOKSHELF, SWITCH_PHASE, END_TURN -> // il client e l'event message che non deve essere castato ed il tipo di messaggio che
                // mando è di tipo TilePack message
                // quindi player turn--> update del client che chiama l'update del server che passa per il game controller il quale poi rinvia direttamente
                // il messaggio al client.
                    playerGame.get(client).update(client, eventMessage); //prendo il controller associato al client e su di questo chiamo l'update passando

            case DISCONNECT_CLIENT -> {
                // if client is not in any game
                if (!playerGame.containsKey(client)){
                    currentPlayersNicknames.remove(eventMessage.getNickname());
                    getConnectedClients().remove(eventMessage.getNickname());
                } else {
                    String gameName = playerGame.get(client).getGame().getGameName();
                    GameController controller = currentGames.get(gameName);
                    Game game = controller.getGame();
                    playerGame.remove(client);


                    // if game has not started yet
                    if (currentLobbyGameNames.contains(gameName)){
                        currentPlayersNicknames.remove(eventMessage.getNickname());
                        getConnectedClients().remove(eventMessage.getNickname());
                        Player unsubscribed = game.getSubscribers().stream().filter(x->x.getName().equals(eventMessage.getNickname())).toList().get(0);
                        game.getSubscribers().remove(unsubscribed);

                        for (Map.Entry<Client, GameController> entry : playerGame.entrySet()){
                            if (entry.getValue().equals(controller)){
                                // todo: maybe add game.unsubscribe(String nickname)
                                entry.getKey().onMessage(new PlayerOfflineMessage(eventMessage.getNickname()));
                                int missingPlayers = game.getNumberOfPlayers().getValue()-game.getSubscribers().size();
                                entry.getKey().onMessage(new WaitingResponseMessage(eventMessage.getNickname(), missingPlayers));
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
                            getConnectedClients().remove(player.getName());
                        }
                    }
                }
                client.onMessage(new DisconnectionResponseMessage(eventMessage.getNickname()));
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