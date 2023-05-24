package it.polimi.ingsw.client.view.FXML;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GameNameController {
    @FXML
    public AnchorPane PaneforParquet;
    @FXML
    public RadioButton two_players;

    @FXML
    public TextField GameName;
    @FXML
    public RadioButton three_players;
    @FXML
    public RadioButton four_players;

    @FXML
    public Button ok;

    @FXML
    public Label AlredytTakenGameName;

    private GUI gui;

    private String gameName;

    public String getGameName(){
        return this.gameName;

    }



    public void GameNameChosen(MouseEvent mouseEvent) {
        //TODO: controllo sugli input spazi vuoti
        this.gameName=GameName.getText();
         gui.askGameName(gameName);
    }

    public void setGui(GUI gui) {
        this.gui=gui;

    }
}
