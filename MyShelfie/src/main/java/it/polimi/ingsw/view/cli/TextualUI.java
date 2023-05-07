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
            int numberOfTiles;
            do {
                out.print("How many tiles would you like to get? ");
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

            out.print("In which column you want to insert your item tiles? ");
            int column = in.nextInt();
            setChanged();
            notifyObservers(numberOfTiles);
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

        if (arg instanceof String subscriber) {
            /* New choice available */
            out.println( subscriber + " got subscribed");
        }

        if (arg instanceof GameView game) {
            /* New choice available */
            out.println("\n-----------------------------------------------------------------------\n LIVING ROOM BOARD:");
            out.println(game.getLivingRoomBoard().toString());
            out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
            out.println(game.getTilePack().toString());
            out.println("\n-----------------------------------------------------------------------\n" + game.getSubscribers().get(0).getName() + "'s BOOKSHELF:");
            out.println(game.getSubscribers().get(0).getBookshelf().toString());
            out.println("\n-----------------------------------------------------------------------\n");
            out.println("First common goal card: " + game.getLivingRoomBoard().getCommonGoalCards().get(0).toString());
            out.println("\n-----------------------------------------------------------------------\n");
            out.println("S1econd common goal card: " + game.getLivingRoomBoard().getCommonGoalCards().get(1).toString());
            out.println("\n-----------------------------------------------------------------------\n");
            out.println(game.getSubscribers().get(0).getName() + "'s personal goal card: " + game.getSubscribers().get(0).getPersonalGoalCard().toString());
            out.println("\n-----------------------------------------------------------------------\n");
        }
    }
}
