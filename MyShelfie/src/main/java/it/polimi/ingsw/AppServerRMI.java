package it.polimi.ingsw;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImplementation;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServerRMI {
    public static void main(String[] args) throws RemoteException{
        Server server = new ServerImplementation();
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind("server", server);
    }
}
