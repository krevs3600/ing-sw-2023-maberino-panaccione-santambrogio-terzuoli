package it.polimi.ingsw.client.view.FXML;


import it.polimi.ingsw.network.ClientImplementation;
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import javafx.scene.paint.Color;



public class JavaFxMain extends Application {
    private static Stage window;
    private static Scene activeScene;
    private static double width;
    private static double height;

    @Override
    public void start(Stage stage) throws IOException {
        // ADD
        GUI view=new GUI();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("start_scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("myShelfie!");
        stage.setScene(scene);
        stage.show();
        stage.setHeight(scene.getHeight());
        stage.setWidth(scene.getWidth());
        window=stage;
        stage.setResizable(true);
        width = stage.getWidth();
        height = stage.getHeight();

        StartController startController=fxmlLoader.getController();// controller iniziale non so se servirà





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

}



