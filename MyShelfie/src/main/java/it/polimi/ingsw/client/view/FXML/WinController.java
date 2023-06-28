package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.model.ModelView.GameView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * <h1>Class WinController</h1>
 *this class represents the controller of the fxml scene where
 * the controller of the win scene where each player is shown
 * whether he won or lost with the corresponding scores
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/14/2023
 */



public class WinController {


    @FXML
    public ImageView Trophy_2;

    @FXML
    public ImageView Trophy_1;


    @FXML
    public ImageView Trophy_3;

    @FXML
    public ImageView Trophy_4;

    @FXML
    public Label congratulations;

    @FXML
    public Label youWonTheGame;


    @FXML
    public Label bestPlayer;

    @FXML
    public Label lost;

    @FXML
    public Label dontGiveUp;

    @FXML
    public Label willBeBetter;

    @FXML
    public ImageView fail_4;
    @FXML
    public ImageView fail_1;

    @FXML
    public ImageView fail_3;

    @FXML
    public ImageView fail_2;

    @FXML
    public Label myscoretext;

    @FXML
    public Label secondPlayerScoreTxt;

    @FXML
    public Label thirdPlayerScoreTxt;


    @FXML
    public Separator lineone;

    @FXML
    public Label fourthPlayerScoretxt;

    @FXML
    public Separator linetwo;

    @FXML
    public Button viewD_2player;

    @FXML
    public Button viewD_3player;

    @FXML
    public Button viewD_4player;

    @FXML
    public Button MyScoreDetail;

    @FXML
    public Button backToMenu;

    private GameView game;
    private GUI gui;

    public void setGame(GameView game) {
        this.game = game;
    }

    /**
     *This method is used to allow the player to see details about second player's score
     * @param mouseEvent  the click on the {@link WinController viewD_2player} button
     */

    public void second_p_score(MouseEvent mouseEvent) {

    }


    /**
     *This method is used to allow the player to see details about third player's score (in case it is a 3-player game)
     * @param mouseEvent the click on the {@link WinController viewD_3player} button
     */
    public void third_p_score(MouseEvent mouseEvent) {

    }


    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    /**
     *This method is used to allow the player to see details about his own score
     *  @param mouseEvent  the click on the {@link WinController MyScoreDetail} button
     */

    public void showDetailsOfTheScore(MouseEvent mouseEvent) {
        gui.showThisClientScores(game);
    }

    /**
     *This method is used to allow the player to see details about fourth player's score (in case it is a 4-player game)
     * @param mouseEvent the click on the {@link WinController viewD_4player} button
     */

    public void fourth_p_score(MouseEvent mouseEvent) {

    }

    public void backToMenu(MouseEvent mouseEvent) {
        gui.goBackToPreviousScene("win_scene.fxml");
    }
}
