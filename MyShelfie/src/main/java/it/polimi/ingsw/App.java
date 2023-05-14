package it.polimi.ingsw;

import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImplementation;

import java.rmi.RemoteException;

public class App {
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImplementation();
        ClientImplementation client = new ClientImplementation(server);
        client.run();
    }
}