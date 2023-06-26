package it.polimi.ingsw.network;

import it.polimi.ingsw.client.view.FXML.GUI;
import it.polimi.ingsw.client.view.FXML.View;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.CreatorLoginResponseMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.LoginResponseMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.RequestMessage;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.observer_observable.Observer;
import it.polimi.ingsw.view.cli.CLI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
public class ClientImplementation extends UnicastRemoteObject implements Client {

    View view;
    private String nickname;

    public View getView() {
        return view;
    }

    public ClientImplementation(View view, Server server) throws RemoteException {
        super();
        this.view = view;
        initialize(server);
    }


    public ClientImplementation(View view, Server server, int port) throws RemoteException {
        super(port);
        this.view = view;
        initialize(server);
    }

    public ClientImplementation(View view, Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        this.view = view;
        initialize(server);
    }

    @Override
//TODO: lancio eccezione al chiamante inserendo update in una coda fino a che la connessione torna
    public void update(GameView gameView, EventMessage eventMessage) {
        view.update(gameView, eventMessage);
    }

    public void onMessage(MessageToClient message) throws IOException {

//just to set the parametres, maybe it will be a problem with concurrency (?)
        if (message instanceof RequestMessage) {
            switch (message.getType()) {
                case CREATOR_LOGIN_RESPONSE -> {
                    CreatorLoginResponseMessage creatorLoginResponseMessage = (CreatorLoginResponseMessage) message;
                    if (creatorLoginResponseMessage.isValidNickname())
                        this.nickname = creatorLoginResponseMessage.getNickname();

                }
                case LOGIN_RESPONSE -> {
                    LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                    if (loginResponseMessage.isValidNickname())
                        this.nickname = loginResponseMessage.getNickname();

                }

                case DISCONNECTION_RESPONSE -> view.deleteAllObservers();


            }
        }
        view.showMessage(message);


    }




    private void initialize(Server server) throws RemoteException {
        if (view instanceof CLI){
            ((CLI)view).addObserver((observable, eventMessage) -> {
                try {
                    server.update(this, eventMessage);
                } catch (IOException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update. ");
                    ((CLI)view).deleteAllObservers();
                }
             });

        }
        else if (view instanceof GUI){
            ((GUI) view).addObserver((observable, eventMessage) -> {
                try {
                    server.update(this, eventMessage);
                } catch (IOException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update. ");
                    ((CLI)view).deleteAllObservers();
                }
            });
        }
    }

    public String getNickname() {
        return this.nickname;
    }
}

