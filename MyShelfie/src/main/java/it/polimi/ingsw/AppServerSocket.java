package it.polimi.ingsw;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImplementation;
import it.polimi.ingsw.network.Socket.ClientSkeleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;


public class AppServerSocket
{
    public static void main( String[] args ) throws RemoteException {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                    Server server = new ServerImplementation();
                    server.register(clientSkeleton);
                    while (true) {
                        clientSkeleton.receive(server);
                    }
                } catch (IOException e) {
                    System.err.println("Socket failed: " + e.getMessage() +". Closing connection and waiting for a new one...");
                }
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot create server socket", e);
        }
    }
}