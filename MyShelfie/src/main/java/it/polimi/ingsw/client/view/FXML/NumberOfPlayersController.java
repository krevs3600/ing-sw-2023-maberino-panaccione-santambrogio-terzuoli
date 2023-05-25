package it.polimi.ingsw.client.view.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class NumberOfPlayersController {
    @FXML
    public AnchorPane PaneforParquet;
    @FXML
    public RadioButton twoPlayers;

    @FXML
    public RadioButton threePlayers;
    @FXML
    public RadioButton fourPlayers;

    @FXML
    public Button okButton;

    @FXML
    public Label missingNumberLabel;

    private int numberOfPlayersChosen;

    private GUI gui;

    private String gameName;

    public void setGameName(String gameName){
        this.gameName=gameName;
    }

    public int getNumberOfPlayersChosen(){
        return numberOfPlayersChosen;
    }

    public void setGui(GUI gui) {
        this.gui=gui;
    }


    public void numberOfPlayerChosen(MouseEvent mouseEvent) {
        if (twoPlayers.isSelected()) {
            this.numberOfPlayersChosen = 2;
        } else if (threePlayers.isSelected()) {
            this.numberOfPlayersChosen = 3;
        } else if (fourPlayers.isSelected()) {
            this.numberOfPlayersChosen = 4;
        } else {
            this.numberOfPlayersChosen = 0;
        }
        this.gui.askNumberOfPlayers(this.gameName);
    }
}
