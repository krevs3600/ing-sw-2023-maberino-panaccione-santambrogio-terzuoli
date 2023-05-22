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

    public ServerSettingsController serverSettingsController;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private GUI gui;


    public void setGui(GUI gui){
        this.gui=gui;
    }
    public void closeJavaFxMain(){
       GuiApp.getWindow().close();
    }

//TODO: refactor del metodo (createGame)
    public void switchToConnection(ActionEvent event) throws IOException {
        gui.askTypeofConnection(gui.getStage());



    }


    public void JoinGame(MouseEvent event) throws IOException{

    }


}


