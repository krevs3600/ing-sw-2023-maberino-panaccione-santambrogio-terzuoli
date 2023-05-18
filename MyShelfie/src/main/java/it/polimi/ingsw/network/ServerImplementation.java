package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.eventMessages.GameNameMessage;
import it.polimi.ingsw.network.eventMessages.RequestMessage.GameNameResponseMessage;
import it.polimi.ingsw.network.eventMessages.RequestMessage.LoginResponseMessage;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerImplementation extends UnicastRemoteObject implements Server {

    private Map<GameController, Game> currentGames = new HashMap<>();
    private Set<String> currentPlayersNicknames = new HashSet<>();
    private Set<String> currentGameNames = new HashSet<>();
    private Map<String, Client> connectedClients = new HashMap<>();
    private Map<Client, GameController> player_game = new HashMap<>();
    private Game game;
    private GameController gameController;

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
        this.game = new Game();
        this.game.addObserver((observer, eventMessage) -> {
            try {
                client.update(new GameView(game), (EventMessage) eventMessage);
            } catch (RemoteException e) {
                System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update");
            }
        });
        this.gameController = new GameController(game, client);
    }

    @Override
    public void update(Client client, EventMessage eventMessage) throws RemoteException {

        switch (eventMessage.getType()) {
            case NICKNAME -> {
                boolean validNickname = false;
                if (!currentPlayersNicknames.contains(eventMessage.getNickName())) {
                    validNickname = true;
                    currentPlayersNicknames.add(eventMessage.getNickName());
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
                    currentGameNames.add(((GameNameMessage) eventMessage).getGameName());
                }
                if (validGameName) {
                    client.onMessage(new GameNameResponseMessage(((GameNameMessage) eventMessage).getGameName(), validGameName));

                } else client.onMessage(new GameNameResponseMessage(validGameName));
            }


        }
    }
}
