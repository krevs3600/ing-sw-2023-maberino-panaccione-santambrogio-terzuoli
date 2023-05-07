package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.utils.Position;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.SortedMap;

public class TextualUI extends Observable implements Observer, Runnable {

    private final PrintStream out = System.out;
    private final Scanner in = new Scanner(System.in);
    private boolean joined = false;
    @Override
    public void run(){
        // initial setup
        printTitle();
        gameMenu();
        while (true) {
            int numberOfTiles = 0;
            do {
                out.print("How many tiles would you like to get?");
                try {
                    numberOfTiles = in.nextInt();
                    setChanged();
                    notifyObservers(numberOfTiles);
                } catch (IllegalArgumentException e){
                    System.out.println("You can pick from 1 to 3 item tiles");
                    numberOfTiles = 0;
                }
            } while (numberOfTiles == 0);

            for (int i = 0; i < numberOfTiles; i++) {
                out.print("r: ");
                int r = in.nextInt();
                System.out.print("c: ");
                int c = in.nextInt();
                try {
                    setChanged();
                    notifyObservers(new Position(r,c));
                } catch (IllegalAccessError e) {
                    out.println(e.getMessage());
                    i--;
                }

            }

        }
    }


    public void gameMenu(){
        printMenu();
        int menuOption = in.nextInt();

        switch (menuOption) {
            case 1 -> {
                setChanged();
                notifyObservers(GameController.Event.CREATE_GAME);
                // --------------------------
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
        if (!(o instanceof GameView model)){
            System.err.println("Discarding update from " + o);
        }

        if (arg instanceof GameView game) {
            /* New choice available */
            out.println( " got subscribed");
            out.println(game.getLivingRoomBoard().toString());
        }
    }
}
