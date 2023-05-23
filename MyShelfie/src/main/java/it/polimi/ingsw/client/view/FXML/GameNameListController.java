package it.polimi.ingsw.client.view.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GameNameListController {


    @FXML
    private GUI gui;

    @FXML
    public TextField GameNameTextField;

    @FXML
    public Button OKButton;


    @FXML
    public Label InvalidGameChoice;

   @FXML
   public String gameToJoin;


    public void setGui(GUI gui){
        this.gui=gui;

    }



    public void SendGameNameChoise(MouseEvent mouseEvent) {
        // TODO: controllo sull'input vuoto
       String gameToJoin= GameNameTextField.getText();
       this.gameToJoin=gameToJoin;
       gui.chosenGame(gameToJoin); // oppure farlo senza input che prende il campo dopo aver fatto l'uguaglianza



    }
}
