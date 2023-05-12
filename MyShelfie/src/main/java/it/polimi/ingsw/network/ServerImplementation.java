package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.EventMessage;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.view.cli.TextualUI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ServerImplementation extends UnicastRemoteObject implements Server {

    private Game game;
    private GameController gameController;

    public ServerImplementation() throws RemoteException {
        super();
    }

    public ServerImplementation(int port) throws RemoteException {
        super(port);
    }

    public ServerImplementation(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException{
        super(port, csf, ssf);
    }

    @Override
    //TODO gestione di più client
    public void register(Client client) {
        this.game = new Game();
        this.game.addObserver((observer, eventMessage)-> {
            try{client.update(new GameView(game), (EventMessage) eventMessage);
        } catch(RemoteException e){
            System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update");
            }
        });
        this.gameController = new GameController(game, client);
    }

    @Override
    public void update(Client client, EventMessage eventMessage) {
        this.gameController.update(client, eventMessage);
    }


}
