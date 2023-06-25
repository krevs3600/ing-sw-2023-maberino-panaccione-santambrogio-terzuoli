package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.model.ModelView.GameView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static it.polimi.ingsw.client.view.FXML.LivingBoardController.getCommonGoalCardPic;

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
    public ImageView Yes_FirstCommonGoal;

    @FXML
    public ImageView Yes_SecondCommonGoal;

    @FXML
    public ImageView Yes_FirstEndGame;

    @FXML
    public ImageView Yes_PersonalGoalCard;


    @FXML
    public StackPane Yes_ExtraPoint;

    @FXML
    public ImageView No_ExtraPoint;

    @FXML
    public ImageView No_FirstCommonGoal;

    @FXML
    public ImageView No_SecondCommonGoal;

    @FXML
    public ImageView No_FirsrtEndGame;

    @FXML
    public ImageView No_PersonalGoalCard;






    @FXML
    public ImageView first_view;

    @FXML
    public ImageView second_view;




    public void initialize(GameView game, String nickname){
        LivingBoardController.initBookshelf(bookshelf);
        LivingBoardController.updateBookshelf(game.getPlayer(nickname).getBookshelf(), bookshelf);

        try {
            commonGoalCard1.setImage(new Image(new FileInputStream(getCommonGoalCardPic(game.getLivingRoomBoard().getCommonGoalCards().get(0)))));
            commonGoalCard2.setImage(new Image(new FileInputStream(getCommonGoalCardPic(game.getLivingRoomBoard().getCommonGoalCards().get(1)))));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        int num = game.getSubscribers().stream().filter(x-> x.getName().equals(nickname)).toList().get(0).getPersonalGoalCard().getPath();
        try {
            personalGoalCard.setImage(new Image(new FileInputStream("src/main/resources/it/polimi/ingsw/client/view/personal goal cards/Personal_Goals" + num + ".png")));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
