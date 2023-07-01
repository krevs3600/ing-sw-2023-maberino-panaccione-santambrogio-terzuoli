package it.polimi.ingsw.view.gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class GuiApp extends Application {

    public AnchorPane rootPane;

    @Override
    public void start(Stage stage) throws IOException {
      GUI gui = new GUI();
      gui.gameMenuGUI(stage);
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}



