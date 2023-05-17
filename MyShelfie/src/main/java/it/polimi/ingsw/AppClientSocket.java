/*package it.polimi.ingsw;

import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.Socket.ServerStub;

import java.rmi.RemoteException;


public class AppClientSocket
{
    public static void main( String[] args ) throws RemoteException {
        ServerStub serverStub = new ServerStub("localhost", 1234);
        ClientImplementation client = new ClientImplementation(serverStub);
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        serverStub.receive(client);
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from server. Stopping...");
                        try {
                            serverStub.close();
                        } catch (RemoteException ex) {
                            System.err.println("Cannot close connection with server. Halting...");
                        }
                        System.exit(1);
                    }
                }
            }
        }.start();

        //client.run();
    }
}

 */