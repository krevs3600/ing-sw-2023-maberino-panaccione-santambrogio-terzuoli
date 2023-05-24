package it.polimi.ingsw.client.view.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GameNameListController {


    @FXML
    private GUI gui;


    @FXML
    public ListView<String> listView;
    @FXML
    public TextField GameNameTextField;

    @FXML
    public Button OKButton;


    @FXML
    public Label InvalidGameChoice;

   @FXML
   public String gameToJoin;


    public GameNameListController(GUI gui) {
        this.gui = gui;
    }

    public void setGui(GUI gui){
        this.gui=gui;

    }



    public void SendGameNameChoise(MouseEvent mouseEvent) {
        // TODO: controllo sull'input vuoto
       String gameToJoin= GameNameTextField.getText();
       this.gameToJoin=gameToJoin;
       gui.chosenGame(gameToJoin); // oppure farlo senza input che prende il campo dopo aver fatto l'uguaglianza



    }

    public void init(){
        ObservableList<String> names = FXCollections.observableArrayList(
                "Game", "Game1", "Game2");
        listView.getItems().setAll(names);
    }
}
