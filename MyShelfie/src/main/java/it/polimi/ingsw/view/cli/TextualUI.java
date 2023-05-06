package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class TextualUI extends Observable implements Observer, Runnable {

    private final PrintStream out = System.out;
    private final Scanner in = new Scanner(System.in);
    private boolean joined = false;
    @Override
    public void run(){
        // initial setup
        printTitle();
        gameMenu();


    }


    public void gameMenu(){
        printMenu();
        int menuOption = in.nextInt();

        switch (menuOption) {
            case 1 -> {
                setChanged();
                notifyObservers(GameController.Event.CREATE_GAME);
                out.print("Insert your name: ");
                String name = in.next();
                this.joined = true;
                setChanged();
                notifyObservers(name);
            }
            case 2 -> {
                out.print("Insert your name: ");
                String name = in.next();
                this.joined = true;
                setChanged();
                notifyObservers(name);
            }
            default -> {
                out.println("Invalid option, please try again");
            }
        }

    }

    public void printTitle() {
        out.println(MessageCLI.TITLE);
    }

    public void printMenu() {
        out.println(MessageCLI.MENU);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Game model)){
            System.err.println("Discarding update from " + o);
        }

        if (arg instanceof Game game) {
            /* New choice available */
            out.println(game.getSubscribers().get(0).getName() + " got subscribed");
            out.println(game.getLivingRoomBoard());
        }
    }
}
