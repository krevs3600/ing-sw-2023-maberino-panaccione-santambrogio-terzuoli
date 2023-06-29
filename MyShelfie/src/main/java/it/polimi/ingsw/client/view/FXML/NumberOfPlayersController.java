package it.polimi.ingsw.client.view.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


/**
 * <h1>Class NumberOfPlayersController</h1>
 *this class represents the controller of the fxml scene of the lobby where
 *  the player the player chooses the number of players for the newly created game
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/23/2023
 */
public class NumberOfPlayersController {
    public String resource = "NumberofPlayers_scene.fxml";
    @FXML
    public RadioButton twoPlayers;

    @FXML
    public RadioButton threePlayers;
    @FXML
    public RadioButton fourPlayers;

    @FXML
    public Button okButton;

    private int numberOfPlayersChosen;

    private GUI gui;

    private String gameName;
    /**
     * Getter method
     * @return the {@link NumberOfPlayersController#gameName} present in the {@link NumberOfPlayersController}
     * which is the name of the unique game just created by the player
     * who has to choose the number of players in the aforementioned game
     *
     */
    public String getGameName(){
        return this.gameName;
    }

    public void setGameName(String gameName){
        this.gameName=gameName;
    }


    /**
     * Getter method
     * @return the {@link NumberOfPlayersController#numberOfPlayersChosen} present in the {@link NumberOfPlayersController}
     * which is the number of players chosen by the creator of the game for the newly created game.
     *
     */
    public int getNumberOfPlayersChosen(){
        return numberOfPlayersChosen;
    }
    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */
    public void setGui(GUI gui) {
        this.gui=gui;
    }
    /**
     *
     This method is used to take the number of players chosen by the game creator as soon as he confirms his choice by pressing the {@link NumberOfPlayersController oKButton} in the
     {@code NumberofPlayers_scene} and after that, the number is sent to the GUI to set the game.
     * @param mouseEvent the click on the {@link NumberOfPlayersController oKButton}
     */

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
