package it.polimi.ingsw.client.view.FXML;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class GuiApp extends Application {
    private static Stage window;
    private static Scene activeScene;
    private static double width;
    private static double height;

    private GUI gui;


    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button playBtn;

    @FXML
    public Button quitBtn;
    @FXML
    public Button quitBtn1;

    private Stage stage;
    private Scene scene;
    private Parent root;



    @Override
    public void start(Stage stage) throws IOException {
        // ADD
      GUI gui=new GUI();
      gui.gameMenuGUI(stage);








    }
    public static void main(String[] args) {
        launch();
    }
    public static Stage getWindow(){
        return window;
    }
    public static Scene getActiveScene() {return activeScene;}
    public static double getWidth() {return width;}
    public static double getHeight() {return height;}



    public void closeJavaFxMain(){
        GuiApp.getWindow().close();
    }

    //TODO: refactor del metodo (createGame)


}



