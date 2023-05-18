package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.AppServer;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.EventMessage;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.Socket.ServerStub;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.network.eventMessages.RequestMessage.GameNameResponseMessage;
import it.polimi.ingsw.network.eventMessages.RequestMessage.LoginResponseMessage;
import it.polimi.ingsw.network.eventMessages.RequestMessage.RequestMessage;
import it.polimi.ingsw.observer_observable.Observable;

import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CLI extends Observable {

    private final PrintStream out = System.out;
    private final Scanner in = new Scanner(System.in);
    private final Object lock = new Object();
    private boolean joined = false;

    private ClientImplementation client;




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
    public void run() {
        printLogo();
        try {
            createConnection();
        } catch (RemoteException e) {
            out.println("bro dinne una giusta");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printLogo() {
        out.println("Hello MyShelfie is here");
    }

    private void createConnection() throws RemoteException, NotBoundException {
        String connectionType = askConnectionType();
        String address = askServerAddress();
        int port = askServerPort();

        switch (connectionType) {
            case "r" -> {
                try {
                    Registry registry = LocateRegistry.getRegistry(port);
                    AppServer server = (AppServer) registry.lookup("MyShelfieServer");


                    ClientImplementation client = new ClientImplementation(this, server.connect());
                    this.client=client;
                    gameMenu();
                } catch (NotBoundException e) {
                    System.err.println("not bound exception registry");
                }
            }
            case "s" -> {
                ServerStub serverStub = new ServerStub(address, port);
                ClientImplementation client = new ClientImplementation(this, serverStub);
                new Thread() {
                    @Override
                    public void run() {
                        while(true) {
                            try {
                                serverStub.receive(client);
                            } catch (RemoteException e) {
                                System.err.println("Cannot receive from server. Stopping...");
                                try {
                                    serverStub.close();
                                } catch (RemoteException ex) {
                                    System.err.println("Cannot close connection with server. Halting...");
                                }
                                System.exit(1);
                            }
                        }
                    }
                }.start();

                gameMenu();
            }
        }
    }

    private String askConnectionType() {
        String connectionType = "";
        do {
            out.print("Please insert a connection type: socket (s) or RMI (r) ");
            connectionType = in.next();
            if (!connectionType.equals("s") && !connectionType.equals("r")) System.err.println("invalid choice");
        } while (!connectionType.equals("s") && !connectionType.equals("r"));
        return connectionType;
    }

    public String askServerAddress(){
        String address;
        do  {
            out.print("Please insert the address of the server you want to connect to, otherwise press ENTER if you want the local host one (127.0.0.1): ");
            address = in.next();
        } while (!isValidIPAddress(address) || address.equals("\n"));
        if(address.equals("\n")){
            address = "127.0.0.1";
        }
        return address;
    }

    private int askServerPort(){
        int serverPort;
        do {
            out.print("Please insert the IP of the server you want to connect to. If you type an invalid port, the default one will be used (1234): ");
            try {
                serverPort = Integer.parseInt(in.next());
            } catch (NumberFormatException e){
                out.print("Using default port");
                serverPort = 1234;
            }
        } while (!isValidPort(serverPort));

        return serverPort;
    }

    private boolean isValidPort(int port) {
        return port >= 1024 && port < 65535;
    }

    private boolean isValidIPAddress(String address) {
        String regExp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return address.matches(regExp);
    }

 /*   @Override
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


  */


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
    }
         */


    public void gameMenu() {
        printMenu();
        int menuOption = in.nextInt();

        switch (menuOption) {
            case 1 -> {
                createGame();
            }
            case 2 -> {

            }
            default -> {
                out.println("Invalid option, please try again");
            }
        }

    }

    private void createGame() {
        askNickname();
        askGameName();
        askNumberOfPlayers();
    }

    private void askNumberOfPlayers() {
    }

    private void askGameName() {

        String GameName = "";
        while (GameName.length() < 1) {
            out.println("Please insert the name for your game : ");
            GameName = in.next();
        }
        setChanged();
        notifyObservers(new GameNameMessage(this.client.getNickname(),GameName));

    }

    private void askNickname() {
        String nickName = "";
        while (nickName.length() < 1) {
            out.println("Please insert your name: ");
            nickName = in.next();
        }
        setChanged();
        notifyObservers(new NicknameMessage(nickName));

    }

    public void printTitle() {
        out.println(MessageCLI.TITLE);
    }

    public void printMenu() {
        out.println(MessageCLI.MENU);
    }


    public void showMessage(RequestMessage message) {

        switch (message.getType()) {
            case LOGIN_RESPONSE -> {
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                if (loginResponseMessage.isValidNickname()) {
                    System.out.println("Available nickname :)");
                }
                else {
                    System.err.println("Invalid nickname, please choose another one");
                    askNickname();
                }


            }
            case GAMENAME_RESPONSE -> {
                GameNameResponseMessage gameNameResponseMessage=(GameNameResponseMessage) message;
                if(gameNameResponseMessage.isValidGameName()){
                    System.out.println("Available GameName " );
                }
                else {
                    System.out.println("There is alredy a gameName with that Name, plese choose another GameName");
                    askGameName();
                }
            }
        }
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
