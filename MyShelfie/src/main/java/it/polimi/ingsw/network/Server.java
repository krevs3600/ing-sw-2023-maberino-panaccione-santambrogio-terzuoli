package it.polimi.ingsw.network;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.eventMessages.EventMessage;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <h1>Interface Server</h1>
 * This interface of the main server, used to handle connections of the clients and their messages
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/10/2023
 */
public interface Server extends Remote{

    /**
     * This method is used to register a {@link Client} into the server. The {@link Client} be sent instances of {@link GameView}
     * as the game progresses, so it will be able to see the game and play it
     * @param client the {@link Client} to be added
     */
    void register(Client client) throws RemoteException;

    /**
     * The update method's purpose is to have the {@link ServerImplementation} react to every {@link EventMessage} it receives in the correct manner and to handle
     *  each correctly.
     * @param client from which it receives the {@link EventMessage}
     * @param eventMessage containing the action to be performed
     */
    void update(Client client, EventMessage eventMessage) throws IOException;

    /**
     * This method removes a {@link Game} name from the lobby
     * @param gameName that is needed to be removed
     */
    void removeGameFromLobby (String gameName) throws RemoteException;

}
