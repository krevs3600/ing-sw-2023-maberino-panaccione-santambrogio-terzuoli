package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.model.utils.NumberOfPlayers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class NumberOfPlayersController {
    @FXML
    public AnchorPane PaneforParquet;
    @FXML
    public RadioButton two_players;

    @FXML
    public RadioButton three_players;
    @FXML
    public RadioButton four_players;

    @FXML
    public Button ok;

    @FXML
    public Label MissingNumberLabel;

    private int NumberOfPlayersChosen;

    private GUI gui;


    private String gameName;

    public void setGameName(String gameName){
        this.gameName=gameName;

    }

    public int getNumberOfPlayersChosen(){
        return NumberOfPlayersChosen;
    }

    public boolean twoPlayers=false;

    public boolean threePlayers=false;

    public boolean fourPlayers=false;




    public void setGui(GUI gui) {
        this.gui=gui;

    }


    public void two_p(MouseEvent mouseEvent) {
        if (!threePlayers && !fourPlayers)
            twoPlayers = true;
        else {
            threePlayers = false;
            fourPlayers = false;
            twoPlayers = true;

        }
    }

    public void three_p(MouseEvent mouseEvent) {
        if (!twoPlayers && !fourPlayers)
            threePlayers = true;
        else {
            twoPlayers = false;
            fourPlayers = false;
            threePlayers = true;


        }
    }

    public void four_p(MouseEvent mouseEvent) {
        if (!threePlayers && !twoPlayers)
            fourPlayers = true;
        else {
            twoPlayers = false;
            threePlayers = false;
            fourPlayers = true;


        }
    }

    public void NumberofPlayerChosen(MouseEvent mouseEvent) {
        if(twoPlayers){
            this.NumberOfPlayersChosen=2;
        }
        else if (threePlayers){
            this.NumberOfPlayersChosen=3;
        }
        else this.NumberOfPlayersChosen=4;

        this.gui.askNumberOfPlayers(this.gameName);
    }





}
