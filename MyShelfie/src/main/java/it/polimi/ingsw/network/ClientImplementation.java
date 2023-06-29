package it.polimi.ingsw.network;

import it.polimi.ingsw.client.view.FXML.GUI;
import it.polimi.ingsw.client.view.FXML.View;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.CreatorLoginResponseMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.LoginResponseMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.RequestMessage;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.view.cli.CLI;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import it.polimi.ingsw.observer_observable.Observer;

/**
 * <h1>Class ClientImplementation</h1>
 * Class that implements the {@link Client} interface. It is used to communicate with the {@link Server}
 * and update the View
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/10/2023
 */
public class ClientImplementation extends UnicastRemoteObject implements Client {

    View view;
    private String nickname;

    public View getView() {
        return view;
    }

    /**
     * Class constructor
     * @param view that is linked to the {@link ClientImplementation}
     * @param server with which the {@link ClientImplementation} intends to communicate
     */
    public ClientImplementation(View view, Server server) throws RemoteException {
        super();
        this.view = view;
        initialize(server);
    }


    /**
     *This method is used by the client to update correctly the View after receiving an {@link EventMessage}
     * @param gameView with which the View will be updated
     * @param eventMessage that prompts the necessity of the update
     */
    @Override
    //TODO: lancio eccezione al chiamante inserendo update in una coda fino a che la connessione torna
    public void update(GameView gameView, EventMessage eventMessage) {
        view.update(gameView, eventMessage);
    }

    /**
     * This method is used to let the {@link Server} communicate directly with the {@link Client}
     * via to asynchronous messages
     * @param message the message to be sent to the {@link Client}
     */
    public void onMessage(MessageToClient message) throws IOException {

        //just to set the parameters, maybe it will be a problem with concurrency (?)
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


    /**
     * Initialize connection to the {@link Server} and send {@link EventMessage}s to it.
     * This method adds either the {@link CLI} or the {@link View} as an {@link Observer} of the {@link EventMessage}. Then, the {@link Server} is updated
     * with such {@link EventMessage}
     */
    private void initialize(Server server) throws RemoteException {
        if (view instanceof CLI){
            ((CLI)view).addObserver((observable, eventMessage) -> {
                try {
                    server.update(this, eventMessage);
                } catch (IOException e) {
                    System.err.println("Unable to update the server:" + e.getMessage()+ " Skipping the update. ");
                    ((CLI)view).deleteAllObservers();
                }
             });

        }
        else if (view instanceof GUI){
            ((GUI) view).addObserver((observable, eventMessage) -> {
                try {
                    server.update(this, eventMessage);
                } catch (IOException e) {
                    System.err.println("Unable to update the server: Skipping the update. ");
                    ((GUI)view).deleteAllObservers();
                }
            });
        }
    }

    /**
     * Getter method
     * @return the {@link ClientImplementation#nickname} linked with the {@link ClientImplementation}
     */
    public String getNickname() {
        return this.nickname;
    }
}

