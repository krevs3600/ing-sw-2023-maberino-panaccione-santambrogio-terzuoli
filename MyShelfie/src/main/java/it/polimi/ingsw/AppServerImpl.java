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
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    rmiThread.join();
                    socketThread.join();
                    System.out.println("Closing existing connections...");
                } catch (InterruptedException e) {
                    System.err.println("No connection protocol available. Exiting...");
                }
            }
        });

    }

    private static void startRMI() throws RemoteException {
        AppServerImpl server = getInstance();

        Registry registry = LocateRegistry.createRegistry(1245);
        registry.rebind("MyShelfieServer", server);
    }

    public static void startSocket() throws RemoteException {
        AppServerImpl instance = getInstance();
        try (ServerSocket serverSocket = new ServerSocket(1246)) {
            while (true) {
                Socket socket = serverSocket.accept();
                instance.executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        Server server = new ServerImplementation();
                        server.register(clientSkeleton);
                        while (true) {
                            clientSkeleton.receive(server);
                        }
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from client. Closing this connection...");
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

    @Override
    public Server connect() throws RemoteException {
        if (server == null) {
            server = new ServerImplementation(1245);
        }
        return server;
    }
}