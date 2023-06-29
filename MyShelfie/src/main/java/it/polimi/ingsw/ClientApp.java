package it.polimi.ingsw;

import it.polimi.ingsw.view.cli.CLI;

import java.rmi.RemoteException;
/**
 * <h1>Class ClientApp</h1>
 * The class ClientApp is used to run the CLI
 *
 * @author Francesco Santambrogio, Francesco Maberino, Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/18/2023
 */

public class ClientApp {
    /**
     *Main method
     */
    public static void main(String[] args) throws RemoteException {
        CLI cli = new CLI();
        cli.run();
        /*Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cli.setChanged();
            cli.notifyObservers(new DisconnectClientMessage(cli.getNickname()));
            System.out.println("Dropping connection and quitting...");
            //System.out.println("Quitting...");
        }));

         */
    }
}
