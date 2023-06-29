package it.polimi.ingsw.network;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.eventMessages.EventMessage;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <h1>Interface Client</h1>
 * Interface to communicate with the {@link Server}
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/10/2023
 */
public interface Client extends Remote {
    /**
     *This method is used by the client to update correctly the View after receiving an {@link EventMessage}
     * @param gameView with which the View will be updated
     * @param eventMessage that prompts the necessity of the update
     */
    void update(GameView gameView, EventMessage eventMessage) throws RemoteException;

    /**
     * This method is used so to let the {@link Server} communicate directly with the {@link Client}
     * via to asynchronous messages
     * @param message the message to be sent to the {@link Client}
     */
    void onMessage (MessageToClient message) throws IOException;

}
