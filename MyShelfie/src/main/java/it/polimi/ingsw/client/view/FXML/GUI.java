package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.AppServer;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.JoinErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.*;
import it.polimi.ingsw.network.Socket.ServerStub;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.observer_observable.Observable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Set;


public class GUI extends Observable implements View{

    private boolean activeTurn = false;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static Stage window;
    private static Scene activeScene;
    private static double width;
    private static double height;
    private NicknameController nicknameController;
    private ServerSettingsController serverSettingsController;
    private CreateorJoinGameController createorJoinGameController;
    private RMIorSocketController rmIorSocketController;
    private LobbyController lobbyController;
    private StartController startController;
    private LivingBoardController livingBoardController;
    private ClientImplementation client;
    private GameNameController gameNameController;
    private NumberOfPlayersController numberOfPlayersController;
    private GameNameListController gameNameListController;
    private String nickname;

    public Stage getStage() {
        return stage;
    }


    public void setClient(ClientImplementation client){
        this.client = client;
    }

    public void gameMenuGUI(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("start_scene.fxml"));
        Scene scene = null;
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("myShelfie!");
        stage.setScene(scene);
        this.stage=stage;
        stage.show();
        stage.setHeight(scene.getHeight());
        stage.setWidth(scene.getWidth());
        window=stage;
        stage.setResizable(true);
        width = stage.getWidth();
        height = stage.getHeight();
        startController = fxmlLoader.getController();
        startController.setGui(this);
    }
    public ClientImplementation getClient(){
        return this.client;
    }

    public void joinGame(){
        setChanged();
        notifyObservers(new JoinGameMessage(this.client.getNickname()));
    }


    @Override
    public String askConnectionType() {

        return null;
    }

    public void StopPickingTiles(int column){
        // Non devo fare il controllo che il turno sia attivo perche altrmenti i pulsanti sono disabilitati
        // quindi anche nel caso in cui sbaglia se si toglie il pop up non c'è problema, l'unica cosa da estire è
        // il caso in cui sceglie 3 tiles
        setChanged();
        notifyObservers(new BookshelfColumnMessage(this.nickname, column));
        System.out.println("qui ok?");

    }

    public void askTypeofConnection(Stage stage) throws IOException {
        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/RMIorSocket_scene.fxml/").toURI().toURL();

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("RMIorSocket_scene.fxml"));

        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        window=stage; // gor
        scene = new Scene(fxmlLoader.load());
        this.stage=stage;
        Platform.runLater(()->stage.setScene(scene));
        RMIorSocketController rmIorSocketController1=fxmlLoader.getController();
        rmIorSocketController1.setGui(this);
        this.rmIorSocketController=rmIorSocketController1;
    }
    @Override
    public void createConnection()  {
        try {
            URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/AddressIp_scene.fxml/").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            scene = new Scene(fxmlLoader.load());
            window=stage;
            Platform.runLater(()->stage.setScene(scene));
            //stage.show();

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


    public void createConnection(String address, int port) throws IOException, NotBoundException {
        if(rmIorSocketController.isRMI()) {
            try {
                Registry registry = LocateRegistry.getRegistry(address, port);
                AppServer server = (AppServer) registry.lookup("MyShelfieServer");
                this.client = new ClientImplementation(this, server.connect());

            } catch (NotBoundException e) {
                System.err.println("not bound exception registry");
            }
        } else if (rmIorSocketController.isSocket()) {
            if(port == 1243) port = 1244;
            ServerStub serverStub = new ServerStub(address, port);
            client = new ClientImplementation(this, serverStub);
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
                            // todo: bisogna far comparire dei pop-up forse oppure chiudere totalmente lo stage
                        }
                        System.exit(1);
                    }
                }
            }).start();
        }

        URL url = null;
        try {
            url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/login_scene.fxml/").toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("login_scene.fxml"));
        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(()->stage.setScene(scene));
        //Platform.runLater(()->stage.show());

        NicknameController nicknameController=fxmlLoader.getController();
        nicknameController.setGui(this);
        this.nicknameController=nicknameController;
        // askNickname();
    }

    @Override
    public String askServerAddress() {
        return null;
    }

    @Override
    public int askServerPort() {
        return 0;
    }

    public void chosenPosition(int r, int c){
        setChanged();
        notifyObservers(new TilePositionMessage(this.client.getNickname(), new Position(r,c)));
    }



    //TODO: askgamespecs
    public void askGameName() {
        String gameName=this.gameNameController.getTextField();
        setChanged();
        notifyObservers(new GameNameMessage(this.client.getNickname(), gameName));
    }

    @Override
    public void showGameNamesList(Set<String> availableGameNames) {
        setChanged();
        notifyObservers(new GameNameChoiceMessage(this.client.getNickname(),this.gameNameListController.getGameToJoin()));

    }

    //todo secondo me si può fare lo stesso metodo nell'interfaccia view e poi implementarlo
    //public void chosenGame(String gameChoice) {
    //
    //}
    public void askGameName(String GameName) {

    }

    public void askGameSpecs(){

    }

    // @Override
    // public void showGameNamesList(Set<String> availableGameNames) {
    //     setChanged();
    //     notifyObservers(new GameNameChoiceMessage(this.client.getNickname(),gameNameListController.);
    // }

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

                //magari serve settare il numero di giocatori selezionato da far vedere
                // if (waitingResponseMessage.getMissingPlayers() == 1) {
                //
                //     // out.println("\nWaiting for 1 player... "); TODO: settare il text della lobby
                // } else {
                //     //TODO: settare il text della lobby
                //     //  out.println("\nWaiting for " + waitingResponseMessage.getMissingPlayers() + " players... ");
                // }
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
                if (activeTurn) {
                    ErrorMessage errorMessage = (ErrorMessage) message;
                    this.showPopup(errorMessage.getErrorMessage());
                    String answer = "";
                    livingBoardController.ContinuepickingT.setVisible(true);
                    livingBoardController.stopPickingT.setVisible(true);

                    while(!livingBoardController.WantStopPickingTiles() && !livingBoardController.WantPickAnotherTile()) {
                        if (livingBoardController.WantStopPickingTiles()) {
                            setChanged();
                            notifyObservers(new SwitchPhaseMessage(client.getNickname(), GamePhase.PLACING_TILES));
                        }
                    }
                        //if(livingBoardController.WantPickAnotherTile()){
                        //    case "" -> {
                        //        out.print("r: ");
                        //        int r = in.nextInt();
                        //        in.nextLine();
                        //        System.out.print("c: ");
                        //        int c = in.nextInt();
                        //        in.nextLine();
                        //        setChanged();
                        //        notifyObservers(new TilePositionMessage(((ErrorMessage) message).getNickName(), new Position(r, c)));
                        //    }

                    livingBoardController.stopPickingT.setVisible(false);
                    livingBoardController.ContinuepickingT.setVisible(false);
                    livingBoardController.resetAnotherTile();
                    livingBoardController.resetStopPickingTiles();
                }
            }

            case NOT_ENOUGH_INSERTABLE_TILES_IN_COLUMN -> {
                ErrorMessage errorMessage = (ErrorMessage) message;
                showPopup(errorMessage.getErrorMessage());
                // quindi poi automaticamente risceglie la colonna
            }






            }
        }



    public void askNumberOfPlayers(String gameName) {
        setChanged();
        notifyObservers(new GameCreationMessage(this.client.getNickname(), this.numberOfPlayersController.getNumberOfPlayersChosen(), gameName));
    }

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

    @Override
    public void update(GameView game, EventMessage eventMessage) {
        switch (eventMessage.getType()) {
            case BOARD -> {
                // todo: in questo caso deve comparire il pop up con i personal goal card e i common goal cards e si inizializza la scena
                try {
                    URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/livingBoard_scene.fxml/").toURI().toURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    Scene scene = new Scene(fxmlLoader.load());
                    livingBoardController = fxmlLoader.getController();
                    livingBoardController.setGui(this);
                    Platform.runLater(() -> stage.setScene(scene));
                    Platform.runLater(() -> {
                        try {
                            livingBoardController.initialize(game, nickname);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            case PLAYER_TURN -> {
                // livingBoardController.board.setDisable(true);
                livingBoardController.bookshelf.setDisable(true);
                livingBoardController.Column1.setDisable(true);
                livingBoardController.Column2.setDisable(true);
                livingBoardController.Column3.setDisable(true);
                livingBoardController.Column4.setDisable(true);
                livingBoardController.Column5.setDisable(true);
                if (this.client.getNickname().equals(eventMessage.getNickname())) {
                    activeTurn = true;

                    showPopup("it's your turn, remember you can select a maximum of 3 tiles\n" +
                            " adjacent to each other that must form a line and they must\n" +
                            " all have a free side at the beginning! Good luck and always keep in mind the achievement " +
                            "\nof personal and common goal cards ! \uD83D\uDCAA\uD83C\uDFFB\uD83D\uDCAA\uD83C\uDFFE");

                    // todo : farei un do-while fin quando la carta non è cliccata mettere un
                    //  timer che fa scadere il turno se non è cliccata entro 3 minuti tipo

                } else {
                    activeTurn = false;
                    showPopup("It's " + eventMessage.getNickname() + "'s turn\n");
                    livingBoardController.OtherPlayerTurnLabel.setText("Wait... " + this.client.getNickname() + "is picking tiles");
                    livingBoardController.OtherPlayerTurnLabel.setVisible(true);

                }
            }

            //in questo caso appena preme il bottone della colonna passa all'inserimento nella Bookshelf
            case TILE_PACK -> {


                TilePackMessage tilePackMessage = (TilePackMessage) eventMessage;
                // for (PlayerView playerView : game.getSubscribers()) {
                //    out.println("\n-----------------------------------------------------------------------\n" + playerView.getName() + "'s BOOKSHELF:");
                //out.println(playerView.getBookshelf().toString());
                //    out.println("\n" + playerView.getName() + "'s score: " + playerView.getScore());
                livingBoardController.setGameView(game);

                Platform.runLater(() -> {
                    try {
                        livingBoardController.updateLivingRoomBoard(game.getLivingRoomBoard());
                        if (activeTurn) {
                            livingBoardController.updateTilePack(game.getTilePack());
                            livingBoardController.Column1.setDisable(false);
                            livingBoardController.Column2.setDisable(false);
                            livingBoardController.Column3.setDisable(false);
                            livingBoardController.Column4.setDisable(false);
                            livingBoardController.Column5.setDisable(false);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                /*

                // out.println("\n-----------------------------------------------------------------------\n LIVING ROOM BOARD:");
                //  out.println(game.getLivingRoomBoard().toString());

                    // out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
                    // out.println(game.getTilePack().toString());
                    // livingBoardController.ContinuepickingT.setVisible(true);
                    // livingBoardController.stopPickingT.setVisible(true);
                    /*
                    while(!livingBoardController.WantStopPickingTiles() && !livingBoardController.WantPickAnotherTile()) {
                        if (livingBoardController.WantStopPickingTiles()) {
                            setChanged();
                            notifyObservers(new SwitchPhaseMessage(client.getNickname(), GamePhase.PLACING_TILES));
                        }

                        // case "easteregg" -> {
                        //    setChanged();
                        //   notifyObservers(new FillBookshelfMessage(eventMessage.getNickname()));
                         //   }
                    }*/

            }

            // va in questo case se nella colonna inserita c'è abbastanza spazio per inserire le tiles.

            case INSERTION_REQUEST -> {
                if (activeTurn) {
                    System.out.println("fin qui ci sono ");
                    Platform.runLater(()->showPopup("F"));
                    //  out.println("\n-----------------------------------------------------------------------\n" + game.getSubscribers().get(0).getName() + "'s BOOKSHELF:");
                    //  out.println(game.getCurrentPlayer().getBookshelf().toString());
                    //  out.println("\n-----------------------------------------------------------------------\n TILE PACK:");
                    // out.println(game.getTilePack().toString());
                   // if (game.getTilePack().getTiles().size() > 0) {
                        //    out.print("Choose an item tile to insert from the tilepack into the selected column\n");
                        //    int itemTileIndex = in.nextInt();
                        //    setChanged();
                        //    notifyObservers(new ItemTileIndexMessage(eventMessage.getNickname(), itemTileIndex));
                        //} else {
                        //    //activeTurn = false;
                        //    setChanged();
                        //    notifyObservers(new EndTurnMessage(eventMessage.getNickname()));
                        //}
                    }   //

                }


            }
        }



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

    public static Stage getWindow(){
        return window;
    }
    public static Scene getActiveScene() {return activeScene;}
    public static double getWidth() {return width;}
    public static double getHeight() {return height;}

}