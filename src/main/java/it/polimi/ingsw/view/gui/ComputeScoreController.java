package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.ModelView.PlayerView;
import it.polimi.ingsw.model.RomanNumber;
import it.polimi.ingsw.model.ScoringToken;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import static it.polimi.ingsw.view.gui.LivingBoardController.getCommonGoalCardPic;

public class ComputeScoreController {
    public String resource = "computeScore_scene.fxml";
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

    @FXML
    public Button backToMenu;

    private GUI gui;

    // Creazione di un oggetto Font con il font desiderato
    Font font = Font.loadFont(getClass().getResourceAsStream("it/polimi/ingsw/client/view/fonts/LillyBelle.ttf"), 24);

    // Impostazione del font sulla proprietà font della Label



    public void initialize(GameView game, String nickname){
        // loading the configuration of the bookshelf
        updateBookshelf(game.getPlayer(nickname).getBookshelf(), bookshelf);
        // loading the two CommonGoalCards
        commonGoalCard1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(getCommonGoalCardPic(game.getLivingRoomBoard().getCommonGoalCards().get(0))))));
        commonGoalCard2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(getCommonGoalCardPic(game.getLivingRoomBoard().getCommonGoalCards().get(1))))));
        // loading the PersonalGoalCard
        int num = game.getPlayer(nickname).getPersonalGoalCard().getPath();
        personalGoalCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/personal goal cards/Personal_Goals" + num + ".png"))));

        commonPoint1.setFont(font);
        commonPoint2.setFont(font);
        personalGoalPoint.setFont(font);
        endGamePoint.setFont(font);
        generalPoint.setFont(font);
        totalScore.setFont(font);

    }

    public void loadScore(PlayerView player) {
        // loading the checkmark image from path
        String checkmarkPath = "/misc/checkmark.png";
        Image checkmark = new Image(Objects.requireNonNull(getClass().getResourceAsStream(checkmarkPath)));

        // updating CommonGoalCards points
        for (ScoringToken scoringToken : player.getTokens()){
            if (scoringToken.getRomanNumber().equals(RomanNumber.ONE)){
                checkFirstCommonGoal.setImage(checkmark);
                commonPoint1.setText("= " + scoringToken.getValue() + " points");
            }
            if (scoringToken.getRomanNumber().equals(RomanNumber.TWO)){
                checkSecondCommonGoal.setImage(checkmark);
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

    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void goBack(){
        gui.goBackToPreviousScene(resource);
    }

    public void backToMenu(MouseEvent mouseEvent) {
        gui.goBackToPreviousScene("win_scene.fxml");
    }

    /**
     * Initialize ImageViews of the bookshelf in the gridPane
     * @param bookshelfGrid gridPane
     */
    public static void initBookshelf(GridPane bookshelfGrid){
        for(int r=0;r<bookshelfGrid.getRowCount(); r++){
            for (int c=0; c<bookshelfGrid.getColumnCount(); c++){
                ImageView imageView = new ImageView();
                imageView.setFitWidth((bookshelfGrid.getPrefWidth() - (int)bookshelfGrid.getPadding().getLeft() - (int)bookshelfGrid.getPadding().getRight() - ((bookshelfGrid.getColumnCount()-1)*bookshelfGrid.getHgap()))/bookshelfGrid.getColumnCount());
                imageView.setFitHeight((bookshelfGrid.getPrefHeight() - (int)bookshelfGrid.getPadding().getTop() - (int)bookshelfGrid.getPadding().getBottom() - ((bookshelfGrid.getRowCount()-1)*bookshelfGrid.getVgap()))/bookshelfGrid.getRowCount());
                imageView.setImage(null);
                imageView.setOnMouseClicked(null);
                bookshelfGrid.add(imageView, c,r);
            }
        }
    }

    /**
     * Load the current tiles' displacement of the provided bookshelf in the gridPane
     * @param bookshelf player's bookshelf
     * @param bookshelfGrid JavaFX gridPane
     */
    public void updateBookshelf(BookshelfView bookshelf, GridPane bookshelfGrid) {
        initBookshelf(bookshelfGrid);
        for(int r=0;r<bookshelfGrid.getRowCount(); r++){
            for (int c=0; c<bookshelfGrid.getColumnCount(); c++){

                if (bookshelf.getGrid()[r][c] != null){
                    Image tile = new Image(Objects.requireNonNull(getClass().getResourceAsStream(tilePath(bookshelf.getGrid()[r][c]))));
                    ImageView bookshelfImage = (ImageView) bookshelfGrid.getChildren().get(r*5 + c);
                    bookshelfImage.setImage(tile);
                } else {
                    ImageView bookshelfImage = (ImageView) bookshelfGrid.getChildren().get(r*5 + c);
                    bookshelfImage.setImage(null);
                }
            }
        }
    }

    /**
     * Given an ItemTile it return its graphical representation's path
     * @param itemTile ItemTile
     * @return image's path of the ItemTile
     */
    private String tilePath(ItemTile itemTile){
        String path = "/itemtiles/";
        return path.concat(itemTile.getType().toString()).concat("1.").concat(itemTile.getImageIndex()).concat(".png");
    }
}
