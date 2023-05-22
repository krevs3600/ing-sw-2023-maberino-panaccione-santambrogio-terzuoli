package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.AppServer;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.observer_observable.Observable;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
    private RMIorSocketController rmIorSocketController;

    private StartController startController;

    private ClientImplementation client;

    public Stage getStage() {
        return stage;
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
        stage.setScene(scene);
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
        stage.setScene(scene);
        stage.show();

        ServerSettingsController serverSettingsController=fxmlLoader.getController();
        serverSettingsController.setGui(this);


    }


    public void createConnection(String address,int port) throws IOException, NotBoundException {
        try {
         Registry registry = LocateRegistry.getRegistry(address, port);
         AppServer server = (AppServer) registry.lookup("MyShelfieServer");
            this.client = new ClientImplementation(this, server.connect());
            URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/login_scene.fxml/").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            NicknameController nicknameController=fxmlLoader.getController();
            nicknameController.setGui(this);
            // askNickname();
        } catch (NotBoundException e) {
            System.err.println("not bound exception registry");
        }



    }

    @Override
    public void askNickname() {

    }

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

    }

    @Override
    public void showGameNamesList(Set<String> availableGameNames) {

    }

    @Override
    public void showMessage(MessageToClient message) {

    }

    @Override
    public void askNumberOfPlayers(String gameName) {

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
