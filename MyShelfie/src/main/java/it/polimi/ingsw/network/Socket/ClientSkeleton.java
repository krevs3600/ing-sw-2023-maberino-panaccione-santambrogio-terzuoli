package it.polimi.ingsw.network.Socket;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * <h1>Class ClientSkeleton</h1>
 * The class ClientSkeleton implements the interface {@link Client}, however it is used by the {@link Server}. The {@link Server} utilizes
 * {@link ClientSkeleton} to send the communication back to the {@link Client}.
 * The classes {@link ServerStub} and {@link ClientSkeleton} together create the middleware that RMI already automatically implements,
 * adapting socket communication to RMI.
 *
 * @author Carlo Terzuoli, Francesco Maberino Francesco Santambrogio
 * @version 1.0
 * @since 13/05/2023
 */
public class ClientSkeleton implements Client {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final Socket socket;

    /**
     * Class constructor
     * @param socket object with which the communication will be established
     * @throws RemoteException
     */
    public ClientSkeleton(Socket socket) throws RemoteException{
        this.socket = socket;
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
    }

    /**
     * This method is used to update the {@link Client} with the right {@link EventMessage} and {@link GameView}
     * @param gameView with which the View will be updated
     * @param eventMessage that prompts the necessity of the update
     * @throws RemoteException in case it is incapable of sending the update
     */
    @Override
    public void update(GameView gameView, EventMessage eventMessage) throws RemoteException {
        try {
            oos.writeObject(gameView);
        } catch (IOException e) {
            throw new RemoteException("Cannot send model view", e);
        }
        try {
            oos.writeObject(eventMessage);
            oos.flush();
            oos.reset();
        } catch (IOException e) {
            throw new RemoteException("Cannot send event", e);
        }
    }

    /**
     * This method, upon receiving an {@link EventMessage}, reads it and afterwards updates the {@link Server} with it
     * @param server to be updated
     * @throws IOException in case the {@link EventMessage} cannot be read
     */
    public void receive(Server server) throws IOException {
        EventMessage eventMessage;
        try {
            eventMessage = (EventMessage) ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("Cannot receive choice from client", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Cannot deserialize choice from client", e);
        }
        server.update(this, eventMessage);
    }

    /**
     * This method is used to send an asynchronous Message to the {@link Client}
     * @param message the message to be sent to the {@link Client}
     * @throws RemoteException in case it is incapable of sending the Message
     */
    public void onMessage (MessageToClient message) throws RemoteException{
        try {
            oos.writeObject(message);
            oos.flush();
            oos.reset();
        } catch (IOException e) {
            throw new RemoteException("Cannot send message " + message.getType());
        }
    }

    /*
    public void disconnect () throws RemoteException {
        this.ois = null;
        this.oos = null;
        try {
            if (!socket.isClosed()){
                socket.close();
            }
        } catch (IOException e){
            throw (new RemoteException("Could not disconnect"));
        }

    }*/
}
