package it.polimi.ingsw;

import it.polimi.ingsw.view.cli.CLI;

import java.rmi.RemoteException;

public class ClientApp {
    public static void main(String[] args) throws RemoteException {
        CLI cli = new CLI();
        cli.run();
    }
}
