package it.polimi.ingsw.client.view.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class WinController {
    @FXML
    public ImageView GoBackToYourScore;
    @FXML
    public Button viewD_1;

    @FXML
    public Button viewD_2;

    @FXML
    public Button viewD_3;

    @FXML
    public ImageView Throphy_2;

    @FXML
    public ImageView Throphy_1;


    @FXML
    public ImageView Throphy_3;

    @FXML
    public ImageView Throphy_4;

    @FXML
    public Label congratulations;

    @FXML
    public Label YouWonTheGame;


    @FXML
    public Label bestplayer;

    @FXML
    public Label lost;

    @FXML
    public Label DontGiveUp;

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
    public Label secondPlayeRScoretxt;

    @FXML
    public Label thirdPlayerscoretxt;


    @FXML
    public Separator lineone;

    @FXML
    public Label FourthPlayerScoretxt;

    @FXML
    public Separator linetwo;

    @FXML
    public Button viewD_secondPlayer;

    @FXML
    public Button viewD_3player;

    @FXML
    public Button viewD_4player;

    @FXML
    public Button MyScoreDetail;




    private GUI gui;


    public void goBackOnMyScore(MouseEvent mouseEvent) {

    }

    public void second_p_score(MouseEvent mouseEvent) {

    }

    public void third_p_score(MouseEvent mouseEvent) {

    }

    public void setGui(GUI gui) {
        this.gui=gui;

    }

    public void showDetailsOfTheScore(MouseEvent mouseEvent) {
        gui.showThisClientScores();

    }

    public void fourth_p_score(MouseEvent mouseEvent) {

    }
}
