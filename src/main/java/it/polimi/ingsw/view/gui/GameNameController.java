package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    public String resource = "GameCreation_scene.fxml";

    @FXML
    public TextField textField;

    @FXML
    public Button ok;

    @FXML
    public RadioButton twoPlayers;

    @FXML
    public RadioButton threePlayers;
    @FXML
    public RadioButton fourPlayers;

    private int numberOfPlayersChosen;

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


    public void gameSpec(MouseEvent mouseEvent) {
        if (twoPlayers.isSelected()) {
            this.numberOfPlayersChosen = 2;
        } else if (threePlayers.isSelected()) {
            this.numberOfPlayersChosen = 3;
        } else if (fourPlayers.isSelected()) {
            this.numberOfPlayersChosen = 4;
        } else {
            this.numberOfPlayersChosen = 0;
        }
        this.gameName = textField.getText();
        gui.askGameSpecs();
    }

    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */

    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void goBack(){
        gui.goBackToPreviousScene(resource);
    }

  //  /**
  //   *
  //   This method is used to take the number of players chosen by the game creator as soon as he confirms his choice by pressing the {@link NumberOfPlayersController oKButton} in the
  //   {@code NumberofPlayers_scene} and after that, the number is sent to the GUI to set the game.
  //   * @param mouseEvent the click on the {@link GameNameController oKButton}
  //   */
  //
  //  public void numberOfPlayerChosen(MouseEvent mouseEvent) {
  //      if (twoPlayers.isSelected()) {
  //          this.numberOfPlayersChosen = 2;
  //      } else if (threePlayers.isSelected()) {
  //          this.numberOfPlayersChosen = 3;
  //      } else if (fourPlayers.isSelected()) {
  //          this.numberOfPlayersChosen = 4;
  //      } else {
  //          this.numberOfPlayersChosen = 0;
  //      }
  //      this.gui.askNumberOfPlayers(this.gameName);
  //  }

    /**
     * Getter method
     * @return the {@link GameNameController#numberOfPlayersChosen} present in the {@link GameNameController}
     * which is the number of players chosen by the creator of the game for the newly created game.
     *
     */
    public int getNumberOfPlayersChosen(){
        return numberOfPlayersChosen;
    }
}
