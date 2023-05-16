package it.polimi.ingsw.client.view.FXML;


import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PersonalGoalCardDeck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static it.polimi.ingsw.client.view.FXML.LivingBoardController.getCommonGoalCardPic;

public class PersonalGoalCardController {
    @FXML
    public Label cgc_description;
    @FXML
    public Label pgc_description;
    @FXML
    public ImageView First_CommonGoalCard;

    @FXML
    public ImageView Second_CommonGoalCard;

    @FXML
    public ImageView PersonalGoalCard;


    public void initialize() throws FileNotFoundException {
        Game game = new Game();
        game.initLivingRoomBoard(NumberOfPlayers.FOUR_PLAYERS);
        Player prova = new Player("prova", game.getPersonalGoalCardDeck());
        CommonGoalCard commonGoalCard1 = game.getLivingRoomBoard().getCommonGoalCards().get(0);
        CommonGoalCard commonGoalCard2 = game.getLivingRoomBoard().getCommonGoalCards().get(1);
        First_CommonGoalCard.setImage(new Image(new FileInputStream(getCommonGoalCardPic(commonGoalCard1))));
        Second_CommonGoalCard.setImage(new Image(new FileInputStream(getCommonGoalCardPic(commonGoalCard2))));

        PersonalGoalCard.setImage(new Image(new FileInputStream("src/main/resources/it/polimi/ingsw/client/view/personal goal cards/Personal_Goals" + String.valueOf(prova.getPersonalGoalCard().getPath() + ".png"))));
    }
}
