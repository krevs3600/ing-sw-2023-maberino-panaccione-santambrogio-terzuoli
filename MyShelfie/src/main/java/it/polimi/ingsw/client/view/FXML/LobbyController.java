package it.polimi.ingsw.client.view.FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LobbyController {

    @FXML
    public Label numPinLobby;

    @FXML
    public Label NumberOfMissingPlayers;

    @FXML
    public Label PlayerJoinedGame;
    private GUI gui;

    private int PinLobby;


    public void setPinLobby(int num){
        this.PinLobby=num;
    }

    public int getPinLobby() {
        return PinLobby;
    }

    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
