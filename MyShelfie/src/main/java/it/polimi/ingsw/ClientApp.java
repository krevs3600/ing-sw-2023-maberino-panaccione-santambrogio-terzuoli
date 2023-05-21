package it.polimi.ingsw;

import it.polimi.ingsw.view.cli.CLI;

import java.rmi.RemoteException;

public class ClientApp {
    public static void main(String[] args) throws RemoteException {
        CLI cli = new CLI();
        cli.run();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (cli.getClient() != null) {
                        cli.getClient().disconnect();
                        System.out.println("Dropping connection and quitting...");
                    } else {
                        System.out.println("Quitting...");
                    }

                } catch (RemoteException e) {
                    System.out.println("Error " + e);
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
