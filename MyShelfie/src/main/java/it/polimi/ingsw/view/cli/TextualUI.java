package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.network.EventMessage;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.observer_observable.Observable;

import java.io.PrintStream;
import java.util.Scanner;

public class TextualUI extends Observable implements Runnable {

    private final PrintStream out = System.out;
    private final Scanner in = new Scanner(System.in);
    private final Object lock = new Object();
    private boolean joined = false;

    private enum State {
        WAITING_FOR_PLAYER,
        WAITING_FOR_MODEL_VIEW
    }

    private State state = State.WAITING_FOR_PLAYER;
    private State getState() {
        synchronized (lock) {
            return state;
        }
    }

    private void setState(State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    @Override
    public void run() {
        while (true) {
            while (getState() == State.WAITING_FOR_MODEL_VIEW) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Interrupted while waiting for server: " + e.getMessage());
                    }
                }
            }
            // initial setup
            printTitle();
            String nickName = "";
            while (nickName.length() < 1) {
                out.println("Please insert your name: ");
                nickName = in.next();
            }
            //setChanged();
            //notifyObservers(new NicknameMessage(nickName));

            int numOfPlayers = 0;
            while (numOfPlayers <= 0 || numOfPlayers > 4) {
                out.println("Please insert the number of players: ");
                numOfPlayers = in.nextInt();
            }
            setChanged();
            notifyObservers(new NumOfPlayerMessage(nickName, numOfPlayers));
            this.setState(State.WAITING_FOR_MODEL_VIEW);
        }



        /**while (true) {
            int numberOfTiles;
            do {
                out.print("How many tiles would you like to get? ");
                try {
                    numberOfTiles = in.nextInt();
                    setChanged();
                    notifyObservers(numberOfTiles);
                } catch (IllegalArgumentException e) {
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
                    notifyObservers(new Position(r, c));
                } catch (IllegalAccessError e) {
                    out.println(e.getMessage());
                    i--;
                }
            }

            out.print("In which column you want to insert your item tiles? ");
            int column = in.nextInt();
            setChanged();
            notifyObservers(column);
        }

         */
    }


    public void gameMenu() {
        printMenu();
        int menuOption = in.nextInt();

        switch (menuOption) {
            case 1 -> {
                setChanged();
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

    public void update(GameView game, EventMessage eventMessage) {
        switch (eventMessage.getType()) {
            /**
             * case NUM_OF_PLAYERS_REQUEST -> {
                out.println("Please insert the number of players: ");
                int numOfPlayers = in.nextInt();
                setChanged();
                notifyObservers(new NumOfPlayerMessage(eventMessage.getNickName(), numOfPlayers));
            }
             */
            case BOARD -> {
                System.out.println("--- NEW TURN ---");
                out.println("\n-----------------------------------------------------------------------\n LIVING ROOM BOARD:");
                out.println(game.getLivingRoomBoard().toString());
                out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
                out.println(game.getTilePack().toString());
                out.println("\n-----------------------------------------------------------------------\n" + game.getSubscribers().get(0).getName() + "'s BOOKSHELF:");
                out.println(game.getSubscribers().get(0).getBookshelf().toString());
                out.println("\n-----------------------------------------------------------------------\n");
                out.println("First common goal card: " + game.getLivingRoomBoard().getCommonGoalCards().get(0).toString());
                out.println("\n-----------------------------------------------------------------------\n");
                out.println("Second common goal card: " + game.getLivingRoomBoard().getCommonGoalCards().get(1).toString());
                out.println("\n-----------------------------------------------------------------------\n");
                out.println(game.getSubscribers().get(0).getName() + "'s personal goal card: \n" + game.getSubscribers().get(0).getPersonalGoalCard().toString());
                out.println("\n-----------------------------------------------------------------------\n");
                out.println(game.getSubscribers().get(0).getName() + "'s score: " + game.getCurrentPlayerScore());
                out.println("\n-----------------------------------------------------------------------\n");
                out.println("Please enter a position: ");
                boolean stopPickingTiles = false;
                for (int i = 0; i < 3 && !stopPickingTiles; i++) {
                    out.print("r: ");
                    int r = in.nextInt();
                    System.out.print("c: ");
                    int c = in.nextInt();
                    try {
                        setChanged();
                        notifyObservers(new TilePositionMessage(eventMessage.getNickName(), new Position(r, c)));
                        this.setState(State.WAITING_FOR_MODEL_VIEW);
                    } catch (IllegalAccessError e) {
                        out.println(e.getMessage());
                        i--;
                    }
                    String answer = "";
                    do {
                        System.out.println("\nIf you whish to stop picking tiles type 'stop', otherwise type 'continue'");
                        answer = in.next();
                        if (answer.equals("stop")) {
                            stopPickingTiles = true;
                        }
                    } while (!answer.equals("stop")  && !answer.equals("continue"));
                }
                out.println("\n-----------------------------------------------------------------------\n" + game.getSubscribers().get(0).getName() + "'s BOOKSHELF:");
                out.println(game.getSubscribers().get(0).getBookshelf().toString());
                out.println("\n-----------------------------------------------------------------------\n");
                out.print("In which column you want to insert your item tiles?\n");
                int column = in.nextInt();
                setChanged();
                notifyObservers(new BookshelfColumnMessage(eventMessage.getNickName(), column));
                this.setState(State.WAITING_FOR_MODEL_VIEW);
            }

            case TILE_POSITION -> {
                out.println("\n-----------------------------------------------------------------------\n LIVING ROOM BOARD:");
                out.println(game.getLivingRoomBoard().toString());
                this.setState(State.WAITING_FOR_PLAYER);
            }

            case TILE_PACK -> {
                out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
                out.println(game.getTilePack().toString());
                this.setState(State.WAITING_FOR_PLAYER);
            }

            case BOOKSHELF -> {
                out.println("\n-----------------------------------------------------------------------\n" + game.getSubscribers().get(0).getName() + "'s BOOKSHELF:");
                out.println(game.getSubscribers().get(0).getBookshelf().toString());
                this.setState(State.WAITING_FOR_PLAYER);
            }

            case INSERTION_REQUEST -> {
                out.println("\n-----------------------------------------------------------------------\n" + game.getSubscribers().get(0).getName() + "'s BOOKSHELF:");
                out.println(game.getSubscribers().get(0).getBookshelf().toString());
                out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
                out.println(game.getTilePack().toString());
                while (game.getTilePack().getTiles().size()>0) {
                    out.print("Choose an item tile to insert from the tilepack into the selected column\n");
                    int itemTileIndex = in.nextInt();
                    setChanged();
                    notifyObservers(new ItemTileIndexMessage(eventMessage.getNickName(), itemTileIndex));
                    this.setState(State.WAITING_FOR_MODEL_VIEW);
                }
            }

        }


/**
 if (!( instanceof GameView model)){
 System.err.println("Discarding update from " + o);
 }

 if (arg instanceof String subscriber) {
 /* New choice available */

/**


 if (arg instanceof GameView game) {
 /* New choice available */
/**
 out.println("\n-----------------------------------------------------------------------\n LIVING ROOM BOARD:");
 out.println(game.getLivingRoomBoard().toString());
 out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
 out.println(game.getTilePack().toString());
 out.println("\n-----------------------------------------------------------------------\n" + game.getSubscribers().get(0).getName() + "'s BOOKSHELF:");
 out.println(game.getSubscribers().get(0).getBookshelf().toString());
 out.println("\n-----------------------------------------------------------------------\n");
 out.println("First common goal card: " + game.getLivingRoomBoard().getCommonGoalCards().get(0).toString());
 out.println("\n-----------------------------------------------------------------------\n");
 out.println("Second common goal card: " + game.getLivingRoomBoard().getCommonGoalCards().get(1).toString());
 out.println("\n-----------------------------------------------------------------------\n");
 out.println(game.getSubscribers().get(0).getName() + "'s personal goal card: \n" + game.getSubscribers().get(0).getPersonalGoalCard().toString());
 out.println("\n-----------------------------------------------------------------------\n");
 out.println(game.getSubscribers().get(0).getName() + "'s score: " + game.getCurrentPlayerScore());
 out.println("\n-----------------------------------------------------------------------\n");
 }

 }
 */
    }
}
