package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.network.eventMessages.GameNameMessage;
import it.polimi.ingsw.network.eventMessages.GameCreationMessage;
import it.polimi.ingsw.network.requestMessage.GameCreationResponseMessage;
import it.polimi.ingsw.network.requestMessage.GameNameResponseMessage;
import it.polimi.ingsw.network.requestMessage.LoginResponseMessage;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImplementation extends UnicastRemoteObject implements Server {

    private Map<GameController, Game> currentGames = new HashMap<>();
    private Set<String> currentPlayersNicknames = new HashSet<>();
    private Set<String> currentGameNames = new HashSet<>();
    private Map<String, Client> connectedClients = new HashMap<>();
    private Map<Client, GameController> player_game = new HashMap<>();

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
        /*this.game = new Game();
        this.game.addObserver((observer, eventMessage) -> {
            try {
                client.update(new GameView(game), (EventMessage) eventMessage);
            } catch (RemoteException e) {
                System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update");
            }
        });

         */
        //this.gameController = new GameController(game, client);
    }

    @Override
    public void update(Client client, EventMessage eventMessage) throws RemoteException {

        switch (eventMessage.getType()) {
            case NICKNAME -> {
                boolean validNickname = false;
                if (!currentPlayersNicknames.contains(eventMessage.getNickName())) {
                    validNickname = true;
                    currentPlayersNicknames.add(eventMessage.getNickName());
                    connectedClients.put(eventMessage.getNickName(), client);
                }
                if (validNickname) {
                    client.onMessage(new LoginResponseMessage(eventMessage.getNickName(), validNickname));
                } else
                    client.onMessage(new LoginResponseMessage(validNickname));
            }
            //this.gameController.update(client, eventMessage);
            case GAMENAME -> {
                boolean validGameName = false;
                GameNameMessage gameNameMessage = (GameNameMessage) eventMessage;
                if (!currentGameNames.contains(gameNameMessage.getGameName())) {
                    validGameName = true;
                    currentGameNames.add(gameNameMessage.getGameName());
                    client.onMessage(new GameNameResponseMessage(gameNameMessage.getGameName(), validGameName));
                }
                else {
                    client.onMessage(new GameNameResponseMessage(validGameName));
                }
            }
            case NUM_OF_PLAYERS -> {
                GameCreationMessage gameCreationMessage = (GameCreationMessage) eventMessage;
                boolean isValid = false;
                if (gameCreationMessage.getNumOfPlayers() > 0 && gameCreationMessage.getNumOfPlayers() < 5) {
                    Game game = new Game(Arrays.stream(NumberOfPlayers.values()).filter(x -> x.getValue() == gameCreationMessage.getNumOfPlayers()).toList().get(0), gameCreationMessage.getGameName());
                    GameController gameController = new GameController(game, client);
                    player_game.put(client, gameController);
                    currentGames.put(gameController, game);
                    isValid = true;
                }
                client.onMessage(new GameCreationResponseMessage(isValid));
            }
        }
    }
}
