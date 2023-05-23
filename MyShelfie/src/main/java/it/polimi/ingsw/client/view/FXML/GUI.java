package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.AppServer;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.GameCreationResponseMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.GameNameResponseMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.LoginResponseMessage;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.eventMessages.GameCreationMessage;
import it.polimi.ingsw.network.eventMessages.GameNameMessage;
import it.polimi.ingsw.network.eventMessages.NicknameMessage;
import it.polimi.ingsw.observer_observable.Observable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Set;

public class GUI extends Observable implements View{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static Stage window;
    private static Scene activeScene;
    private static double width;
    private static double height;
    private NicknameController nicknameController;

    private CreateorJoinGameController createorJoinGameController;
    private RMIorSocketController rmIorSocketController;

    private LobbyController lobbyController;
    private StartController startController;



    private ClientImplementation client;

    private GameNameController gameNameController;
    private NumberOfPlayersController numberOfPlayersController;

    public Stage getStage() {
        return stage;
    }


    public void setClient(ClientImplementation client){
        this.client=client;

    }

    public void gameMenuGUI(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("start_scene.fxml"));
        Scene scene = null;

        scene = new Scene(fxmlLoader.load());

      //  stage.setTitle("myShelfie!");

        stage.setScene(scene);
        this.stage=stage;
        stage.show();
        stage.setHeight(scene.getHeight());
        stage.setWidth(scene.getWidth());
        window=stage;
        stage.setResizable(true);
        width = stage.getWidth();
        height = stage.getHeight();
        StartController startController=fxmlLoader.getController();
        startController.setGui(this);
    }
    public ClientImplementation getClient(){
        return this.client;
    }




    @Override
    public String askConnectionType() {

        return null;
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
        stage.show();
        RMIorSocketController rmIorSocketController1=fxmlLoader.getController();
        rmIorSocketController1.setGui(this);
    }
    @Override
    public void createConnection()  {
        URL url = null;
        try {
            url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/AddressIp_scene.fxml/").toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("AddressIp_scene.fxml"));
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
        window=stage; // gor
        Platform.runLater(()->stage.setScene(scene));
        stage.show();

        ServerSettingsController serverSettingsController=fxmlLoader.getController();
        serverSettingsController.setGui(this);


    }

    @Override
    public void askNickname() {
        setChanged();
        notifyObservers(new NicknameMessage(this.nicknameController.getNickname()));

    }


    public void createConnection(String address,int port) throws IOException, NotBoundException {
        try {
         Registry registry = LocateRegistry.getRegistry(address, port);
         AppServer server = (AppServer) registry.lookup("MyShelfieServer");
            this.client = new ClientImplementation(this, server.connect());

        } catch (NotBoundException e) {
            System.err.println("not bound exception registry");
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
            stage.show();

            NicknameController nicknameController=fxmlLoader.getController();
            nicknameController.setGui(this);
            this.nicknameController=nicknameController;
            // askNickname();




    }

    //@Override
    //public void askNickname() {
    //
    //
    //
    //    Stage stage = GuiApp.getWindow();
    //    URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/livingBoard_scene.fxml").toURI().toURL();
    //    FXMLLoader fxmlLoader = new FXMLLoader(url);
    //    Scene scene = new Scene(fxmlLoader.load());
    //    stage.setScene(scene);
    //
    //}


    //public void askNickname(String nickname) {
    //
    //    setChanged();
    //    notifyObservers(new NicknameMessage(nickname));
    //
    //
    //}
    @Override
    public String askServerAddress() {
        return null;
    }

    @Override
    public int askServerPort() {
        return 0;
    }

    @Override
    public void askGameName() {
      String gameName=this.gameNameController.getGameName();
      setChanged();
      notifyObservers(new GameNameMessage(this.client.getNickname(),gameName));



    }


    public void askGameName(String GameName) {

    }

    @Override
    public void showGameNamesList(Set<String> availableGameNames) {

    }

    @Override
    public void showMessage(MessageToClient message) {
        switch (message.getType()) {
            case LOGIN_RESPONSE -> {
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                if (loginResponseMessage.isValidNickname()) {
                    URL url = null;
                    try {
                        url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/CreateorJoinGame_scene.fxml/").toURI().toURL();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreateorJoinGame_scene.fxml"));
                    try {
                        root = FXMLLoader.load(url);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ;
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                  CreateorJoinGameController createorJoinGameController= fxmlLoader.getController();
                  createorJoinGameController.setGui(this);
                    this.createorJoinGameController=createorJoinGameController;


                   // GameNameController GameNameController = fxmlLoader.getController();
                   // GameNameController.setGui(this);
                   // this.gameNameController = GameNameController;

                    Platform.runLater(() -> stage.setScene(scene));
                    stage.show();
                    // this.nicknameController.InvalidNickname.setVisible(true);





                    //showGameNamesList(loginResponseMessage.getAvailableGames());

                } else {

                    this.nicknameController.InvalidNickname.setVisible(true);
                }
            }
                case GAME_NAME_RESPONSE -> {
                    GameNameResponseMessage gameNameResponseMessage = (GameNameResponseMessage) message;
                    if (gameNameResponseMessage.isValidGameName()) {
                        URL url = null;
                        try {
                            url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/NumberofPlayers_scene.fxml/").toURI().toURL();
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("NumberofPlayers_scene.fxml"));
                        try {
                            root = FXMLLoader.load(url);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
//TODO capire la concorrenza tra queste due scene
                        NumberOfPlayersController numberOfPlayersController = fxmlLoader.getController();
                        this.numberOfPlayersController = numberOfPlayersController;
                        numberOfPlayersController.setGui(this);
                        numberOfPlayersController.setGameName(gameNameResponseMessage.getGameName());


                        Platform.runLater(() -> stage.setScene(scene));
                        stage.show();
                        // this.nicknameController.InvalidNickname.setVisible(true);



                       ;
                    } else {

                        this.gameNameController.AlredytTakenGameName.setVisible(true);

                    }
                }

            case GAME_CREATION -> {
                // nel caso della GUI è sempre valido il gioco creato, perchè il nome l'ho controllato precedentemente e il
                // numero di giocatori viene selezionato attrvaerso i bottoni che indicano gi il range giusto di gioco,
                // anzi in realtà potrebbe premere ok prima di selezionare il numero di giocatori
                GameCreationResponseMessage gameCreationResponseMessage = (GameCreationResponseMessage) message;
                if (gameCreationResponseMessage.isValidGameCreation()) {
                    URL url = null;
                    try {
                        url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/lobby_scene.fxml/").toURI().toURL();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("lobby_scene.fxml"));
                    try {
                        root = FXMLLoader.load(url);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ;
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Platform.runLater(() -> stage.setScene(scene));
                    stage.show();

//TODO capire la concorrenza tra queste due scene
                    LobbyController lobbyController=fxmlLoader.getController();
                   this.lobbyController=lobbyController;
                    lobbyController.setGui(this);
                    //magari serve settare il numero di giocatori selezionato da far vedere





                } else {
                    this.numberOfPlayersController.MissingNumberLabel.setVisible(true);
                }
            }

            }


        }


    @Override
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

    @Override
    public void gameMenu() {
        if (createorJoinGameController.getCreateGame()) {
            URL url = null;
            try {
                url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/GameName_scene.fxml/").toURI().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("GameName_scene.fxml"));
            try {
                root = FXMLLoader.load(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }




            GameNameController GameNameController = fxmlLoader.getController();
            GameNameController.setGui(this);
            this.gameNameController = GameNameController;

            Platform.runLater(() -> stage.setScene(scene));
            stage.show();
        }

    }

    @Override
    public void update(GameView gameView, EventMessage eventMessage) {

    }



    public static Stage getWindow(){
        return window;
    }
    public static Scene getActiveScene() {return activeScene;}
    public static double getWidth() {return width;}
    public static double getHeight() {return height;}




}
