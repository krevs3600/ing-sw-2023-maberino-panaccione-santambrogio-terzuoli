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
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImplementation extends UnicastRemoteObject implements Server {

    private final Map<String,GameController> currentGames = new HashMap<>();
    private final Set<String> currentPlayersNicknames = new HashSet<>();
    private final Set<String> currentLobbyGameNames = new HashSet<>();
    private final Map<String, Client> connectedClients = new HashMap<>();
    private final Map<Client, GameController> playerGame = new HashMap<>();

    private final List<GameController> savedGames = new ArrayList<>();

    private final Map<String, GameController> disconnectedPlayersGame = new HashMap<>();

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
        game.addObserver((obs, eventMessage) -> {
            try {
                if (eventMessage instanceof WinGameMessage) {
                    savedGames.remove(playerGame.get(client));
                    Storage storage = new Storage();
                    storage.delete();
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

    @Override
    public void update(Client client, EventMessage eventMessage) throws IOException {

        switch (eventMessage.getType()) {
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

            case PING -> {
                disconnectedPlayersGame.remove(eventMessage.getNickname(), playerGame.get(client));
                System.out.println("Ping arrived from " + eventMessage.getNickname());
            }

            case GAME_NAME -> {
                GameNameMessage gameNameMessage = (GameNameMessage) eventMessage;
                // da capire cosa modificare
                if (!currentLobbyGameNames.contains(gameNameMessage.getGameName()) && !currentGames.containsKey(gameNameMessage.getGameName())) {
                    currentLobbyGameNames.add(gameNameMessage.getGameName());
                    try {
                    client.onMessage(new GameNameResponseMessage(gameNameMessage.getNickname(),gameNameMessage.getGameName(), true));
                    } catch (RemoteException e) {
                    System.err.println("disconnection");
                    }
                } else {
                    try {
                    client.onMessage(new GameNameResponseMessage(eventMessage.getNickname(), gameNameMessage.getGameName(), false));
                } catch (RemoteException e) {
                        System.err.println("disconnection");
                    }
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
                try {
                client.onMessage(new GameCreationResponseMessage(eventMessage.getNickname(), isValid));
                } catch (RemoteException e) {
                    System.err.println("disconnection");
                }
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
                try {
                client.onMessage(new GameSpecsResponseMessage(eventMessage.getNickname(), isValidGameName, isValidNumOfPlayers));
                } catch (RemoteException e) {
                    System.err.println("disconnection");
                }
            }

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
            // TODO check if game has been created
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
            case DISCONNECT_CLIENT -> {
                currentPlayersNicknames.remove(eventMessage.getNickname());
            }

            default -> playerGame.get(client).update(client, eventMessage); //prendo il controller associato al client e su di questo chiamo l'update passando
        }
    }

            /*else {
                // kill game
                currentGames.remove(game.getGameName());

                for (Map.Entry<Client, GameController> entry : playerGame.entrySet()) {
                    if (entry.getValue().equals(controller)) {
                        // todo: maybe add game.unsubscribe(String nickname)
                        Client c = entry.getKey();
                        if (!entry.getKey().equals(client)) {

                            c.onMessage(new PlayerOfflineMessage(eventMessage.getNickname()));
                            c.onMessage(new KillGameMessage(eventMessage.getNickname()));
                        }
                        playerGame.remove(c);
                    }

                }
                for (Player player : game.getSubscribers()) {
                    getConnectedClients().remove(player.getName());
                }
            }
            */
        //client.onMessage(new DisconnectionResponseMessage(eventMessage.getNickname()));
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
                        // todo: maybe add game.unsubscribe(String nickname)
                        //entry.getKey().onMessage(new PlayerOfflineMessage(eventMessage.getNickname()));
                        int missingPlayers = game.getNumberOfPlayers().getValue() - game.getSubscribers().size();
                        try {
                            entry.getKey().onMessage(new WaitingResponseMessage(nickname, missingPlayers));
                        } catch (IOException e) {
                            System.err.println("disconnection");
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
}


/* todo disconnessioni durante la partita da rivedere assolutamente
// ancora in lobby, in match
if (playerGame  != null) {
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