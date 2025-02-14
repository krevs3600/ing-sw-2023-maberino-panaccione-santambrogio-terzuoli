package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.JoinErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ReloadGameErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ResumeGameErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.*;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.persistence.Storage;
import it.polimi.ingsw.observer_observable.Observer;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * <h1>Class ServerImplementation</h1>
 * The class {@link ServerImplementation} implements the {@link Server} interface and is used to communicate with the {@link Client}s.
 * It handles both RMI and Socket connections.
 * It is also used to keep track of the instances of {@link Game} that are currently playing as well as of
 * the players that are playing them, or that got disconnected
 *
 * @author Francesco Maberino, Francesco Santambrogio, Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/10/2023
 * @version 1.0
 */
public class ServerImplementation extends UnicastRemoteObject implements Server {

    private final Map<String,GameController> currentGames = new HashMap<>();
    private final Set<String> currentPlayersNicknames = new HashSet<>();
    private final Set<String> currentLobbyGameNames = new HashSet<>();
    private final Map<String, Client> connectedClients = new HashMap<>();
    private final Map<Client, GameController> playerGame = new HashMap<>();

    private final List<GameController> savedGames = new ArrayList<>();

    private final Map<String, GameController> disconnectedPlayersGame = new HashMap<>();

    /**
     * Constructor class
     * Default constructor
     */
    public ServerImplementation() throws RemoteException {
        super();
    }




    /**
     * This method is used to register a {@link Client} into the server. The {@link Client} be sent instances of {@link GameView}
     * as the game progresses, so it will be able to see the game and play it.
     * The method adds the {@link GameView} as {@link Observer} of the {@link Game}. Then, the {@link Client} is updated with
     * such {@link GameView}
     * @param client the {@link Client} to be added
     */
    @Override
    public void register(Client client) {
        Game game = playerGame.get(client).getGame();
        game.addObserver((obs, eventMessage) -> {
            try {
                if (eventMessage instanceof WinGameMessage) {
                    savedGames.remove(playerGame.get(client));
                    Storage storage = new Storage();
                    storage.delete();
                }

                int numDisconnectedPlayers = 0;
                for (Player player: game.getSubscribers()) {
                    if (disconnectedPlayersGame.containsKey(player.getName())) numDisconnectedPlayers++;
                }
                if (numDisconnectedPlayers==game.getSubscribers().size()) {
                    savedGames.remove(playerGame.get(client));
                    Storage storage = new Storage();
                    storage.delete();
                    currentGames.remove(game.getGameName());
                    for (Player player: game.getSubscribers()) {
                        disconnectedPlayersGame.remove(player.getName());
                    }
                    return;
                }

                if (disconnectedPlayersGame.containsKey(eventMessage.getNickname()) && (eventMessage instanceof PlayerTurnMessage)) {
                    System.out.println("Skipping the " + eventMessage.getNickname() + "'s turn");
                    if (!connectedClients.containsValue(client)) {
                        disconnectedPlayersGame.get(eventMessage.getNickname()).update(client, new EndTurnMessage(eventMessage.getNickname()));
                    }
                    return;
                }
                if (!connectedClients.containsValue(client)) {
                    return;
                }
                /*if (playerGame.get(client).equals(disconnectedPlayersGame.get(eventMessage.getNickname())))
                {
                    System.out.println("Skipping the " + eventMessage.getNickname() + "'s turn");
                    playerGame.get(client).update(client, new EndTurnMessage(eventMessage.getNickname()));
                }

                 */

                client.update(new GameView(game), eventMessage);
            } catch (IOException e) {
                System.err.println("Unable to update the client:" + e.getMessage() + " Skipping the update");
            }
        });
    }

    /**
     * The update method's purpose is to have the {@link ServerImplementation} react to every {@link EventMessage} it receives in the correct manner and to handle
     *  each correctly.
     * @param client from which it receives the {@link EventMessage}
     * @param eventMessage containing the action to be performed
     */
    @Override
    public void update(Client client, EventMessage eventMessage) throws IOException {

        switch (eventMessage.getType()) {
            //the client provides the nickname with which he/she will play the game. If the nickname is already taken,
            //the server asks the client to provide a new one
            case NICKNAME -> {
                if (!currentPlayersNicknames.contains(eventMessage.getNickname())) {                                                              //case CREATOR_NICKNAME -> {
                    currentPlayersNicknames.add(eventMessage.getNickname());                                                                      //    boolean validNickname = false;
                    getConnectedClients().put(eventMessage.getNickname(), client);                                                                     //    if (!currentPlayersNicknames.contains(eventMessage.getNickName())) {
                    new Thread(() -> {
                        try {
                            while (true) {
                                Thread.sleep(5000);
                                client.onMessage(new PingToClientMessage(eventMessage.getNickname()));
                                System.out.println("Ping sent to " + eventMessage.getNickname());
                            }
                        } catch (InterruptedException | IOException e) {
                            disconnectClient(client, eventMessage.getNickname());
                            for (Map.Entry<Client, GameController> entry : playerGame.entrySet()) {
                                if(entry.getValue().equals(disconnectedPlayersGame.get(eventMessage.getNickname()))) {
                                    try {
                                        entry.getKey().onMessage(new ClientDisconnectedMessage(eventMessage.getNickname()));
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            }
                            if (disconnectedPlayersGame.get(eventMessage.getNickname()).getGame().getCurrentPlayer().getName().equals(eventMessage.getNickname())) {
                                try {
                                    disconnectedPlayersGame.get(eventMessage.getNickname()).update(client, new EndTurnMessage(eventMessage.getNickname()));
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }).start();


                    try {
                        client.onMessage(new LoginResponseMessage(eventMessage.getNickname(), true));
                    } catch (RemoteException e) {
                        System.err.println("disconnection");
                    }
                }
                else {
                    try {
                        client.onMessage(new LoginResponseMessage(eventMessage.getNickname(), false));
                    } catch (RemoteException e) {
                        System.err.println("disconnection");
                    }
                }
            }

            //ping to check whether the client is still playing or it has disconnected
            case PING ->  System.out.println("Ping arrived from " + eventMessage.getNickname());


            case GAME_SPECS -> {
                GameSpecsMessage gameSpecsMessage = (GameSpecsMessage) eventMessage;
                boolean isValidGameName = false;
                boolean isValidNumOfPlayers = false;
                if (!currentLobbyGameNames.contains(gameSpecsMessage.getGameName()) && !currentGames.containsKey(gameSpecsMessage.getGameName())) {
                    isValidGameName = true;
                }
                if(gameSpecsMessage.getNumOfPlayers() > 1 && gameSpecsMessage.getNumOfPlayers() < 5){
                        isValidNumOfPlayers = true;
                }
                if (isValidGameName && isValidNumOfPlayers) {
                    currentLobbyGameNames.add(gameSpecsMessage.getGameName());
                    Game game = new Game(Arrays.stream(NumberOfPlayers.values()).filter(x -> x.getValue() == gameSpecsMessage.getNumOfPlayers()).toList().get(0), gameSpecsMessage.getGameName());
                    GameController gameController = new GameController(game);
                    playerGame.put(client, gameController);
                    register(client);
                    currentGames.put(gameSpecsMessage.getGameName(), gameController);
                    // first player is directly added
                    gameController.update(client, gameSpecsMessage);
                }
                try {
                client.onMessage(new GameSpecsResponseMessage(eventMessage.getNickname(), isValidGameName, isValidNumOfPlayers));
                } catch (RemoteException e) {
                    System.err.println("disconnection");
                }
            }

            //the client is requesting to join an already existing game. The server provides the client with a list of
            //available games
            case JOIN_GAME_REQUEST -> {
                Set<String> availableGames = new HashSet<>(currentLobbyGameNames);
                if (currentLobbyGameNames.isEmpty()) {
                    try {
                    client.onMessage(new JoinErrorMessage(eventMessage.getNickname(), "no available games in lobby"));
                    } catch (RemoteException e) {
                    System.err.println("disconnection");
                    }
                } else {
                    try  {
                    client.onMessage(new JoinGameResponseMessage(eventMessage.getNickname(), availableGames));
                    } catch (RemoteException e) {
                    System.err.println("disconnection");
                    }
                }
            }

            //the client is requesting to resume a game he was previously playing and from which it got disconnected
            case RESUME_GAME_REQUEST -> {
                if(disconnectedPlayersGame.containsKey(eventMessage.getNickname())) {
                    playerGame.put(client, disconnectedPlayersGame.get(eventMessage.getNickname()));
                    disconnectedPlayersGame.remove(eventMessage.getNickname());
                    register(client);
                    int numOfDisconnectedPlayers = 0;
                    for (Player player: playerGame.get(client).getGame().getSubscribers()) {
                        if (disconnectedPlayersGame.containsKey(player.getName())) {
                            numOfDisconnectedPlayers++;
                        }
                    }
                    if (numOfDisconnectedPlayers==playerGame.get(client).getGame().getSubscribers().size()-2) {
                        if (playerGame.get(client).getGame().getTurnPhase().equals(GamePhase.WAITING)) {
                            playerGame.get(client).update(client, new EndTurnMessage(eventMessage.getNickname()));
                        }
                        else client.onMessage(new ResumeGameResponseMessage(eventMessage.getNickname(), new GameView(playerGame.get(client).getGame())));
                    }
                    else client.onMessage(new ResumeGameResponseMessage(eventMessage.getNickname(), new GameView(playerGame.get(client).getGame())));
                }
                else client.onMessage(new ResumeGameErrorMessage(eventMessage.getNickname(), " there is no games to resume"));
            }

            case RELOAD_GAME_REQUEST -> {


                boolean validation = false;
                GameController savedGameController = null;
                for (GameController gameController : savedGames) {
                    for (Player player : gameController.getGame().getSubscribers()) {
                        if (player.getName().equals(eventMessage.getNickname())) {
                            validation = true;
                            savedGameController = gameController;
                            break;
                        }
                    }
                }

                if (savedGameController == null) {
                    Storage storage = new Storage();
                    savedGameController = storage.restore();
                    if (savedGameController != null) {
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
                    boolean allClientsReloaded = true;
                    int missingPlayers = 0;
                    for (Player checkPlayer : savedGameController.getGame().getSubscribers()) {
                        if (!getConnectedClients().containsKey(checkPlayer.getName())) {
                            allClientsReloaded = false;
                            missingPlayers++;
                        }
                    }
                    if (allClientsReloaded) {

                        savedGameController.update(client, new EndTurnMessage(eventMessage.getNickname()));
                    } else {
                        for (Player waitingPlayer : savedGameController.getGame().getSubscribers()) {
                            if (getConnectedClients().containsKey(waitingPlayer.getName())) {
                                try {
                                    getConnectedClients().get(waitingPlayer.getName()).onMessage(new WaitingResponseMessage(eventMessage.getNickname(), missingPlayers));
                                } catch (RemoteException e) {
                                    System.err.println("disconnection");
                                }
                            }
                        }
                    }
                } else {
                    try {
                        client.onMessage(new ReloadGameErrorMessage(eventMessage.getNickname(), "there is no game to reload"));
                    } catch (RemoteException e) {
                        System.err.println("disconnection");
                    }
                }
            }

            //the client selects the game in which it wants to be added from the list previously provided by  the
            //server. The server register the client and adds it to the game, updating the game controller
            case GAME_CHOICE -> {
                GameNameChoiceMessage gameNameChoiceMessage=(GameNameChoiceMessage) eventMessage;
                GameController gameController = currentGames.get(gameNameChoiceMessage.getGameChoice());
                playerGame.put(client, gameController);
                register(client);
                gameController.update(client,gameNameChoiceMessage);
                if (gameController.getGame().getSubscribers().size() < gameController.getGame().getNumberOfPlayers().getValue()-1){
                    try {
                    client.onMessage(new WaitingResponseMessage(eventMessage.getNickname(), gameController.getGame().getNumberOfPlayers().getValue()-gameController.getGame().getSubscribers().size()));
                    } catch (RemoteException e) {
                    System.err.println("disconnection");
                    }
                }
                for (Player player : gameController.getGame().getSubscribers()){
                    // notify other clients that a new player has joined the game
                    if (!player.getName().equals(eventMessage.getNickname())){
                        try {
                        getConnectedClients().get(player.getName()).onMessage(new PlayerJoinedLobbyMessage(eventMessage.getNickname()));
                        } catch (RemoteException e) {
                        System.err.println("disconnection");
                        }
                    }

                    if (gameController.getGame().getSubscribers().size() < gameController.getGame().getNumberOfPlayers().getValue()){
                        try {
                        getConnectedClients().get(player.getName()).onMessage(new WaitingResponseMessage(eventMessage.getNickname(), gameController.getGame().getNumberOfPlayers().getValue()-gameController.getGame().getSubscribers().size()));
                        } catch (RemoteException e) {
                        System.err.println("disconnection");
                        }
                    }
                }

                if (gameController.getGame().getSubscribers().size() == gameController.getGame().getNumberOfPlayers().getValue()) {
                    removeGameFromLobby(gameNameChoiceMessage.getGameChoice());
                    gameController.update(client, new StartGameMessage(eventMessage.getNickname()));
                }
            }

            //the client signals it has finished the turn. The server communicates it to the correct game controller
            case END_TURN -> {
                int numOfDisconnectedPlayers = 0;
                for (Player player: playerGame.get(client).getGame().getSubscribers()) {
                    if (disconnectedPlayersGame.containsKey(player.getName())) {
                        numOfDisconnectedPlayers++;
                    }
                }
                if (numOfDisconnectedPlayers==playerGame.get(client).getGame().getSubscribers().size()-1) {
                    playerGame.get(client).getGame().setTurnPhase(GamePhase.WAITING);
                    client.onMessage(new WaitingForOtherPlayersMessage(eventMessage.getNickname()));
                }
                else playerGame.get(client).update(client, eventMessage);
            }

            //a client wants out of the game
            case EXIT -> {
                int remainingPlayers = 0;
                for(Map.Entry<Client, GameController> entry : playerGame.entrySet()) {
                    if (entry.getValue().equals(playerGame.get(client))) {
                        remainingPlayers++;
                    }
                }
                if (remainingPlayers==1) currentGames.remove(playerGame.get(client).getGame().getGameName());
                connectedClients.remove(eventMessage.getNickname(), client);
                playerGame.remove(client);

            }

            //a client has disconnected from the server, which cannot reach it any longer
            case DISCONNECT_CLIENT -> currentPlayersNicknames.remove(eventMessage.getNickname());

            //prendo il controller associato al client e su di questo chiamo l'update passando
            //the game controller related to the cliend is updted with the message the client is sending
            default -> playerGame.get(client).update(client, eventMessage);
        }
    }

    /**
     * This method removes a {@link Game} name from the lobby
     * @param gameName that is needed to be removed
     */
    @Override
    public void removeGameFromLobby(String gameName) {
        this.currentLobbyGameNames.remove(gameName);
    }



    /**
     * Getter method
     * @return a containing the name associated to the clients as key and the {@link Client} object as value
     */
    public Map<String, Client> getConnectedClients() {
        return connectedClients;
    }

    /**
     * This method is used to disconnect a {@link Client} from a game
     * @param client to be removed
     * @param nickname of the {@link Client} to be removed
     */
    public void disconnectClient(Client client, String nickname) {
        System.err.println("client " + nickname + " disconnected");
        currentPlayersNicknames.remove(nickname);
        getConnectedClients().remove(nickname);
        if (playerGame.containsKey(client)) {
            disconnectedPlayersGame.put(nickname, playerGame.get(client));
            /*int i = 0;
            for (; i < playerGame.get(client).getGame().getSubscribers().size(); i++) {
                if (playerGame.get(client).getGame().getSubscribers().get(i).getName().equals(nickname)) {
                    break;
                }
            }
            playerGame.get(client).getGame().deleteObserver(playerGame.get(client).getGame().getObservers().get(i));

             */
            String gameName = playerGame.get(client).getGame().getGameName();
            GameController controller = currentGames.get(gameName);
            Game game = controller.getGame();
            playerGame.remove(client);


            // if game has not started yet
            if (currentLobbyGameNames.contains(gameName)) {

                for (Map.Entry<Client, GameController> entry : playerGame.entrySet()) {
                    if (entry.getValue().equals(controller)) {
                        int missingPlayers = game.getNumberOfPlayers().getValue() - game.getSubscribers().size();
                        try {
                            entry.getKey().onMessage(new WaitingResponseMessage(nickname, missingPlayers));
                        } catch (IOException e) {
                            System.err.println("disconnection");
                        }
                    }

                }
            }


            for (Map.Entry<Client, GameController> entry : playerGame.entrySet()) {
                if (entry.getValue().equals(disconnectedPlayersGame.get(nickname))) {
                    try {
                        entry.getKey().onMessage(new ClientDisconnectedMessage(nickname));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            if (disconnectedPlayersGame.get(nickname).getGame().getCurrentPlayer().getName().equals(nickname)) {
                try {
                    disconnectedPlayersGame.get(nickname).update(client, new EndTurnMessage(nickname));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}