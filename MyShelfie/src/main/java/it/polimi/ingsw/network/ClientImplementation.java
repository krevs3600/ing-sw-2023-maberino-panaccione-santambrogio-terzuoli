package it.polimi.ingsw.network;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.CreatorLoginResponseMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.GameNameResponseMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.LoginResponseMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.RequestMessage;
import it.polimi.ingsw.network.eventMessages.DisconnectClientMessage;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.view.cli.CLI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
public class ClientImplementation extends UnicastRemoteObject implements Client {

    CLI view;

    private String nickname;
    private Server server;
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
        this.server = server;
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

    public void onMessage(MessageToClient message) throws RemoteException {

//just to set the parametres, maybe it will be a problem with concurrency (?)
        if(message instanceof RequestMessage) {
            switch (message.getType()) {
                case CREATOR_LOGIN_RESPONSE -> {
                    CreatorLoginResponseMessage creatorLoginResponseMessage = (CreatorLoginResponseMessage) message;
                    if (creatorLoginResponseMessage.isValidNickname())
                        this.nickname = creatorLoginResponseMessage.getNickname();

                }
                case GAME_NAME_RESPONSE -> {
                    GameNameResponseMessage gameNameResponseMessage = (GameNameResponseMessage) message;
                    if (gameNameResponseMessage.isValidGameName())
                        this.gameName = gameNameResponseMessage.getGameName();
                }
                case LOGIN_RESPONSE -> {
                    LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                    if (loginResponseMessage.isValidNickname())
                        this.nickname = loginResponseMessage.getNickname();

                }


            }
        }
        else if (message instanceof ErrorMessage) {
        }
        view.showMessage(message);
    }

    public void disconnect() throws RemoteException{
        // UnicastRemoteObject.unexportObject(this, true);
        server.update(this, new DisconnectClientMessage(this, this.getNickname()));
        server = null;
        view = null;
    }


    private void initialize(Server server) throws RemoteException {
        this.server = server;
        view.addObserver((observable, eventMessage) -> {
            try {
                server.update(this, (EventMessage) eventMessage);
            } catch (RemoteException e) {
                System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update. ");
            }
        });
    }
}

