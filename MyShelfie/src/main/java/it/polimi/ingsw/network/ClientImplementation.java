package it.polimi.ingsw.network;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.view.cli.TextualUI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientImplementation extends UnicastRemoteObject implements Client, Runnable {

    TextualUI view = new TextualUI();
    public ClientImplementation(Server server) throws RemoteException{
        super();
        initialize(server);
    }

    public ClientImplementation (Server server, int port) throws RemoteException{
        super(port);
        initialize(server);
    }

    public ClientImplementation (Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException{
        super(port, csf, ssf);
        initialize(server);
    }

    @Override
    //TODO: lancio eccezione al chiamante inserendo update in una coda fino a che la connessione torna
    public void update(GameView gameView, EventMessage eventMessage) {
        view.update(gameView, eventMessage);
    }

    @Override
    public void run() {
        view.run();
    }

    private void initialize(Server server) throws RemoteException{
        server.register(this);
        view.addObserver((observable, eventMessage)-> {
            try{
                server.update(this, (EventMessage) eventMessage);
            } catch (RemoteException e){
                System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update. ");
            }
        });
    }
}
