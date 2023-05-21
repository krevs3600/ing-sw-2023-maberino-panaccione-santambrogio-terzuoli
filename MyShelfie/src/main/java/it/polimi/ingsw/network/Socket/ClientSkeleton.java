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

public class ClientSkeleton implements Client {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private String nickname = "";

    public ClientSkeleton(Socket socket) throws RemoteException {
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

    @Override
    public void update(GameView gameView, EventMessage eventMessage) throws RemoteException {
        try {
            oos.writeObject(gameView);
        } catch (IOException e) {
            throw new RemoteException("Cannot send model view", e);
        }
        try {
            oos.writeObject(eventMessage);
        } catch (IOException e) {
            throw new RemoteException("Cannot send event", e);
        }
    }

    public void receive(Server server) throws RemoteException {
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

    public void onMessage (MessageToClient message) throws RemoteException {

    }

    public void disconnect(){

    }
}
