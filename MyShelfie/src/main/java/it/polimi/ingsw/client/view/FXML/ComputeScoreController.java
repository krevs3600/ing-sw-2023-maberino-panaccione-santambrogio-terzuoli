package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.ModelView.PlayerView;
import it.polimi.ingsw.model.RomanNumber;
import it.polimi.ingsw.model.ScoringToken;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static it.polimi.ingsw.client.view.FXML.LivingBoardController.getCommonGoalCardPic;
import static it.polimi.ingsw.client.view.FXML.LivingBoardController.updateBookshelf;

public class ComputeScoreController {
    @FXML
    public GridPane bookshelf;
    @FXML
    public ImageView commonGoalCard1;
    @FXML
    public ImageView commonGoalCard2;
    @FXML
    public ImageView endGameToken;
    @FXML
    public ImageView personalGoalCard;

    @FXML
    public Label commonPoint2;
    @FXML
    public Label commonPoint1;
    @FXML
    public Label personalGoalPoint;
    @FXML
    public Label endGamePoint;
    @FXML
    public Label generalPoint;
    @FXML
    public Label totalScore;


    @FXML
    public ImageView checkFirstCommonGoal;
    @FXML
    public ImageView checkSecondCommonGoal;
    @FXML
    public ImageView checkFirstToFinish;
    @FXML
    public ImageView checkPersonalGoalCard;
    @FXML
    public ImageView checkAdjacent;


    public void initialize(GameView game, String nickname){
        // loading the configuration of the bookshelf
        updateBookshelf(game.getPlayer(nickname).getBookshelf(), bookshelf);
        // loading the two CommonGoalCards
        try {
            commonGoalCard1.setImage(new Image(new FileInputStream(getCommonGoalCardPic(game.getLivingRoomBoard().getCommonGoalCards().get(0)))));
            commonGoalCard2.setImage(new Image(new FileInputStream(getCommonGoalCardPic(game.getLivingRoomBoard().getCommonGoalCards().get(1)))));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        // loading the PersonalGoalCard
        int num = game.getPlayer(nickname).getPersonalGoalCard().getPath();
        try {
            personalGoalCard.setImage(new Image(new FileInputStream("src/main/resources/it/polimi/ingsw/client/view/personal goal cards/Personal_Goals" + num + ".png")));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadScore(PlayerView player) {
        // loading the checkmark image from path
        String checkmarkPath = "src/main/resources/it/polimi/ingsw/client/view/misc/checkmark.png";
        Image checkmark = null;
        try {
            checkmark = new Image(new FileInputStream(checkmarkPath));
        } catch (FileNotFoundException e) {
            System.err.println("Can't find image checkmark.png in " + checkmarkPath);
        }
        // updating CommonGoalCards points
        System.out.println(player.getTokens());
        for (ScoringToken scoringToken : player.getTokens()){
            if (scoringToken.getRomanNumber().equals(RomanNumber.ONE)){
                checkFirstCommonGoal.setImage(checkmark);
                commonPoint1.setText("= " + scoringToken.getValue() + " points");
            }
            if (scoringToken.getRomanNumber().equals(RomanNumber.TWO)){
                checkFirstCommonGoal.setImage(checkmark);
                commonPoint2.setText("= " + scoringToken.getValue() + " points");
            }
        }
        // updating firstToFinish point
        if (player.isFirstToFinish()){
            checkFirstToFinish.setImage(checkmark);
            endGamePoint.setText("= 1 point");
        }
        // updating personalGoalCard points
        if (player.getPersonalGoalCardScore() > 0) {
            checkPersonalGoalCard.setImage(checkmark);
            personalGoalPoint.setText("= " + player.getPersonalGoalCardScore() + " points");
        }
        // updating adjacent points
        if (player.getAdjacentTilesScore() > 0) {
            checkAdjacent.setImage(checkmark);
            generalPoint.setText("= " + player.getAdjacentTilesScore() + " points");
        }
        totalScore.setText("= " + player.getScore() + " points");
    }
}
