package it.polimi.ingsw.client.view.FXML;


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

    @Override
    public void start(Stage stage) throws IOException {




        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("livingboard_scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("myShelfie!");
        stage.setScene(scene);
        stage.show();
        window=stage;
    }
    public static void main(String[] args) {
        launch();
    }
    public static Stage getWindow(){
        return window;
    }

}



