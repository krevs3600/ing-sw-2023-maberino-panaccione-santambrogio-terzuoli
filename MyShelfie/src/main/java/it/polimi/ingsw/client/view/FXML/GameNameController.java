package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.model.Bookshelf;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * <h1>Class GameNameController</h1>
 *this class represents the controller of the fxml scene where
 *  the player who created the game chooses the unique name of his game
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/23/2023
 */


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

    /**
     * Getter method
     * @return the {@link GameNameController#gameName} present in the {@link GameNameController}
     * that the creator of the game typed in the text field of the scene {@code GameName_scene} :
     */
    public String getTextField(){
        return this.gameName;
    }

    /**
     *
     This method is used to take the name of the game typed by the player during game creation as soon as he confirms his choice by pressing the ok button.
     That name is then sent to the GUI to subsequently verify its validity.
     * @param mouseEvent  the click on the {@link GameNameController#ok} button
     */


    public void gameNameChosen(MouseEvent mouseEvent) {
        //TODO: controllo sugli input spazi vuoti
        this.gameName = textField.getText();
        gui.askGameName();
    }

    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */

    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
