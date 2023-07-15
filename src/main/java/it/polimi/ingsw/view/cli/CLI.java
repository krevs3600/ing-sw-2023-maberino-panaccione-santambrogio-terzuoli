package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.AppServer;
import it.polimi.ingsw.view.View;
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    private Scanner in;
    private Printer out;

    private class Printer {
        private final PrintStream out;
        private Printer(PrintStream out){
            this.out = out;
        }
        private void printToCLI(String string, ColorCLI color){
            out.print(color.getCode() + string + ColorCLI.RESET.getCode());
        }
    }


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
                    out.printToCLI("Error: not bound exception registry\n", ColorCLI.RED_T);
                } catch (RemoteException re) {
                    out.printToCLI("Couldn't connect to server " + address + " on port " + port + "\n", ColorCLI.RED_T);
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
                            out.printToCLI("Cannot receive from server. Stopping...\n", ColorCLI.RED_T);

                            try {
                                serverStub.close();
                            } catch (RemoteException ex) {
                                out.printToCLI("Cannot close connection with server. Halting...\n", ColorCLI.RED_T);
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
            out.printToCLI("Please insert a connection type: socket (s) or RMI (r): ", ColorCLI.YELLOW_T);
            connectionType = in.nextLine();
            if (!connectionType.equals("s") && !connectionType.equals("r")) {
                out.printToCLI("Error: invalid choice\n", ColorCLI.RED_T);
            }
        } while (!connectionType.equals("s") && !connectionType.equals("r"));
        return connectionType;

    }


    @Override
    public String askServerAddress(){
        String address;
        do  {
            out.printToCLI("Please insert the server address (press ENTER for localhost): ", ColorCLI.YELLOW_T);
            address = in.nextLine();
            if (!isValidIPAddress(address) && !address.equals("")) System.err.println("Error: invalid choice\n");
        } while (!isValidIPAddress(address) && !address.equals(""));
        if(address.equals("")){
            out.printToCLI("Using default address...\n", ColorCLI.PURPLE_T);
            address = "127.0.0.1";
        }
        return address;
    }



    @Override
    public int askServerPort(){
        String serverPort;
        do {
            out.printToCLI("Please insert the ip port (press ENTER for default): ", ColorCLI.YELLOW_T);
            serverPort = in.nextLine();
            if (serverPort.equals("")){
                out.printToCLI("Using default port...\n", ColorCLI.PURPLE_T);
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
            out.printToCLI("Error: input is not a number!\n", ColorCLI.RED_T);
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
                    out.printToCLI(">>> ", ColorCLI.WHITE_T);
                    String input = in.nextLine();
                    menuOption = Integer.parseInt(input);
                    if (menuOption > 0) validOption = true;
                } catch (NumberFormatException e) {
                    out.printToCLI("This is not a valid input, please insert a number\n", ColorCLI.RED_T);
                }
            }

            switch (menuOption) {

                case 1 -> createGame();
                case 2 -> joinGame();
                case 3 -> resumeGame();
                case 4 -> reloadGame();
                case 5 -> {
                    out.printToCLI("Closing game...\n", ColorCLI.RED_T);
                    setChanged();
                    notifyObservers(new ExitMessage(getNickname()));

                }
                default -> {
                    validOption = false;
                    out.printToCLI("Invalid option, please try again\n", ColorCLI.RED_T);
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
    public void askGameSpecs(){

        String gameName = "";
        while (gameName.length() < 1) {
            out.printToCLI(getNickname() + " choose your game's name: ", ColorCLI.CYAN_T);
            gameName = in.nextLine();
            if (gameName.length() < 1) {
                out.printToCLI("Please insert at least one character\n", ColorCLI.RED_T);
            }
        }

        boolean isValidNumber = false;
        int numOfPlayers = 0;
        while (!isValidNumber) {
            try {
                out.printToCLI("Please insert the number of players: ", ColorCLI.CYAN_T);
                String input = in.nextLine();
                numOfPlayers = Integer.parseInt(input);
                if (numOfPlayers >= 2 && numOfPlayers <= 4) {
                    isValidNumber = true;
                }
                else{
                    out.printToCLI("Players in range 2 to 4", ColorCLI.RED_T);
                }
            } catch (NumberFormatException e) {
                out.printToCLI("This is not a valid input, please insert a number\n", ColorCLI.RED_T);
            }
        }
        setChanged();
        notifyObservers(new GameSpecsMessage(getNickname(),gameName, numOfPlayers));
    }



    @Override
    public void askNickname() {
        String nickName = "";
        while (nickName.length() < 1) {
            out.printToCLI("Please insert your name: ", ColorCLI.CYAN_T);
            nickName = in.nextLine();
            this.nickname = nickName;
        }
        setChanged();
        notifyObservers(new NicknameMessage(nickName));
    }


    @Override
    public void showGameNamesList(Set<String> availableGameNames) {
        if (availableGameNames.isEmpty()){
            out.printToCLI("No games available, please create a new one!\n", ColorCLI.RED_T);
        } else {
            out.printToCLI("Available games in lobby:\n", ColorCLI.WHITE_T);
            for (String game : availableGameNames) {
                out.printToCLI("> " + game + "\n", ColorCLI.CYAN_T);
            }
        }
        String gameChoice;
        do {
            out.printToCLI("Enter the game's name to join: ", ColorCLI.CYAN_T);
            gameChoice = in.nextLine();

            if(!availableGameNames.contains(gameChoice)) {
                out.printToCLI("Invalid game choice\n", ColorCLI.RED_T);
            }
        }while(!availableGameNames.contains(gameChoice));

        setChanged();
        notifyObservers(new GameNameChoiceMessage(getNickname(), gameChoice));
    }

    public void printTitle() {
        out = new Printer(System.out);
        out.printToCLI(MessageCLI.TITLE + "\n", ColorCLI.RESET);
    }

    /**
     * Method that prints the menu on command line interface
     */
    public void printMenu() {
        out.printToCLI(MessageCLI.MENU + "\n", ColorCLI.BLUE_T);
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
                    out.printToCLI("Available game name\n", ColorCLI.GREEN_T);
                } else {
                    out.printToCLI("The game name is already taken, please choose another game name\n", ColorCLI.RED_T);
                    askGameSpecs();
                }
                if(gameSpecsResponseMessage.isValidGameCreation()){
                    out.printToCLI("Valid number of players\n", ColorCLI.GREEN_T);
                    out.printToCLI("Waiting for other players...\n", ColorCLI.YELLOW_T);
                } else {
                    out.printToCLI("Invalid number of players, please choose a number within the available range (2-4)", ColorCLI.RED);
                    askGameSpecs();
                }
            }

            case LOGIN_RESPONSE -> {
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                if (loginResponseMessage.isValidNickname()) {
                    out.printToCLI("Available nickname " + loginResponseMessage.getNickname(), ColorCLI.GREEN_T);
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
                        out.printToCLI("Invalid nickname, please choose another one\n", ColorCLI.RED_T);
                        askNickname();
                    }

            }

            case JOIN_GAME_RESPONSE -> {
                JoinGameResponseMessage joinGameResponseMessage= (JoinGameResponseMessage) message;
                showGameNamesList(joinGameResponseMessage.getAvailableGamesInLobby());

            }

            case RESUME_GAME_RESPONSE -> out.printToCLI("Waiting to resume the game...\n", ColorCLI.YELLOW_T);

            case JOIN_GAME_ERROR -> {
                JoinErrorMessage joinErrorMessage = (JoinErrorMessage) message;
                out.printToCLI(joinErrorMessage.getErrorMessage() + "\n", ColorCLI.RED_T);
                gameMenu();
            }

            case RELOAD_GAME_ERROR -> {
                ReloadGameErrorMessage reloadGameErrorMessage = (ReloadGameErrorMessage) message;
                out.printToCLI(reloadGameErrorMessage.getErrorMessage() + "\n", ColorCLI.RED_T);
                gameMenu();
            }

            case RESUME_GAME_ERROR -> {
                ResumeGameErrorMessage resumeGameErrorMessage = (ResumeGameErrorMessage) message;
                out.printToCLI(resumeGameErrorMessage.getErrorMessage() + "\n", ColorCLI.RED_T);
                gameMenu();
            }

            case WAIT_PLAYERS -> {
                //assert message instanceof WaitingResponseMessage;
                WaitingResponseMessage waitingResponseMessage = (WaitingResponseMessage) message;
                if (waitingResponseMessage.getMissingPlayers() == 1) {
                    out.printToCLI("Waiting for 1 player... \n", ColorCLI.YELLOW_T);
                } else {
                    out.printToCLI("Waiting for " + waitingResponseMessage.getMissingPlayers() + " players... \n",  ColorCLI.YELLOW_T);
                }
            }
            case PLAYER_JOINED_LOBBY_RESPONSE -> {
                PlayerJoinedLobbyMessage player = (PlayerJoinedLobbyMessage) message;
                out.printToCLI( player.getNickname() + " joined lobby\n", ColorCLI.GREEN_T);
            }
            case ILLEGAL_POSITION, UPPER_BOUND_TILEPACK, NOT_ENOUGH_INSERTABLE_TILES -> {
                    ErrorMessage errorMessage = (ErrorMessage) message;
                    out.printToCLI(errorMessage.getErrorMessage() + "\n", ColorCLI.RED_T);
                    String answer;
                    do {
                        out.printToCLI("\nIf you wish to stop picking tiles type 'stop', otherwise press ENTER\n", ColorCLI.CYAN_T);
                        out.printToCLI(">>> ", ColorCLI.WHITE_T);
                        answer = in.nextLine();
                        switch (answer) {
                            case "stop" -> {
                                setChanged();
                                notifyObservers(new SwitchPhaseMessage(errorMessage.getNickname(), GamePhase.COLUMN_CHOICE));
                            }
                            case "" -> {
                                Position position = askPositionChoice();
                                setChanged();
                                notifyObservers(new TilePositionMessage(message.getNickname(), position));
                            }
                        }
                    } while (!answer.equals("stop") && !answer.equals(""));
            }

            case NOT_ENOUGH_INSERTABLE_TILES_IN_COLUMN -> {
                ErrorMessage errorMessage = (ErrorMessage) message;
                out.printToCLI(errorMessage.getErrorMessage() + "\n", ColorCLI.RED_T);
                int column = askColumnChoice();
                setChanged();
                notifyObservers(new BookshelfColumnMessage(message.getNickname(), column));
            }

            case WAIT_FOR_OTHER_PLAYERS -> out.printToCLI("Waiting for other players to reconnect...\n", ColorCLI.YELLOW_T);

            case CLIENT_DISCONNECTION -> out.printToCLI(message.getNickname() + " has disconnected\n", ColorCLI.RED_T);
        }
    }




    /**
     * This method overrides the {@code Observer update}: it is called by the {@link Server} when an {@link EventMessage} is generated
     *  from the Model.
     *  According to the type of {@link EventMessage}, this method interacts differently with the User
     * @param game the Observable class
     * @param eventMessage the specific event of which the class is notified
     */
    public void update(GameView game, EventMessage eventMessage) {
        switch (eventMessage.getType()) {

            case PLAYER_TURN -> {
                if (this.nickname.equals(game.getCurrentPlayer().getName())) {
                    out.printToCLI("\n" + eventMessage.getNickname() + ", it's your turn!\n", ColorCLI.CYAN_T);
                    out.printToCLI("Please enter a position: \n", ColorCLI.CYAN_T);
                    Position position = askPositionChoice();
                    setChanged();
                    notifyObservers(new TilePositionMessage(eventMessage.getNickname(), position));
                }
            }

            case BOARD -> {
                out.printToCLI(game.toCLI(getNickname()) + "\n", ColorCLI.RESET);
                if (!this.nickname.equals(game.getCurrentPlayer().getName())) {
                    out.printToCLI("\nIt's " + eventMessage.getNickname() + "'s turn\n", ColorCLI.WHITE_T);
                }
            }


            case TILE_PACK -> out.printToCLI(game.toCLI(getNickname()) + "\n", ColorCLI.RESET);

            case PICKING_TILES -> {
                if (this.nickname.equals(game.getCurrentPlayer().getName())) {
                    out.printToCLI(game.toCLI(getNickname()) + "\n", ColorCLI.RESET);
                    String answer;
                    do {
                        out.printToCLI("If you wish to stop picking tiles type 'stop', otherwise press ENTER\n", ColorCLI.CYAN_T);
                        out.printToCLI(">>> ", ColorCLI.WHITE_T);
                        answer = in.nextLine();
                        switch (answer) {
                            case "stop" -> {
                                setChanged();
                                notifyObservers(new SwitchPhaseMessage(getNickname(), GamePhase.COLUMN_CHOICE));
                            }
                            case "" -> {
                                out.printToCLI("Please enter a position: \n", ColorCLI.CYAN_T);
                                Position position = askPositionChoice();
                                setChanged();
                                notifyObservers(new TilePositionMessage(eventMessage.getNickname(), position));
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
                    int column = askColumnChoice();
                    setChanged();
                    notifyObservers(new BookshelfColumnMessage(eventMessage.getNickname(), column));
                }
            }

            case BOOKSHELF -> {
                out.printToCLI(game.toCLI(getNickname()) + "\n", ColorCLI.RESET);
                if (!getNickname().equals(game.getCurrentPlayer().getName())) {
                    out.printToCLI(eventMessage.getNickname() + " is inserting tiles in the bookshelf\n", ColorCLI.WHITE_T);
                }
            }

            case PLACING_TILES -> {
                if (getNickname().equals(game.getCurrentPlayer().getName())) {
                    if (game.getTilePack().getTiles().size() > 0) {
                        out.printToCLI("Choose an item tile to insert from the tile pack into the selected column\n", ColorCLI.CYAN_T);

                        boolean validIndex = false;
                        int itemTileIndex = 0;
                        while (!validIndex) {
                            try {
                                out.printToCLI("index: ", ColorCLI.CYAN_T);
                                String input = in.nextLine();
                                itemTileIndex = Integer.parseInt(input);
                                if (itemTileIndex >= 0) validIndex = true;
                            } catch (NumberFormatException e) {
                                out.printToCLI("This is not a valid input, please insert a number\n", ColorCLI.RED_T);
                            }
                        }
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
                    out.printToCLI("\nCongrats, you filled your bookshelf! You have an extra point!\n", ColorCLI.GREEN_T);
                } else {
                    out.printToCLI("\n" + eventMessage.getNickname() + " filled his bookshelf...\n", ColorCLI.WHITE_T);
                }
            }

            case END_GAME -> {
                out.printToCLI("\n", ColorCLI.WHITE_T);
                for (PlayerView playerView : game.getSubscribers()) {
                    out.printToCLI(playerView.getName() + "'s score is " + playerView.getScore() + " points\n", ColorCLI.WHITE_T);
                }
                if (eventMessage.getNickname().equals(getNickname())) {
                    out.printToCLI("\nCongratulations, you won!\n", ColorCLI.GREEN_T);
                }
                out.printToCLI("Game has ended... Hope you had fun!\n", ColorCLI.CYAN_T);
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

    private Position askPositionChoice () {
        boolean validRow = false;
        int r = 0;
        while (!validRow) {
            try {
                out.printToCLI("r: ", ColorCLI.WHITE_T);
                String input = in.nextLine();
                r = Integer.parseInt(input);
                if (r >= 0) validRow = true;
            } catch (NumberFormatException e) {
                out.printToCLI("This is not a valid input, please insert a number\n", ColorCLI.RED_T);
            }
        }
        boolean validColumn = false;
        int c = 0;
        while (!validColumn) {
            try {
                out.printToCLI("c: ", ColorCLI.WHITE_T);
                String input = in.nextLine();
                c = Integer.parseInt(input);
                if (c >= 0) validColumn = true;
            } catch (NumberFormatException e) {
                out.printToCLI("This is not a valid input, please insert a number\n", ColorCLI.RED_T);
            }
        }
        return new Position(r, c);
    }

    private int askColumnChoice () {
        out.printToCLI("In which column you want to insert your item tiles?\n ", ColorCLI.CYAN_T);
        boolean validColumn = false;
        int column = 0;
        while (!validColumn) {
            try {
                out.printToCLI("column: ", ColorCLI.WHITE_T);
                String input = in.nextLine();
                column = Integer.parseInt(input);
                if (column >= 0) validColumn = true;
            } catch (NumberFormatException e) {
                out.printToCLI("This is not a valid input, please insert a number\n", ColorCLI.RED_T);
            }
        }
        return column;
    }
}
