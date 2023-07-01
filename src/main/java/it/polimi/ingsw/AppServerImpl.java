package it.polimi.ingsw;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImplementation;
import it.polimi.ingsw.network.Socket.ClientSkeleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * <h1>Class AppServerImpl</h1>
 * The class AppServerImpl is used to run the Server
 *
 * @author Francesco Santambrogio, Francesco Maberino
 * @version 1.0
 * @since 5/18/2023
 */

public class AppServerImpl extends UnicastRemoteObject implements AppServer
{

    private static Server server = null;
    private static AppServerImpl instance;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    protected AppServerImpl() throws RemoteException {
    }

    public static AppServerImpl getInstance() throws RemoteException {
        if (instance == null) {
            instance = new AppServerImpl();
        }
        return instance;
    }

    /**
     * Main method
     */
    public static void main(String[] args) {

        Thread rmiThread = new Thread(() -> {
            try {
                startRMI();
            } catch (RemoteException e) {
                System.err.println("Cannot start RMI. This protocol will be disabled.");
            }
        });

        rmiThread.start();

        Thread socketThread = new Thread(() -> {
            try {
                startSocket();
            } catch (RemoteException e) {
                System.err.println("Cannot start socket. This protocol will be disabled.");
            }
        });
        socketThread.start();
        try {
            rmiThread.join();
            socketThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }

    }

    /**
     * Method to start new RMI connection
     * @throws RemoteException
     */
    private static void startRMI() throws RemoteException {
        AppServerImpl server = getInstance();

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("MyShelfieServer", server);
    }

    /**
     * Method to start new Socket connection
     * @throws RemoteException
     */
    public static void startSocket() throws RemoteException {
        AppServerImpl instance = getInstance();
        try (ServerSocket serverSocket = new ServerSocket(1244)) {
            while (true) {
                Socket socket = serverSocket.accept();
                instance.executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        Server server = getInstance().connect();
                        //server.register(clientSkeleton);
                        while (true) {
                            clientSkeleton.receive(server);
                        }
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from client. Closing this connection...");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot start socket server", e);
        }
    }

    /**
     * Connect method
     * @return if no {@link Server} has already been created, it instantiates one, otherwise it return the already
     * existing one
     * @throws RemoteException
     */
    @Override
    public Server connect() throws RemoteException {
        if (server == null) {
            server = new ServerImplementation();
        }
        return server;
    }
}