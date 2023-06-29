package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.AppServer;
import it.polimi.ingsw.client.view.FXML.View;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.ModelView.PlayerView;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.JoinErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ReloadGameErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ResumeGameErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.*;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.Socket.ServerStub;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.observer_observable.Observable;
import java.io.PrintStream;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
/**
 * <h1>Class CLI</h1>
 * The class CLI extends the Observable abstract class and implements all the methods of the View interface.
 * This class is used to manage the command line interface
 *
 * @author Francesco Santambrogio, Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/06/2023
 */
public class CLI extends Observable<EventMessage> implements View {

    private String nickname;
    private PrintStream out;
    private Scanner in;


    /**
     * This method is used to run the CLI
     * First, it prints the Logo of MyShelfie. Then it tries to establish a connection to the {@link Server} either
     * with Socket or RMI, according to the input of the User.
     */
    public void run() {
        printTitle();
        try {
            createConnection();
        } catch (RemoteException ignored) {

        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to print the logo of MyShelfie on the CLI
     */
    private void printLogo() {
        out = new PrintStream(System.out);
        out.println("myShelfie\n");
    }


    @Override
    public void createConnection() throws RemoteException, NotBoundException {
        in = new Scanner(System.in);
        String connectionType = askConnectionType();
        String address = askServerAddress();
        int port = askServerPort();

        switch (connectionType) {
            case "r" -> {
                try {
                    Registry registry = LocateRegistry.getRegistry(address, port);
                    AppServer server = (AppServer) registry.lookup("MyShelfieServer");
                    new ClientImplementation(this, server.connect());
                    askNickname();
                } catch (NotBoundException e) {
                    System.err.println("Error: not bound exception registry");
                } catch (RemoteException re) {
                    System.err.println("Couldn't connect to server " + address + " on port " + port);
                    createConnection();
                }
            }
            case "s" -> {
                port = (port == 1099) ? 1244 : port;
                ServerStub serverStub = new ServerStub(address, port);
                Client client = new ClientImplementation(this, serverStub);
                serverStub.register(client);
                new Thread(() -> {
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
                }).start();

                askNickname();
            }
        }
    }


    @Override
    public String askConnectionType() {
        String connectionType;
        do {
            out.print("Please insert a connection type: socket (s) or RMI (r): ");
            connectionType = in.nextLine();
            if (!connectionType.equals("s") && !connectionType.equals("r")) System.err.println("Error: invalid choice");
        } while (!connectionType.equals("s") && !connectionType.equals("r"));
        return connectionType;

    }


    @Override
    public String askServerAddress(){
        String address;
        do  {
            out.print("Please insert the server address (press ENTER for localhost): ");
            address = in.nextLine();
            if (!isValidIPAddress(address) && !address.equals("")) System.err.println("Error: invalid choice");
        } while (!isValidIPAddress(address) && !address.equals(""));
        if(address.equals("")){
            out.println("Using default address...");
            address = "127.0.0.1";
        }
        return address;
    }



    @Override
    public int askServerPort(){
        String serverPort;
        do {
            out.print("Please insert the ip port (press ENTER for default): ");
            serverPort = in.nextLine();
            if (serverPort.equals("")){
                out.println("Using default port...");
                return 1099;
            }
        } while (!isValidPort(serverPort));

        return Integer.parseInt(serverPort);
    }



    @Override
    public boolean isValidPort(String serverPort) {
        try {
            int port = Integer.parseInt(serverPort);
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e){
            out.println("Error: input is not a number!");
            return false;
        }
    }


    @Override
    public boolean isValidIPAddress(String address) {
        String regExp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return address.matches(regExp);
    }




    @Override
    public void gameMenu() {
        printMenu();
        boolean validOption;
        do  {
            validOption = false;
            int menuOption = 0;
            while (!validOption) {
                try {
                    out.print(">>> ");
                    String input = in.nextLine();
                    menuOption = Integer.parseInt(input);
                    if (menuOption > 0) validOption = true;
                } catch (NumberFormatException e) {
                    System.err.println("\nThis is not a valid input, please insert a number");
                }
            }

            switch (menuOption) {

                case 1 -> createGame();
                case 2 -> joinGame();
                case 3 -> resumeGame();
                case 4 -> reloadGame();
                case 5 -> {
                    out.println("Closing game...");
                    setChanged();
                    notifyObservers(new ExitMessage(getNickname()));

                }
                default -> {
                    validOption = false;
                    System.err.println("Invalid option, please try again");
                }
            }
        } while (!validOption);

    }


    @Override
    public void resumeGame() {
        setChanged();
        notifyObservers(new ResumeGameMessage(getNickname()));
    }


    @Override
    public void reloadGame() {
        setChanged();
        notifyObservers(new ReloadGameMessage(getNickname()));
    }

    /**
     * After a user has decided to create a game, he/she is asked to the specs of the game: the game name and
     * the number of players that will play the game
     */
    private void createGame() {
        askGameSpecs();
    }

    @Override
    public void joinGame(){
        setChanged();
        notifyObservers(new JoinGameMessage(getNickname()));
    }


    @Override
    public void askNumberOfPlayers(String gameName) {
        int numOfPlayers = 0;
            while (numOfPlayers <= 0) {
                boolean isNumber;
                do {
                    isNumber = true;
                    out.print("Please insert the number of players: ");
                    try {
                        numOfPlayers = in.nextInt();
                        in.nextLine();
                    } catch (InputMismatchException e) {
                        isNumber= false;
                    }
                }while (!isNumber);

            }
            setChanged();
            notifyObservers(new GameCreationMessage(getNickname(), numOfPlayers, gameName));
    }





    @Override
    public void askGameSpecs(){

        String gameName = "";
        while (gameName.length() < 1) {
            out.print(getNickname() + " choose your game's name: ");
            gameName = in.nextLine();
            if (gameName.length() < 1) System.err.println("Please insert at least one character");
        }

        boolean isValidNumber = false;
        int numOfPlayers = 0;
        while (!isValidNumber) {
            try {
                out.print("Please insert the number of players: ");
                String input = in.nextLine();
                numOfPlayers = Integer.parseInt(input);
                if (numOfPlayers >= 2 && numOfPlayers <= 4) isValidNumber = true;
                else{
                    out.println("Players in range 2 to 4");
                }
            } catch (NumberFormatException e) {
                System.err.println("\nThis is not a valid input, please insert a number");
            }
        }
        setChanged();
        notifyObservers(new GameSpecsMessage(getNickname(),gameName, numOfPlayers));
    }


    @Override
    public void askGameName() {

        String gameName = "";
        while (gameName.length() < 1) {
            out.print(getNickname() + " choose your game's name: ");
            gameName = in.nextLine();
        }
        setChanged();
        notifyObservers(new GameNameMessage(getNickname(),gameName));

    }



    @Override
    public void askNickname() {
        String nickName = "";
        while (nickName.length() < 1) {
            out.print("Please insert your name: ");
            nickName = in.nextLine();
            this.nickname = nickName;
        }
        setChanged();
        notifyObservers(new NicknameMessage(nickName));
    }


    @Override
    public void showGameNamesList(Set<String> availableGameNames) {
        if (availableGameNames.isEmpty()){
            out.println("No games available, please create a new one!");
        } else {
            out.println("Available games in lobby:\n");
            for (String game : availableGameNames) {
                out.println("> " + game);
            }
        }
        String gameChoice;
        do {
            out.print("Enter the game's name to join: ");
            gameChoice = in.nextLine();

            if(!availableGameNames.contains(gameChoice)) {
                System.err.println("\nInvalid game choice");
            }
        }while(!availableGameNames.contains(gameChoice));

        setChanged();
        notifyObservers(new GameNameChoiceMessage(getNickname(), gameChoice));
    }

    public void printTitle() {
        out = new PrintStream(System.out);
        out.println(MessageCLI.TITLE);
    }

    /**
     * Method that prints the menu on command line interface
     */
    public void printMenu() {
        out.println(MessageCLI.MENU);
    }

    /**
     * Getter method
     * @return the nickname
     */
    public String getNickname () {
        return this.nickname;
    }


    @Override
    public void showMessage(MessageToClient message) {

        switch (message.getType()) {

            case GAME_SPECS -> {
                GameSpecsResponseMessage gameSpecsResponseMessage = (GameSpecsResponseMessage) message;
                if (gameSpecsResponseMessage.isValidGameName()) {
                    System.out.println("Available game name 😊 ");
                } else {
                    System.err.println("The game name is already taken, please choose another game name");
                    askGameSpecs();
                }
                if(gameSpecsResponseMessage.isValidGameCreation()){
                    System.out.println("Valid number of players 😊 ");
                    System.out.println("Waiting for other players... ");
                } else {
                    System.err.println("\nInvalid number of players, please choose a number within the available range (2-4)");
                    askGameSpecs();
                }
            }

            case LOGIN_RESPONSE -> {
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                if (loginResponseMessage.isValidNickname()) {
                    System.out.println("Available nickname " + loginResponseMessage.getNickname() + " 😊");
                    new Thread(() -> {
                        try {
                            while (true) {
                                setChanged();
                                notifyObservers(new PingMessage(loginResponseMessage.getNickname()));
                                Thread.sleep(5000);
                            }
                        } catch (RuntimeException | InterruptedException e) {
                            System.err.println(e.getMessage());
                            try {
                                createConnection();
                            } catch (RemoteException | NotBoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }).start();
                    gameMenu();
                } else {
                        System.err.println("\nInvalid nickname, please choose another one");
                        askNickname();
                    }

                }

            case JOIN_GAME_RESPONSE -> {
                JoinGameResponseMessage joinGameResponseMessage= (JoinGameResponseMessage) message;
                showGameNamesList(joinGameResponseMessage.getAvailableGamesInLobby());

            }

            case RESUME_GAME_RESPONSE -> out.println("Waiting to resume the game...");

            case JOIN_GAME_ERROR -> {
                JoinErrorMessage joinErrorMessage = (JoinErrorMessage) message;
                System.err.println(joinErrorMessage.getErrorMessage());
                gameMenu();
            }

            case RELOAD_GAME_ERROR -> {
                ReloadGameErrorMessage reloadGameErrorMessage = (ReloadGameErrorMessage) message;
                System.err.println(reloadGameErrorMessage.getErrorMessage());
                gameMenu();
            }

            case RESUME_GAME_ERROR -> {
                ResumeGameErrorMessage resumeGameErrorMessage = (ResumeGameErrorMessage) message;
                System.err.println(resumeGameErrorMessage.getErrorMessage());
                gameMenu();
            }

            case WAIT_PLAYERS -> {
                //assert message instanceof WaitingResponseMessage;
                WaitingResponseMessage waitingResponseMessage = (WaitingResponseMessage) message;
                if (waitingResponseMessage.getMissingPlayers() == 1) {
                    out.println("Waiting for 1 player... ");
                } else {
                    out.println("Waiting for " + waitingResponseMessage.getMissingPlayers() + " players... ");
                }
            }
            case PLAYER_JOINED_LOBBY_RESPONSE -> {
                PlayerJoinedLobbyMessage player = (PlayerJoinedLobbyMessage) message;
                out.println("\n" + player.getNickname() + " joined lobby");
            }
            case ILLEGAL_POSITION, UPPER_BOUND_TILEPACK, NOT_ENOUGH_INSERTABLE_TILES -> {
                    ErrorMessage errorMessage = (ErrorMessage) message;
                    out.println(errorMessage.getErrorMessage());
                    String answer;
                    do {
                        out.println("\nIf you wish to stop picking tiles type 'stop', otherwise press ENTER");
                        out.print(">>> ");
                        answer = in.nextLine();
                        switch (answer) {
                            case "stop" -> {
                                setChanged();
                                notifyObservers(new SwitchPhaseMessage(errorMessage.getNickname(), GamePhase.COLUMN_CHOICE));
                            }
                            case "" -> {
                                boolean validRow = false;
                                int r = 0;
                                while (!validRow) {
                                    try {
                                        out.print("r: ");
                                        String input = in.nextLine();
                                        r = Integer.parseInt(input);
                                        in.nextLine();
                                        if (r >= 0) validRow = true;
                                    } catch (NumberFormatException e) {
                                        System.err.println("\nThis is not a valid input, please insert a number");
                                    }
                                }

                                in.nextLine();

                                boolean validColumn = false;
                                int c = 0;
                                while (!validColumn) {
                                    try {
                                        out.print("c: ");
                                        String input = in.nextLine();
                                        c = Integer.parseInt(input);
                                        in.nextLine();
                                        if (c >= 0) validColumn = true;
                                    } catch (NumberFormatException e) {
                                        System.err.println("\nThis is not a valid input, please insert a number");
                                    }
                                }

                                in.nextLine();

                                setChanged();
                                notifyObservers(new TilePositionMessage(message.getNickname(), new Position(r, c)));
                            }
                        }
                    }while (!answer.equals("stop") && !answer.equals(""));
            }

            case NOT_ENOUGH_INSERTABLE_TILES_IN_COLUMN -> {
                ErrorMessage errorMessage = (ErrorMessage) message;
                out.println(errorMessage.getErrorMessage());
                out.print("In which column you want to insert your item tiles?\n");

                boolean validColumn = false;
                int column = 0;
                while (!validColumn) {
                    try {
                        out.print("column: ");
                        String input = in.nextLine();
                        column = Integer.parseInt(input);
                        in.nextLine();
                        if (column >= 0) validColumn = true;
                    } catch (NumberFormatException e) {
                        System.err.println("\nThis is not a valid input, please insert a number");
                    }
                }

                in.nextLine();

                setChanged();
                notifyObservers(new BookshelfColumnMessage(message.getNickname(), column));
            }

            case WAIT_FOR_OTHER_PLAYERS -> out.println("Waiting for other players to reconnect...");

            case CLIENT_DISCONNECTION -> out.println(message.getNickname() + " has disconnected");
        }
    }




    /**
     * This method overrides the {@code Observer update}: it is called by the {@link Server} when an {@link EventMessage} is generated
     *  from the Model.
     *  According to the {@EventMessage #type}, this method interacts differently with the User
     * @param game the Observable class
     * @param eventMessage the specific event of which the class is notified
     */
    public void update(GameView game, EventMessage eventMessage) {
        switch (eventMessage.getType()) {

            case PLAYER_TURN -> {
                if (this.nickname.equals(game.getCurrentPlayer().getName())) {
                    out.println("\n" + eventMessage.getNickname() + ", it's your turn!");
                    out.println("\nPlease enter a position: ");

                    boolean validRow = false;
                    int r = 0;
                    while (!validRow) {
                        try {
                            out.print("r: ");
                            String input = in.nextLine();
                            r = Integer.parseInt(input);
                            in.nextLine();
                            if (r >= 0) validRow = true;
                        } catch (NumberFormatException e) {
                            System.err.println("\nThis is not a valid input, please insert a number");
                        }
                    }

                    in.nextLine();

                    boolean validColumn = false;
                    int c = 0;
                    while (!validColumn) {
                        try {
                            out.print("c: ");
                            String input = in.nextLine();
                            c = Integer.parseInt(input);
                            in.nextLine();
                            if (c >= 0) validColumn = true;
                        } catch (NumberFormatException e) {
                            System.err.println("\nThis is not a valid input, please insert a number");
                        }
                    }

                    in.nextLine();

                    setChanged();
                    notifyObservers(new TilePositionMessage(eventMessage.getNickname(), new Position(r, c)));
                }
            }

            case BOARD -> {

                out.println(game.toCLI(getNickname()));

                if (!this.nickname.equals(game.getCurrentPlayer().getName())) {
                    out.println("\nIt's " + eventMessage.getNickname() + "'s turn\n");
                }
            }


            case TILE_PACK -> out.println(game.toCLI(getNickname()));

            case PICKING_TILES -> {
                if (this.nickname.equals(game.getCurrentPlayer().getName())) {
                    out.println(game.toCLI(getNickname()));
                    String answer;
                    do {
                        out.println("\nIf you wish to stop picking tiles type 'stop', otherwise press ENTER");
                        out.print(">>> ");
                        answer = in.nextLine();
                        switch (answer) {
                            case "stop" -> {
                                setChanged();
                                notifyObservers(new SwitchPhaseMessage(getNickname(), GamePhase.COLUMN_CHOICE));
                            }
                            case "" -> {
                                out.println("\nPlease enter a position: ");

                                boolean validRow = false;
                                int r = 0;
                                while (!validRow) {
                                    try {
                                        out.print("r: ");
                                        String input = in.nextLine();
                                        r = Integer.parseInt(input);
                                        in.nextLine();
                                        if (r >= 0) validRow = true;
                                    } catch (NumberFormatException e) {
                                        System.err.println("\nThis is not a valid input, please insert a number");
                                    }
                                }

                                in.nextLine();

                                boolean validColumn = false;
                                int c = 0;
                                while (!validColumn) {
                                    try {
                                        out.print("c: ");
                                        String input = in.nextLine();
                                        c = Integer.parseInt(input);
                                        in.nextLine();
                                        if (c >= 0) validColumn = true;
                                    } catch (NumberFormatException e) {
                                        System.err.println("\nThis is not a valid input, please insert a number");
                                    }
                                }

                                in.nextLine();

                                setChanged();
                                notifyObservers(new TilePositionMessage(eventMessage.getNickname(), new Position(r, c)));
                            }
                            case "Patrick" -> {
                                setChanged();
                                notifyObservers(new EasterEggMessage(eventMessage.getNickname()));
                            }
                        }
                    } while (!answer.equals("stop") && !answer.equals("") && !answer.equals("Patrick"));
                }
            }

            case COLUMN_CHOICE -> {
                if (getNickname().equals(game.getCurrentPlayer().getName())) {
                    out.print("In which column you want to insert your item tiles?\n");

                    boolean validColumn = false;
                    int column = 0;
                    while (!validColumn) {
                        try {
                            out.print("column: ");
                            String input = in.nextLine();
                            column = Integer.parseInt(input);
                            in.nextLine();
                            if (column >= 0) validColumn = true;
                        } catch (NumberFormatException e) {
                            System.err.println("\nThis is not a valid input, please insert a number");
                        }
                    }

                    in.nextLine();

                    setChanged();
                    notifyObservers(new BookshelfColumnMessage(eventMessage.getNickname(), column));
                }
            }

            case BOOKSHELF -> {
                out.println(game.toCLI(getNickname()));
                if (!getNickname().equals(game.getCurrentPlayer().getName())) {
                    out.println("\n" + eventMessage.getNickname() + " is inserting tiles in the bookshelf\n");
                }
            }

            case PLACING_TILES -> {
                if (getNickname().equals(game.getCurrentPlayer().getName())) {
                    if (game.getTilePack().getTiles().size() > 0) {
                        out.print("Choose an item tile to insert from the tile pack into the selected column\n");

                        boolean validIndex = false;
                        int itemTileIndex = 0;
                        while (!validIndex) {
                            try {
                                out.print("index: ");
                                String input = in.nextLine();
                                itemTileIndex = Integer.parseInt(input);
                                in.nextLine();
                                if (itemTileIndex >= 0) validIndex = true;
                            } catch (NumberFormatException e) {
                                System.err.println("\nThis is not a valid input, please insert a number");
                            }
                        }

                        in.nextLine();

                        setChanged();
                        notifyObservers(new ItemTileIndexMessage(eventMessage.getNickname(), itemTileIndex));
                    } else {
                        setChanged();
                        notifyObservers(new EndTurnMessage(eventMessage.getNickname()));
                    }
                }
            }

            case LAST_TURN -> {
                if (getNickname().equals(game.getCurrentPlayer().getName())) {
                    out.println("\nCongrats, you filled your bookshelf! You have an extra point!");
                } else {
                    out.println("\n" + eventMessage.getNickname() + " filled his bookshelf...");
                }
            }

            case END_GAME -> {
                out.println("\n");
                for (PlayerView playerView : game.getSubscribers()) {
                    out.println(playerView.getName() + "'s score is " + playerView.getScore() + " points");
                }
                if (eventMessage.getNickname().equals(getNickname())) {
                    out.println("\nCongratulations, you won 😊!");
                }
                out.println("\nGame has ended... Hope you had fun!");
                setChanged();
                notifyObservers(new DisconnectClientMessage(eventMessage.getNickname()));

                new Thread(() -> {
                    try {
                        gameMenu();
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                    }
                }).start();

            }
        }
    }
}
