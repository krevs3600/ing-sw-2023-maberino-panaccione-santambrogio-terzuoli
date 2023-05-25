package it.polimi.ingsw.client.view.FXML;

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
    public TextField textField;

    @FXML
    public Button ok;

    @FXML
    public Label alreadyTakenGameName;

    private GUI gui;

    private String gameName;

    public String getTextField(){
        return this.gameName;
    }

    public void gameNameChosen(MouseEvent mouseEvent) {
        //TODO: controllo sugli input spazi vuoti
        this.gameName = textField.getText();
        gui.askGameName();
    }

    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
