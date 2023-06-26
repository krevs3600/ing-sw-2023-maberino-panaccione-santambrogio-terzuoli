package it.polimi.ingsw.network;

import it.polimi.ingsw.network.eventMessages.EventMessage;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote{

    void register(Client client) throws RemoteException;

    void update(Client client, EventMessage eventMessage) throws IOException;

    void removeGameFromLobby (String gameName) throws RemoteException;
    void removeClient(Client client) throws RemoteException;

}
