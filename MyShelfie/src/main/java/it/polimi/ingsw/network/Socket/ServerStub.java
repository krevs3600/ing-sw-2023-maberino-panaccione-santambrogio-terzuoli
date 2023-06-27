package it.polimi.ingsw.network.Socket;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.RequestMessage;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStub implements Server {

    String ip;
    int port;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private Socket socket;

    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;

    }

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

    @Override
    public void removeClient(Client client) throws RemoteException {

    }

    public void receive(Client client) throws RemoteException {
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
