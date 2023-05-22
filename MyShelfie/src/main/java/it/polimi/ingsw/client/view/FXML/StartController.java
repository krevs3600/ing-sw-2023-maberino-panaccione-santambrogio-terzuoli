package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.observer_observable.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class StartController extends Observable {
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


    public void closeJavaFxMain(){
       JavaFxMain.getWindow().close();
    }

//TODO: refactor del metodo (createGame)
    public void switchToConnection(ActionEvent event) throws IOException {
        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/RMIorSocket_scene.fxml/").toURI().toURL();

        root = FXMLLoader.load(url);
        stage = JavaFxMain.getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void JoinGame(MouseEvent event) throws IOException{

    }


}


