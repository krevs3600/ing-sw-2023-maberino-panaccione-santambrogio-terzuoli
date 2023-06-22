package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.AppServer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.ModelView.PlayerView;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.JoinErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.*;
import it.polimi.ingsw.network.Socket.ServerStub;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.observer_observable.Observable;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <h1>Class GUI</h1>
 * The class GUI extends the Observable abstract class and implements all the methods of the View interface.
 * This class is used to manage the graphical user interface
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/21/2023
 */
public class GUI extends Observable implements View {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private static Stage window;
    private static double width;
    private static double height;
    private NicknameController nicknameController;
    private ServerSettingsController serverSettingsController;
    private CreateorJoinGameController createorJoinGameController;
    private RMIorSocketController rmIorSocketController;
    private LobbyController lobbyController;
    private StartController startController;
    private WinController winController;
    private LivingBoardController livingBoardController;
    private ClientImplementation client;
    private GameNameController gameNameController;
    private NumberOfPlayersController numberOfPlayersController;
    private GameNameListController gameNameListController;
    private String nickname;

    private GameView game; // aggiunto referenza al game


    /**
     * Getter method
     * @return the current stage of the game
     */

    public Stage getStage() {
        return stage;
    }


    /**
     * This method is used to set the {@link GUI client} that decided to start
     * the game via the graphical interface and to which response messages are sent from the server
     * @param client the client instance
     */


    public void setClient(ClientImplementation client) {
        this.client = client;
    }



    /**
     * This method is used to instantiate the first fxml scene : the {@code start_scene} and its controller:
     * {@code StartController}
     * where the player can decide whether to exit the application or start the game
     * @param stage where to set the scene
     */
    public void gameMenuGUI(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("start_scene.fxml"));
        Scene scene = null;
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("myShelfie!");
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
        stage.setHeight(scene.getHeight());
        stage.setWidth(scene.getWidth());
        window = stage;
        stage.setResizable(true);
        width = stage.getWidth();
        height = stage.getHeight();
        startController = fxmlLoader.getController();
        startController.setGui(this);
    }

    /**
     * Getter method
     * @return the instance of the {@link GUI#client}
     */


    public ClientImplementation getClient() {
        return this.client;
    }


    /**
     * This method is used to notify the server
     * about the client's choice to enter a game after pressing the {@link CreateorJoinGameController#joinGame} button
     * in the fxml {@code CreateOrJoinGame_scene}.
     */

    public void joinGame() {
        setChanged();
        notifyObservers(new JoinGameMessage(this.client.getNickname()));
    }



    @Override
    public String askConnectionType() {

        return null;
    }

    /**
     *This method is used to notify the server about the client's choice to
     * start inserting tiles after clicking one of the {@link LivingBoardController ColumnnButtons} in
     * the {@code livingBoard_scene},
     * this method also disables the board of the same client so that he cannot take more tiles.
     *@param column the column number chosen by the client to insert all the tiles it has just taken from the board
     */
    public void stopPickingTiles(int column) {

        livingBoardController.board.setDisable(true);
        setChanged();
        notifyObservers(new BookshelfColumnMessage(this.nickname, column)); // dopo questo entra nel placing tile case
    }

    /**
     *This method is used to set the fxml scene {@code RMIorSocket_scene } after pressing the {@link StartController playBtn} in the
     * {@code start_scene}.
     * The player can now decide whether to connect via RMI or Socket.
     *@param stage where to set the scene
     */

    public void askTypeofConnection(Stage stage) throws IOException {
        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/RMIorSocket_scene.fxml/").toURI().toURL();

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("RMIorSocket_scene.fxml"));

        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        window = stage;
        scene = new Scene(fxmlLoader.load());
        this.stage = stage;
        Platform.runLater(() -> stage.setScene(scene));
        RMIorSocketController rmIorSocketController1 = fxmlLoader.getController();
        rmIorSocketController1.setGui(this);
        this.rmIorSocketController = rmIorSocketController1;
    }

    @Override
    public void createConnection() {
        try {
            URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/AddressIp_scene.fxml/").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            scene = new Scene(fxmlLoader.load());
            window = stage;
            Platform.runLater(() -> stage.setScene(scene));
            serverSettingsController = fxmlLoader.getController();
            serverSettingsController.setGui(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void askNickname() {
        setChanged();
        notifyObservers(new NicknameMessage(this.nicknameController.getNickname()));
    }


    /**
     * This method is used to create a socket or an RMI connection based on the player's choice after clicking either the {@link RMIorSocketController RMIbutton} or
     * the {@link  RMIorSocketController TCPbutton} in the {@code RMIorSocket_scene}.
     * In particular, if the connection is made, a new client is instantiated with the chosen IP address and port.
     *@param address the ip address chosen by the player for the connection, which was already syntactically checked in the {@link ServerSettingsController}
     * @param port the port chosen by the player for the connection, which was already syntactically checked in the {@link ServerSettingsController}
     */

    public void createConnection(String address, int port) throws IOException, NotBoundException {
        if (rmIorSocketController.isRMI()) {
            try {
                Registry registry = LocateRegistry.getRegistry(address, port);
                AppServer server = (AppServer) registry.lookup("MyShelfieServer");
                this.client = new ClientImplementation(this, server.connect());

            } catch (NotBoundException e) {
                System.err.println("not bound exception registry");
            }
        } else if (rmIorSocketController.isSocket()) {
            if (port == 1243) port = 1244;
            ServerStub serverStub = new ServerStub(address, port);
            client = new ClientImplementation(this, serverStub);
            serverStub.register(client);
            new Thread(() -> {
                while (true) {
                    try {
                        serverStub.receive(client);
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from server. Stopping...");

                        try {
                            serverStub.close();
                        } catch (RemoteException ex) {
                            System.err.println("Cannot close connection with server. Halting...");
                            // todo: bisogna far comparire dei pop-up forse oppure chiudere totalmente lo stage
                        }
                        System.exit(1);
                    }
                }
            }).start();
        }

        try {
            URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/login_scene.fxml/").toURI().toURL();
            ;
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            scene = new Scene(fxmlLoader.load());
            Platform.runLater(() -> stage.setScene(scene));
            nicknameController = fxmlLoader.getController();
            nicknameController.setGui(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String askServerAddress() {
        return null;
    }

    @Override
    public int askServerPort() {
        return 0;
    }

    /**
     * This method is used to notify the server of the tile chosen by the player
     * by sending a message with the row and column of the chosen tile after the player has clicked on one tile in the {@code livingBoard_scene.fxml}.
     * In this way, the server can check the validity of the chosen position and
     * in case allow the client to take the tile,
     * or display a warning if the position is not valid.
     *
     *@param r the row of the tile clicked by the player
     * @param c the column of the tile clicked by the player
     */

    public void chosenPosition(int r, int c) {
       // System.out.println(this.client.getNickname() + " ha scelto la tile nella posizione " + r + " " + c);
        setChanged();
        notifyObservers(new TilePositionMessage(this.client.getNickname(), new Position(r, c)));

    }


    /**
     *This method is used to notify the server
     *  of the name chosen by the game creator for the game he has just created.
     * The server will check if there are no other active games with the same name,
     * otherwise a warning message will be displayed.
     *
     */
    //TODO: askgamespecs
    public void askGameName() {
        String gameName = this.gameNameController.getTextField();
        setChanged();
        notifyObservers(new GameNameMessage(this.client.getNickname(), gameName));
    }

    @Override
    public void showGameNamesList(Set<String> availableGameNames) {
        System.out.println(" il gioco da joinare si chiama " + this.gameNameListController.getGameToJoin());
        System.out.println("il client è " + this.client.getNickname());
        setChanged();
        notifyObservers(new GameNameChoiceMessage(this.client.getNickname(), this.gameNameListController.getGameToJoin()));

    }

    //todo secondo me si può fare lo stesso metodo nell'interfaccia view e poi implementarlo


    //todo penso si possa togliere
    public void askGameSpecs() {

    }



    @Override
    public void showMessage(MessageToClient message) {
        switch (message.getType()) {
            case LOGIN_RESPONSE -> {
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                if (loginResponseMessage.isValidNickname()) {

                    try {
                        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/CreateorJoinGame_scene.fxml/").toURI().toURL();
                        FXMLLoader fxmlLoader = new FXMLLoader(url);
                        scene = new Scene(fxmlLoader.load());
                        this.nickname = ((LoginResponseMessage) message).getNickname();
                        CreateorJoinGameController createorJoinGameController = fxmlLoader.getController();
                        createorJoinGameController.setGui(this);
                        this.createorJoinGameController = createorJoinGameController;
                        Platform.runLater(() -> stage.setScene(scene));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // this.nicknameController.InvalidNickname.setVisible(true);
                    // showGameNamesList(loginResponseMessage.getAvailableGames());
                } else {
                    // this.nicknameController.InvalidNickname.setVisible(true);
                    showPopup("Unavailable nickname, please choose another one");
                }
            }
            case GAME_NAME_RESPONSE -> {
                GameNameResponseMessage gameNameResponseMessage = (GameNameResponseMessage) message;
                if (gameNameResponseMessage.isValidGameName()) {
                    try {
                        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/NumberofPlayers_scene.fxml/").toURI().toURL();
                        FXMLLoader fxmlLoader = new FXMLLoader(url);
                        scene = new Scene(fxmlLoader.load());
                        NumberOfPlayersController numberOfPlayersController = fxmlLoader.getController();
                        this.numberOfPlayersController = numberOfPlayersController;
                        numberOfPlayersController.setGui(this);
                        numberOfPlayersController.setGameName(gameNameResponseMessage.getGameName());
                        System.out.println("il nome del gioco è " + numberOfPlayersController.getGameName());
                        Platform.runLater(() -> stage.setScene(scene));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // this.gameNameController.alreadyTakenGameName.setVisible(true);
                    showPopup("Invalid game name, please choose another one");
                }
            }

            case GAME_CREATION -> {
                // nel caso della GUI è sempre valido il gioco creato, perchè il nome l'ho controllato precedentemente e il
                // numero di giocatori viene selezionato attrvaerso i bottoni che indicano gi il range giusto di gioco,
                // anzi in realtà potrebbe premere ok prima di selezionare il numero di giocatori
                GameCreationResponseMessage gameCreationResponseMessage = (GameCreationResponseMessage) message;
                if (gameCreationResponseMessage.isValidGameCreation()) {
                    try {
                        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/lobby_scene.fxml/").toURI().toURL();
                        FXMLLoader fxmlLoader = new FXMLLoader(url);
                        scene = new Scene(fxmlLoader.load());
                        lobbyController = fxmlLoader.getController();
                        lobbyController.setGui(this);
                        Platform.runLater(() -> stage.setScene(scene));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    this.numberOfPlayersController.missingNumberLabel.setVisible(true);
                }
            }

            case JOIN_GAME_RESPONSE -> {
                // questo viene invocato prima di digitare il nome del gioco, perciò l'errore  della lobby si ha nel momento in cui chiedo di joinare ma non ci sono giochi disponibili
                JoinGameResponseMessage joinGameResponseMessage = (JoinGameResponseMessage) message;
                //showGameNamesList(joinGameResponseMessage.getAvailableGamesInLobby());//todo: aggiungere qui lo switch alla scena in cui viene mostrata la lista di giochi
                //else if (createorJoinGameController.getJoinGameb()) {
                if (!((JoinGameResponseMessage) message).getAvailableGamesInLobby().isEmpty()) {
                    try {
                        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/GameNameList_scene.fxml/").toURI().toURL();
                        FXMLLoader fxmlLoader = new FXMLLoader(url);
                        scene = new Scene(fxmlLoader.load());
                        GameNameListController gameNameListController = fxmlLoader.getController();
                        gameNameListController.setGui(this);
                        this.gameNameListController = gameNameListController;
                        this.gameNameListController.setCurrentLobbyGameNames(((JoinGameResponseMessage) message).getAvailableGamesInLobby());
                        gameNameListController.initialize();
                        Platform.runLater(() -> stage.setScene(scene));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            case JOIN_GAME_ERROR -> {
                JoinErrorMessage joinErrorMessage = (JoinErrorMessage) message;
                // pop up not available games in the lobby
                showPopup("No available games in the lobby");
            }


            case WAIT_PLAYERS -> {
                WaitingResponseMessage waitingResponseMessage = (WaitingResponseMessage) message;
                if (this.lobbyController == null) {
                    try {
                        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/lobby_scene.fxml/").toURI().toURL();
                        FXMLLoader fxmlLoader = new FXMLLoader(url);
                        scene = new Scene(fxmlLoader.load());
                        lobbyController = fxmlLoader.getController();
                        lobbyController.setGui(this);
                        Platform.runLater(() -> stage.setScene(scene));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                Platform.runLater(() -> lobbyController.NumberOfMissingPlayers.setText("Waiting for" + waitingResponseMessage.getMissingPlayers() + " players....."));
                lobbyController.NumberOfMissingPlayers.setVisible(true);
            }

            case PLAYER_JOINED_LOBBY_RESPONSE -> {
                PlayerJoinedLobbyMessage player = (PlayerJoinedLobbyMessage) message;
                //out.println("\n" + player.getNickname() + " joined lobby") TODO: settare il text della lobby ;
                Platform.runLater(() -> lobbyController.PlayerJoinedGame.setText(player.getNickname() + " joined lobby"));
                this.lobbyController.PlayerJoinedGame.setVisible(true);
                // ok forse il comando sotto non ha senso perche sono nella GUI di un client che quindi vede il suo lobbyController
                //  this.lobbyController.NumberOfMissingPlayers.setText("Waiting for"+ this.lobbyController.getPinLobby());
                //  this.lobbyController.NumberOfMissingPlayers.setVisible(true);
            }

            case ILLEGAL_POSITION, UPPER_BOUND_TILEPACK, NOT_ENOUGH_INSERTABLE_TILES -> {
                if (this.client.getNickname().equals(message.getNickname())) {
                    ErrorMessage errorMessage = (ErrorMessage) message;
                    this.showPopup(errorMessage.getErrorMessage());
                    String answer = "";

                }
            }

            case NOT_ENOUGH_INSERTABLE_TILES_IN_COLUMN -> {
                ErrorMessage errorMessage = (ErrorMessage) message;
                showPopup(errorMessage.getErrorMessage());
                // quindi poi automaticamente risceglie la colonna
                // ma le avevo disabilitate...
                livingBoardController.resetColumnChoice();
            }
            case PLAYER_OFFLINE, KILL_GAME, DISCONNECTION_RESPONSE -> {
            }
        }

    }

    /**
     *This method is used to notify the server with the number of players chosen by the player for the newly created game.
     * At this point, the game is created and the creator waits for the other participants to connect to start playing.
     * @param gameName the name chosen for the game by the creator.
     */

    public void askNumberOfPlayers(String gameName) {
        setChanged();
        notifyObservers(new GameCreationMessage(this.client.getNickname(), this.numberOfPlayersController.getNumberOfPlayersChosen(), gameName));
    }
// todo : questi due metodi ora possono essere tolti perche il check lo facciamo direttamente nel controller della scena, però da errore perche altrimenti non implementiamo tutti i metodi dell'interfaccia.. è un problema?

    @Override
    public boolean isValidPort(String serverPort) {
        return false;
    }

    @Override
    public boolean isValidIPAddress(String address) {
        return false;
    }

    // TODO: mettere tutto in un metodo che cambia il root Pane in base al path che do in input
    @Override
    public void gameMenu() {
        if (createorJoinGameController.getCreateGame()) {
            try {
                URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/GameName_scene.fxml/").toURI().toURL();
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                scene = new Scene(fxmlLoader.load());
                gameNameController = fxmlLoader.getController();
                gameNameController.setGui(this);
                Platform.runLater(() -> stage.setScene(scene));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // booleano per distinguere la prima volta del gioco e le altre
    private boolean isFirstTime = true;

    @Override
    public void update(GameView game, EventMessage eventMessage) {
        this.game = game;
        switch (eventMessage.getType()) {
            case BOARD -> {
                // todo: in questo caso deve comparire il pop up con i personal goal card e i common goal cards e si inizializza la scena
                if (isFirstTime) {
                    try {
                        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/livingBoard_scene.fxml/").toURI().toURL();
                        FXMLLoader fxmlLoader = new FXMLLoader(url);
                        Scene scene = new Scene(fxmlLoader.load());
                        livingBoardController = fxmlLoader.getController();
                        livingBoardController.setGui(this);

                        Platform.runLater(() -> {

                            stage.setScene(scene);
                            // if (isFirstTime) {
                            //     int displayTimeMillis = 9000;
                            //     livingBoardController.AnchorPaneforTheCandPgoalcards.setVisible(true);
                            //     FadeTransition fadeTransition = new FadeTransition(Duration.millis(displayTimeMillis), livingBoardController.AnchorPaneforTheCandPgoalcards);
                            //     fadeTransition.setOnFinished(event -> {
                            //         livingBoardController.AnchorPaneforTheCandPgoalcards.setVisible(false);
                            //     });
                            //     fadeTransition.play();
                            //     isFirstTime = false;
                            //
                            // }

                            livingBoardController.initialize(game, nickname);
                            livingBoardController.updateLivingRoomBoard(game.getLivingRoomBoard());
                            livingBoardController.tilePack.setDisable(true);
                        });
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    isFirstTime = false;
                } else {
                    Platform.runLater(() -> {
                        livingBoardController.updateLivingRoomBoard(game.getLivingRoomBoard());
                        livingBoardController.tilePack.setDisable(true);
                        livingBoardController.resetColumnChoice();
                        showPopup("NEW TURN HAS JUST STARTED");

                    });
                }
            }

            case PLAYER_TURN -> {
                System.out.println(client.getNickname() + " " + eventMessage.getNickname() + " " + game.getCurrentPlayer().getName());
                System.out.println("it's " + game.getCurrentPlayer().getName() + " turn");
                if (this.client.getNickname().equals(game.getCurrentPlayer().getName())) {
                    Platform.runLater(() -> {
                        livingBoardController.board.setDisable(false);
                        showPopup("it's your turn"); // todo ricontrollare questo pop up non mi sembra che compaia
                        livingBoardController.OtherPlayerTurnLabel.setVisible(false);
                        livingBoardController.board.setDisable(false);
                        livingBoardController.disableColumnChoice();
                    });
                    //salva per vedere se sono gli stessi

                    // todo ricontrollare questo pop up non mi sembra che compaia

                    // todo : farei un do-while fin quando la carta non è cliccata mettere un
                    //  timer che fa scadere il turno se non è cliccata entro 3 minuti tipo

                } else {
                    Platform.runLater(() -> {
                        livingBoardController.board.setDisable(true);
                        livingBoardController.disableColumnChoice();
                        livingBoardController.OtherPlayerTurnLabel.setText("Wait... " + eventMessage.getNickname() + " is picking tiles");
                        livingBoardController.OtherPlayerTurnLabel.setVisible(true);
                    });
                }
            }

            case PLACING_TILES -> {
                livingBoardController.tilePack.setDisable(false);
                if (this.client.getNickname().equals(game.getCurrentPlayer().getName())) {
                    Platform.runLater(() -> {
                        livingBoardController.updateTilePack(game.getTilePack());
                        LivingBoardController.updateBookshelf(game.getCurrentPlayer().getBookshelf(), livingBoardController.bookshelf);
                        LivingBoardController.updateBookshelf(game.getPlayer(livingBoardController.getPlayers().get(livingBoardController.getWatchedPlayer())).getBookshelf(), livingBoardController.otherBookshelf);
                        livingBoardController.setGameView(game);
                    });
                    if (game.getTilePack().getTiles().size() > 0) {
                        Platform.runLater(() -> showPopup("Choose an item tile to insert from the tilepack \ninto the selected column"));
                    } else if (game.getTurnPhase().equals(GamePhase.PLACING_TILES)) {
                        setChanged();
                        notifyObservers(new EndTurnMessage(eventMessage.getNickname()));
                    }
                } else {
                    Platform.runLater(() -> {
                        LivingBoardController.updateBookshelf(game.getPlayer(livingBoardController.getPlayers().get(livingBoardController.getWatchedPlayer())).getBookshelf(), livingBoardController.otherBookshelf);
                    });
                }
            }

            case PICKING_TILES -> {
                if (this.client.getNickname().equals(game.getCurrentPlayer().getName())) {
                    System.out.println("arrivato a " + this.client.getNickname());
                    Platform.runLater(() -> {
                        livingBoardController.updateLivingRoomBoard(game.getLivingRoomBoard());
                        livingBoardController.updateTilePack(game.getTilePack());
                        livingBoardController.resetColumnChoice();
                    });
                } else {
                    Platform.runLater(() ->
                            livingBoardController.updateLivingRoomBoard(game.getLivingRoomBoard()));
                    showPopup(game.getCurrentPlayer().getName() + "is picking Tiles");
                }
            }
            case TILE_PACK, COLUMN_CHOICE, BOOKSHELF -> {
                break;
            }
            case COMMON_GOAL_CARD -> {
                CommonGoalCardMessage message = (CommonGoalCardMessage) eventMessage;
                System.out.println("Received message goalcard message");
                Platform.runLater(()->{
                    livingBoardController.updateTokens(game, message.getCommonGoalCardIndex());
                    String number = message.getCommonGoalCardIndex() == 0 ? "first" : "second";
                    if (eventMessage.getNickname().equals(nickname)){
                        showPopup("You completed the " + number + " common goal");
                    } else {
                        showPopup(eventMessage.getNickname() + " completed the " + number + " common goal");
                    }
                });
            }
            case LAST_TURN -> {
                if (this.client.getNickname().equals(game.getCurrentPlayer().getName())) {
                    showPopup("\nCongrats, you filled your bookshelf! You have an extra point!");
                } else {
                    showPopup("\n" + eventMessage.getNickname() + " filled his bookshelf...");
                }
            }
            case END_GAME -> {
                try {
                    URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/win_scene.fxml/").toURI().toURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    scene = new Scene(fxmlLoader.load());
                    WinController winController = fxmlLoader.getController();
                    winController.setGui(this);
                    this.winController = winController;
                    Platform.runLater(() -> stage.setScene(scene));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int scoreOfThisClient=0;
                for(PlayerView player:game.getSubscribers()){
                    if(player.getName().equals(this.client.getNickname())){
                        scoreOfThisClient= player.getScore();
                        break;
                    }
                }

                winController.myscoretext.setText("YOUR  FINAL SCORE IS           " + scoreOfThisClient);

                if (eventMessage.getNickname().equals(this.client.getNickname())) {
                    winController.fail_1.setVisible(false);
                    winController.fail_2.setVisible(false);
                    winController.fail_3.setVisible(false);
                    winController.fail_4.setVisible(false);
                    winController.congratulations.setText("Congratulations "+ eventMessage.getNickname() +"!");



                    System.out.println(eventMessage.getNickname() + "win the game");
                } else {
                    winController.bestplayer.setVisible(false);
                    winController.congratulations.setVisible(false);
                    winController.YouWonTheGame.setVisible(false);
                    winController.lost.setVisible(true);
                    winController.lost.setText("Sorry " +client.getNickname()+"...... you lost");
                    winController.DontGiveUp.setVisible(true);
                    winController.willBeBetter.setVisible(true);
                    Platform.runLater(() -> showPopup(eventMessage.getNickname() + " won"));
                }

                if (game.getNumberOfPlayers().equals(NumberOfPlayers.THREE_PLAYERS) || game.getNumberOfPlayers().equals(NumberOfPlayers.FOUR_PLAYERS)) {
                    winController.lineone.setVisible(true);
                    winController.thirdPlayerscoretxt.setVisible(true);
                    winController.viewD_3player.setVisible(true);
                    if (game.getNumberOfPlayers().equals(NumberOfPlayers.FOUR_PLAYERS)) {
                        winController.linetwo.setVisible(true);
                        winController.FourthPlayerScoretxt.setVisible(true);
                        winController.viewD_4player.setVisible(true);
                    }
                }

                PlayerView playerreal=null;



                for (PlayerView player : game.getSubscribers()) {
                    if (this.client.getNickname().equals(player.getName())) {
                        playerreal=player;
                        break; // quindi metodo che mi ritorna il giocatore associato a questo client e poi costruisco una lista senza quel giocatore.

                    }
                }


                    System.out.println(" il giocatore a cui mi sono bloccata è "+playerreal.getName());
                    for (int i = 0; i < game.otherPlayersList(playerreal).size(); i++) {
                        if (i == 0) { // 2 giocatori1
                            winController.secondPlayeRScoretxt.setText(game.otherPlayersList(playerreal).get(0).getName() + "'s score is:  " + game.otherPlayersList(playerreal).get(0).getScore());
                        } else if (i == 1) { // 3 giocatori
                            winController.thirdPlayerscoretxt.setText(game.otherPlayersList(playerreal).get(1).getName() + "'s score is:  " + game.otherPlayersList(playerreal).get(1).getScore());
                        } else if (i == 2) {
                            winController.FourthPlayerScoretxt.setText(game.otherPlayersList(playerreal).get(2).getName() + "'s score is:  " + game.otherPlayersList(playerreal).get(2).getScore());
                        }
                    }

                }


            }
        }





    /**
     *This method is used to display a generic pop-up warning in all game scenes
     * @param text the message that appears in the popup
     */



    public void showPopup(String text){
        Popup popup = new Popup();
        Label noSelection = new Label(text);
        noSelection.setStyle("-fx-background-color: #FFCCCC; -fx-text-fill: #FF0000;-fx-font-size: 30; -fx-padding: 30px;");
        popup.getContent().add(noSelection);
        popup.setAutoHide(true);
        if (getStage()!=null){
            Platform.runLater(()-> popup.show(this.getStage()));
        }
    }


    /**
     *This method is used to notify the server of the specific tile to be placed in the bookshelf after the player
     * has selected it from the tilepack by clicking an image in the {@link LivingBoardController tilePack} present in the
     * {@code livingBoard_scene}
     * @param itemTileIndex the index of the tilepack that contains the chosen tile to be placed in the column.Since there are at most 3 tiles in the tilepack,
     * this index will have a value between 0 and 2.
     */


    public void insertTileInColumn(int itemTileIndex){
        setChanged();
        notifyObservers(new ItemTileIndexMessage(nickname, itemTileIndex));
    }

   /* public static Stage getWindow(){
        return window;
    }
    public static Scene getActiveScene() {return activeScene;}
    public static double getWidth() {return width;}
    public static double getHeight() {return height;}


    */




    /**
     *This method is used to set the {@code computeScore_scene.fxml } and instantiate his scene controller in the GUI.
     * In that scene the player can see the details of his score.
     * The switch to this scene comes after the player has clicked the {@link WinController MyScoreDetail} button
     * in the scene {@code win_scene}.
     */

    public void showThisClientScores() {
        try {
            URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/computeScore_scene.fxml/").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            scene = new Scene(fxmlLoader.load());
            WinController winController = fxmlLoader.getController();
            winController.setGui(this);
            this.winController = winController;
            Platform.runLater(() -> stage.setScene(scene));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}