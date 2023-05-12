package it.polimi.ingsw.network;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote{

    void register(Client client) throws RemoteException;

    void update(Client client, EventMessage eventMessage)throws RemoteException;
}
