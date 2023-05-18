package it.polimi.ingsw.network;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.eventMessages.RequestMessage.GameNameResponseMessage;
import it.polimi.ingsw.network.eventMessages.RequestMessage.LoginResponseMessage;
import it.polimi.ingsw.network.eventMessages.RequestMessage.RequestMessage;
import it.polimi.ingsw.view.cli.CLI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientImplementation extends UnicastRemoteObject implements Client {

    CLI view;

    private String nickname;

    private String gameName; // in realtà è un optional

    public String getNickname() {
        return this.nickname;
    }

    public String getGameName() {
        return this.gameName;
    }
    public ClientImplementation(CLI view, Server server) throws RemoteException {
        super();
        this.view = view;
        initialize(server);
    }

    public ClientImplementation(CLI view, Server server, int port) throws RemoteException {
        super(port);
        this.view = view;
        initialize(server);
    }

    public ClientImplementation(CLI view, Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        this.view = view;
        initialize(server);
    }

    @Override
    //TODO: lancio eccezione al chiamante inserendo update in una coda fino a che la connessione torna
    public void update(GameView gameView, EventMessage eventMessage) {
        view.update(gameView, eventMessage);
    }

    public void onMessage(RequestMessage message) throws RemoteException {
        view.showMessage(message);
        //just to set the parametres, maybe it will be a problem with concurrency (?)
        switch (message.getType()) {
            case LOGIN_RESPONSE -> {
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                if (loginResponseMessage.isValidNickname())
                    this.nickname = loginResponseMessage.getNickname();
            }
            case GAMENAME_RESPONSE -> {
               GameNameResponseMessage gameNameResponseMessage = (GameNameResponseMessage) message;
                if (gameNameResponseMessage.isValidGameName())
                   this.gameName=gameNameResponseMessage.getGameName();
            }

        }
    }


    private void initialize(Server server) throws RemoteException {
        //server.register(this);
        view.addObserver((observable, eventMessage) -> {
            try {
                server.update(this, (EventMessage) eventMessage);
            } catch (RemoteException e) {
                System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update. ");
            }
        });
    }
}

