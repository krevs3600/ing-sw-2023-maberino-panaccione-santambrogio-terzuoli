package it.polimi.ingsw.network.Socket;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.RequestMessage;
import it.polimi.ingsw.network.ServerImplementation;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * <h1>Class ServerStub</h1>
 * The class ServerStub implements the interface {@link Server}, however it is used by the {@link Client}. Whenever
 * a {@link Client} wants to either register on a {@link Server} or to update it, everything has to go through the {@link ServerStub}.
 * The classes {@link ServerStub} and {@link ClientSkeleton} together create the middleware that RMI already automatically implements,
 * adapting socket communication to RMI.
 *
 * @author Carlo Terzuoli, Francesco Maberino Francesco Santambrogio
 * @version 1.0
 * @since 13/05/2023
 */

public class ServerStub implements Server {

    String ip;
    int port;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private Socket socket;

    /**
     * Class constructor
     */
    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;

    }

    /**
     * This method is used to register a {@link Client} into the server. It creates a new socket, with input stream and output stream
     * to enable both the sending and receiving of messages
     * @param client the {@link Client} to be added
     * @exception RemoteException like RMI would do, if it is not able to create the input or the output stream
     */
    @Override
    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(ip, port);
            try {
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create output stream", e);
            }
            try {
                this.ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create input stream", e);
            }
        } catch (IOException e) {
            throw new RemoteException("Unable to connect to the server", e);
        }
    }

    /**
     * The update method's purpose is to forward to the {@link ServerImplementation} the {@link EventMessage} the action the{@link Client}
     * intended to perform
     * @param client from which it receives the {@link EventMessage}
     * @param eventMessage containing the action to be performed
     * @throws RemoteException if we are not able to send the update
     */
    @Override
    public void update(Client client, EventMessage eventMessage) throws RemoteException {
        try {
            oos.writeObject(eventMessage);
            oos.flush();
            oos.reset();
        } catch (IOException e) {
            throw new RemoteException("Cannot send event", e);
        }
    }

    @Override
    public void removeGameFromLobby(String gameName) throws RemoteException {

    }


    /**
     * This method, upon receiving a message from a {@link Server}, reads it.
     * If the Message received is either a {@link RequestMessage} or an {@link ErrorMessage} the stub sends an asynchronous message
     * to the {@link Client}. Otherwise, if it is an {@link EventMessage}, it updates the {@link Client} with it, together with the correct
     * {@link GameView}.
     * @param client to be updated
     * @throws RemoteException in case the {@link EventMessage} cannot be read
     */
    public synchronized void receive(Client client) throws RemoteException {
        GameView gameView;
        EventMessage eventMessage;
        try {
            Object received = ois.readObject();
            if (received instanceof RequestMessage message){
                client.onMessage(message);
            } else if (received instanceof ErrorMessage message) {
                client.onMessage(message);
            }
            else if (received instanceof GameView) {
                gameView = (GameView) received;
                eventMessage = (EventMessage) ois.readObject();
                client.update(gameView, eventMessage);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws RemoteException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }
    }
}
