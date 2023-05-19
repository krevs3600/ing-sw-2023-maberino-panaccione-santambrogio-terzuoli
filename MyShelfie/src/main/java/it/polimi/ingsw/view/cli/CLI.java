package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.AppServer;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.MessagesToServer.MessageToClient;
import it.polimi.ingsw.network.MessagesToServer.errorMessages.JoinErrorMessage;
import it.polimi.ingsw.network.MessagesToServer.requestMessage.*;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.Socket.ServerStub;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.observer_observable.Observable;

import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Set;

public class CLI extends Observable {
    private boolean activeTurn = false;

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

        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printLogo() {
        out.println("myShelfie");
    }

    private void createConnection() throws RemoteException, NotBoundException {
        String connectionType = askConnectionType();
        String address = askServerAddress();
        int port = askServerPort();

        switch (connectionType) {
            case "r" -> {
                try {
                    Registry registry = LocateRegistry.getRegistry(address, port);
                    AppServer server = (AppServer) registry.lookup("MyShelfieServer");

                    this.client = new ClientImplementation(this, server.connect());
                   askNickname();
                } catch (NotBoundException e) {
                    System.err.println("not bound exception registry");
                }
            }
            case "s" -> {
                port = (port == 1243) ? 1244 : port;
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

                askNickname();
            }
        }
    }

    private String askConnectionType() {
        String connectionType = "";
        do {
            out.print("Please insert a connection type: socket (s) or RMI (r): ");
            connectionType = in.nextLine();
            if (!connectionType.equals("s") && !connectionType.equals("r")) System.err.println("invalid choice");
        } while (!connectionType.equals("s") && !connectionType.equals("r"));
        return connectionType;
    }

    public String askServerAddress(){
        String address;
        do  {
            out.print("Please insert the server address (press ENTER for localhost): ");
            address = in.nextLine();
        } while (!isValidIPAddress(address) && !address.equals(""));
        if(address.equals("")){
            out.println("Using default address...");
            address = "127.0.0.1";
        }
        return address;
    }

    private int askServerPort(){
        String serverPort;
        do {
            out.print("Please insert the ip port (press ENTER for default): ");
            serverPort = in.nextLine();
            if (serverPort.equals("")){
                out.println("Using default port...");
                return 1243;
            }
        } while (!isValidPort(serverPort));

        return Integer.parseInt(serverPort);
    }

    private boolean isValidPort(String serverPort) {
        try {
            int port = Integer.parseInt(serverPort);
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e){
            System.out.println("Input is not a number!");
            return false;
        }
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
        out.print(">>> ");
        int menuOption = in.nextInt();
        in.nextLine();

        switch (menuOption) {
            case 1 -> {
                createGame();
            }
            case 2 -> {
                joinGame();

            }
            default -> {
                out.println("Invalid option, please try again");
            }
        }

    }

    private void createGame() {
        askGameName();
    }

    private void joinGame(){
        setChanged();
        notifyObservers(new JoinGameMessage(this.client.getNickname()));

    }

    private void askNumberOfPlayers(String gameName) {
            int numOfPlayers = 0;
            while (numOfPlayers <= 0) {
                out.print("Please insert the number of players: ");
                numOfPlayers = in.nextInt();
                in.nextLine();
            }
            setChanged();
            notifyObservers(new GameCreationMessage(this.client.getNickname(), numOfPlayers, gameName));
    }

    private void askGameName() {

        String gameName = "";
        while (gameName.length() < 1) {
            out.print(this.client.getNickname() + " choose your game's name : ");
            gameName = in.nextLine();
        }
        setChanged();
        notifyObservers(new GameNameMessage(this.client.getNickname(),gameName));

    }

    private void askNickname() {
        String nickName = "";
        while (nickName.length() < 1) {
            out.print("Please insert your name: ");
            nickName = in.nextLine();
        }
      //  return nickName;
        setChanged();
        notifyObservers(new NicknameMessage(nickName));
    }

    public void showGameNamesList(Set<String> availableGameNames) {
        if (availableGameNames.isEmpty()){
            out.println("No games available, please create a new one!");
        } else {
            for (String game : availableGameNames) {
                out.println("> " + game);
            }
        }
        String gameChoice = null;
        do {
            out.print("Enter the game's name to join: ");
            gameChoice = in.nextLine();

            if(!availableGameNames.contains(gameChoice)) {
                System.err.println("Invalid game choice, spell it right!");
            }
        }while(!availableGameNames.contains(gameChoice));

        setChanged();
        notifyObservers(new GameNameChoiceMessage(this.client.getNickname(), gameChoice));
    }

    public void printTitle() {
        out.println(MessageCLI.TITLE);
    }

    public void printMenu() {
        out.println(MessageCLI.MENU);
    }

    public void showMessage(MessageToClient message) {

        switch (message.getType()) {
           // case CREATOR_LOGIN_RESPONSE -> {
           //     CreatorLoginResponseMessage creatorLoginResponseMessage = (CreatorLoginResponseMessage) message;
           //     if (creatorLoginResponseMessage.isValidNickname()) {
           //         System.out.println("Available nickname " + this.client.getNickname() + " :)\n");
           //         askGameName();
           //     } else {
           //         System.err.println("Invalid nickname, please choose another one\n");
           //         askNickname();
           //     }



            case GAME_NAME_RESPONSE -> {
                GameNameResponseMessage gameNameResponseMessage = (GameNameResponseMessage) message;
                if (gameNameResponseMessage.isValidGameName()) {
                    System.out.println("Available game name :) ");
                    askNumberOfPlayers(gameNameResponseMessage.getGameName());
                } else {
                    System.err.println("The game name is already taken, please choose another game name");
                    askGameName();
                }
            }
            //this case is related to the insertion of the number of players
            case GAME_CREATION -> {
                GameCreationResponseMessage gameCreationResponseMessage = (GameCreationResponseMessage) message;
                if (gameCreationResponseMessage.isValidGameCreation()) {
                    System.out.println("Valid number of players :) ");
                    System.out.println("Waiting for other players... ");
                } else {
                    System.err.println("Invalid number of players, please choose a number within the available range");
                    askNumberOfPlayers(this.client.getGameName());
                }
            }
            case LOGIN_RESPONSE -> {
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                if (loginResponseMessage.isValidNickname()) {
                    System.out.println("Available nickname " + this.client.getNickname() + " :)");
                    gameMenu();
                    //showGameNamesList(loginResponseMessage.getAvailableGames());

                } else {
                   // if (loginResponseMessage.getNickname().equals(this.client.getNickname())) {
                   //     System.err.println("No games available, returning to the main menu ");
                   //     printMenu();
                        System.err.println("Invalid nickname, please choose another one\n");
                        askNickname();
                    }

                }

            case JOIN_GAME_RESPONSE -> {
                JoinGameResponseMessage joinGameResponseMessage= (JoinGameResponseMessage) message;
                showGameNamesList(joinGameResponseMessage.getAvailableGamesInLobby());
            }
            case JOIN_GAME_ERROR -> {
                JoinErrorMessage joinErrorMessage = (JoinErrorMessage) message;
                System.err.println(joinErrorMessage.getErrorMessage());
                gameMenu();
            }
            case WAIT_PLAYERS -> {
                WaitingResponseMessage waitingResponseMessage = (WaitingResponseMessage) message;
                if (waitingResponseMessage.getMissingPlayers() == 1) {
                    out.println("Waiting for 1 player... ");
                } else {
                    out.println("Waiting for " + waitingResponseMessage.getMissingPlayers() + "players... ");
                }
            }
            case PLAYER_JOINED_LOBBY_RESPONSE -> {
                PlayerJoinedLobbyMessage player = (PlayerJoinedLobbyMessage) message;
                out.println(player.getNickname() + " joined lobby");
            }
            case ILLEGAL_POSITION -> {
                if (activeTurn) {
                    out.println("IllegalAccessError, position is not valid. Please try again...");
                    out.print("r: ");
                    int r = in.nextInt();
                    in.nextLine();
                    System.out.print("c: ");
                    int c = in.nextInt();
                    in.nextLine();
                    setChanged();
                    notifyObservers(new TilePositionMessage(((IllegalTilePositionErrorMessage)message).getNickname(), new Position(r, c)));
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
            case PLAYER_TURN -> {
                if (this.client.getNickname().equals(eventMessage.getNickName())){
                    activeTurn = true;
                    out.println(this.client.getNickname() + " is your turn!");

                    boolean stopPickingTiles = false;
                    out.println("Please enter a position: ");
                    out.print("r: ");
                    int r = in.nextInt();
                    in.nextLine();
                    System.out.print("c: ");
                    int c = in.nextInt();
                    in.nextLine();
                    setChanged();
                    notifyObservers(new TilePositionMessage(eventMessage.getNickName(), new Position(r, c)));
                } else {
                    activeTurn = false;
                }
            }
            case BOARD -> {
                System.out.println("--- NEW TURN ---");
                out.println("\n-----------------------------------------------------------------------\n LIVING ROOM BOARD:");
                out.println(game.getLivingRoomBoard().toString());
                /*out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
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

                 */
            }

            case TILE_POSITION -> {
                out.println("\n-----------------------------------------------------------------------\n LIVING ROOM BOARD:");
                out.println(game.getLivingRoomBoard().toString());
            }

            case TILE_PACK -> {
                if (activeTurn){
                    out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
                    out.println(game.getTilePack().toString());
                    String answer = "";
                    do {
                        out.println("\nIf you wish to stop picking tiles type 'stop', otherwise press ENTER");
                        out.print(">>> ");
                        answer = in.nextLine();
                        if (answer.equals("stop")) {
                            out.println("\n-----------------------------------------------------------------------\n" + game.getCurrentPlayer().getName() + "'s BOOKSHELF:");
                            out.println(game.getCurrentPlayer().getBookshelf().toString());
                            out.println("\n-----------------------------------------------------------------------\n");
                            out.print("In which column you want to insert your item tiles?\n");
                            int column = in.nextInt();
                            in.nextLine();
                            setChanged();
                            notifyObservers(new BookshelfColumnMessage(eventMessage.getNickName(), column));
                        }
                        else if (answer.equals("")){
                            out.println("Please enter a position: ");
                            out.print("r: ");
                            int r = in.nextInt();
                            in.nextLine();
                            System.out.print("c: ");
                            int c = in.nextInt();
                            in.nextLine();
                            setChanged();
                            notifyObservers(new TilePositionMessage(eventMessage.getNickName(), new Position(r, c)));
                        }
                    } while (!answer.equals("stop") && !answer.equals("continue"));
                }

            }

            case BOOKSHELF -> {
                out.println("\n-----------------------------------------------------------------------\n" + game.getSubscribers().get(0).getName() + "'s BOOKSHELF:");
                out.println(game.getSubscribers().get(0).getBookshelf().toString());
                this.setState(State.WAITING_FOR_PLAYER);
            }

            case INSERTION_REQUEST -> {
                if (activeTurn){
                    out.println("\n-----------------------------------------------------------------------\n" + game.getSubscribers().get(0).getName() + "'s BOOKSHELF:");
                    out.println(game.getCurrentPlayer().getBookshelf().toString());
                    out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
                    out.println(game.getTilePack().toString());
                    if (game.getTilePack().getTiles().size()>0) {
                        out.print("Choose an item tile to insert from the tilepack into the selected column\n");
                        int itemTileIndex = in.nextInt();
                        setChanged();
                        notifyObservers(new ItemTileIndexMessage(eventMessage.getNickName(), itemTileIndex));
                    } else {
                        activeTurn = false;
                        setChanged();
                        notifyObservers(new EndTurnMessage(eventMessage.getNickName()));
                    }
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
