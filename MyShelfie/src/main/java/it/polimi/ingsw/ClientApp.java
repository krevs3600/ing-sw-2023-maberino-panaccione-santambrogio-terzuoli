package it.polimi.ingsw;

import it.polimi.ingsw.network.eventMessages.DisconnectClientMessage;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.Client;

import java.rmi.RemoteException;

public class ClientApp {
    public static void main(String[] args) throws RemoteException {
        CLI cli = new CLI();
        cli.run();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cli.setChanged();
            cli.notifyObservers(new DisconnectClientMessage(cli.getNickname()));
            System.out.println("Dropping connection and quitting...");
            //System.out.println("Quitting...");
        }));
    }
}
