package it.polimi.ingsw.client.view.FXML;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.ModelView.*;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import it.polimi.ingsw.model.utils.TileType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class LivingBoardController {
    @FXML
    public GridPane board;

    @FXML
    public Group tilePack;

    @FXML
    public Group columnOptions;

    @FXML
    public GridPane bookshelf;

    @FXML
    public ImageView First_2;

    @FXML
    public ImageView First_4;

    @FXML
    public ImageView First_6;

    @FXML
    public ImageView First_8;

    @FXML
    public ImageView Second_2;
    @FXML
    public ImageView Second_4;

    @FXML
    public ImageView Second_6;

    @FXML
    public ImageView Second_8;

    @FXML
    public AnchorPane playerBookshelves;

    @FXML
    public ImageView personalCard;

    @FXML
    public ImageView commonGoalCard1;
    @FXML
    public ImageView commonGoalCard2;
    @FXML
    public GridPane otherBookshelf;
    @FXML
    public Button previousPlayer;
    @FXML
    public Button nextPlayer;
    @FXML
    public Button playerBookshelf;

    @FXML
    public Button ContinuepickingT;

    @FXML
    public Button stopPickingT;
    @FXML
    public Button Column1;

    @FXML
    public Button Column2;

    @FXML
    public Button Column5;

    @FXML
    public Button Column4;

    @FXML
    public Button Column3;

    @FXML
    public Label OtherPlayerTurnLabel;





    private boolean columnSelected = false;
    private int column;
    private String nickname;
    private GUI gui;
    private Map<TileType, String[]> images = new HashMap<>();
    private int personalGameIndex;
    private int watchedPlayer;
    private Map<Integer, String> players = new HashMap<>();
    private GameView gameView;

    public void setGameView(GameView gameView){
        this.gameView = gameView;
    }

    private String tilePath(ItemTile itemTile){
        String path = "src/main/resources/it/polimi/ingsw/client/view/itemtiles/";
        return path.concat(itemTile.getType().toString()).concat("1.").concat(itemTile.getImageIndex()).concat(".png");
    }
    public void initialize(GameView gameView, String nickname) throws FileNotFoundException {
        setGameView(gameView);
        this.nickname = nickname;
        personalGameIndex = getPersonalGameIndex(gameView);
        for (int i=0; i< gameView.getSubscribers().size(); i++){
            players.put(i, gameView.getSubscribers().get(i).getName());
        }

        // loading itemTiles paths and creating support structures

        /*
        String[] cats = {"Gatti1.1.png", "Gatti1.2.png", "Gatti1.3.png"};
        String[] frames = {"Cornici1.1.png", "Cornici1.2.png", "Cornici1.3.png"};
        String[] games = {"Giochi1.1.png", "Giochi1.2.png", "Giochi1.3.png"};
        String[] books = {"Libri1.1.png", "Libri1.2.png", "Libri1.3.png"};
        String[] plants = {"Piante1.1.png", "Piante1.2.png", "Piante1.3.png"};
        String[] trophey = {"Trofei1.1.png", "Trofei1.2.png", "Trofei1.3.png"};
        images.put(TileType.CAT, cats);
        images.put(TileType.FRAME, frames);
        images.put(TileType.GAME, games);
        images.put(TileType.BOOK, books);
        images.put(TileType.PLANT, plants);
        images.put(TileType.TROPHY, trophey);*/

        // INIT LIVING_ROOM_BOARD

        // String path = "src/main/resources/it/polimi/ingsw/client/view/itemtiles/";
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                SpaceView space = gameView.getLivingRoomBoard().getSpace(new Position(r,c));
                if (space.getType() == SpaceType.PLAYABLE){
                    ImageView imageView = new ImageView(new Image(new FileInputStream(tilePath(space.getTile()))));
                    imageView.setFitWidth((board.getPrefWidth() - ((board.getColumnCount()-1)*board.getHgap()))/board.getColumnCount()-10);
                    imageView.setFitHeight((board.getPrefHeight() - ((board.getRowCount()-1)*board.getVgap()))/board.getRowCount()-10);
                    imageView.setOnMouseClicked(this::boardTileClicked);
                    board.add(imageView, c, r);
                }
                else {
                    board.add(new ImageView(), c, r);
                }
            }
        }

        // INIT TILEPACK
        updateTilePack(gameView.getTilePack());


        // INIT BOOKSHELF
        for (int r=0; r<6;r++){
            for (int c=0; c<5; c++){
                ImageView imageView = new ImageView();
                imageView.setFitWidth((bookshelf.getPrefWidth() - (int)bookshelf.getPadding().getLeft() - (int)bookshelf.getPadding().getRight() - ((bookshelf.getColumnCount()-1)*bookshelf.getHgap()))/bookshelf.getColumnCount());
                imageView.setFitHeight((bookshelf.getPrefHeight() - (int)bookshelf.getPadding().getTop() - (int)bookshelf.getPadding().getBottom() - ((bookshelf.getRowCount()-1)*bookshelf.getVgap()))/bookshelf.getRowCount());
                imageView.setImage(null);
                bookshelf.add(imageView, c,r);
            }
        }
        for (int r=0; r<6;r++){
            for (int c=0; c<5; c++){
                ImageView imageView = new ImageView();
                imageView.setFitWidth((otherBookshelf.getPrefWidth() - (int)otherBookshelf.getPadding().getLeft() - (int)otherBookshelf.getPadding().getRight() - ((otherBookshelf.getColumnCount()-1)*otherBookshelf.getHgap()))/otherBookshelf.getColumnCount());
                imageView.setFitHeight((otherBookshelf.getPrefHeight() - (int)otherBookshelf.getPadding().getTop() - (int)otherBookshelf.getPadding().getBottom() - ((otherBookshelf.getRowCount()-1)*otherBookshelf.getVgap()))/otherBookshelf.getRowCount());
                imageView.setImage(null);
                otherBookshelf.add(imageView, c,r);
            }
        }
        System.out.println(gameView.getSubscribers().size());
        if (gameView.getSubscribers().size() == 2){
            nextPlayer.setVisible(false);
            previousPlayer.setVisible(false);
            playerBookshelf.setText(gameView.getSubscribers().stream().map(PlayerView::getName).filter(x->!x.equals(nickname)).toList().get(0));
            watchedPlayer = players.get(0).equals(nickname) ? 1 : 0;
            playerBookshelf.setText(players.get(watchedPlayer));
        } else {
            if (watchedPlayer < players.size()-1){
                watchedPlayer +=1;
            } else watchedPlayer = 0;
            if (watchedPlayer == personalGameIndex){
                if (watchedPlayer < players.size()-1){
                    watchedPlayer +=1;
                } else watchedPlayer = 0;
            }
            playerBookshelf.setText(players.get(watchedPlayer));
        }
/*
        System.out.println(game.getNumberOfPlayers().getValue());
        int j = 0;
        for (int i=0; i<game.getNumberOfPlayers().getValue(); i++){
            if(!Objects.equals(game.getSubscribers().get(i).getName(), nickname)){
                Button button = (Button) playerBookshelves.getChildren().get(j);
                button.setVisible(true);
                button.setText(game.getSubscribers().get(i).getName() + "'s bookshelf");
                button.setOnMouseClicked(this::bookshelfButtonPressed);
                j++;
            }
        }
*/
        //init personal Card
        int num = gameView.getSubscribers().stream().filter(x-> x.getName().equals(nickname)).toList().get(0).getPersonalGoalCard().getPath();
        personalCard.setImage(new Image(new FileInputStream("src/main/resources/it/polimi/ingsw/client/view/personal goal cards/Personal_Goals" + num + ".png")));

        //init common goal card
        commonGoalCard1.setImage(new Image(new FileInputStream(getCommonGoalCardPic(gameView.getLivingRoomBoard().getCommonGoalCards().get(0)))));
        commonGoalCard2.setImage(new Image(new FileInputStream(getCommonGoalCardPic(gameView.getLivingRoomBoard().getCommonGoalCards().get(1)))));
    }

    private int getPersonalGameIndex(GameView gameView) {
        for(int i=0; i<gameView.getSubscribers().size(); i++){
            if(gameView.getSubscribers().get(i).getName().equals(nickname)){
                personalGameIndex = i;
                break;
            }
        }
        return personalGameIndex;
    }

    private void initTilePack(){
        for (Node node : tilePack.getChildren()){
            ImageView imageView = (ImageView) node;
            ((ImageView) node).setImage(null);
            if (imageView.getEventDispatcher() == null){
                imageView.setOnMouseClicked(this::packTileClicked);
            }
        }
    }

    public void updateBookshelf(BookshelfView bookshelf, GridPane bookshelfGrid) throws FileNotFoundException {
        for(int r=0;r<bookshelfGrid.getRowCount(); r++){
            for (int c=0; c<bookshelfGrid.getColumnCount(); c++){
                // TODO: set a fixed image
                if (bookshelf.getGrid()[r][c] != null){
                    Image tile = new Image(new FileInputStream(tilePath(bookshelf.getGrid()[r][c])));
                    ImageView bookshelfImage = (ImageView) bookshelfGrid.getChildren().get(r*5 + c);
                    bookshelfImage.setImage(tile);
                }
            }
        }
    }

    public void updateTilePack(TilePackView tilePackView) throws FileNotFoundException {
        //initTilePack();
        for (int i=0; i<3; i++){
            ImageView imageView = (ImageView) tilePack.getChildren().get(i);
            if (i<tilePackView.getTiles().size()){
                imageView.setImage(new Image(new FileInputStream(tilePath(tilePackView.getTiles().get(i)))));
                if (imageView.getEventDispatcher() == null){
                    imageView.setOnMouseClicked(this::packTileClicked);
                }
            } else {
                imageView.setOnMouseClicked(null);
                imageView.setImage(null);
            }
        }
    }

    public void updateLivingRoomBoard(LivingRoomBoardView livingRoomBoardView) throws FileNotFoundException {
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                SpaceView space = livingRoomBoardView.getSpace(new Position(r,c));
                ImageView imageView = (ImageView) board.getChildren().get(r*9 + c);
                if (!space.isFree() && space.getType().equals(SpaceType.PLAYABLE)){
                    imageView.setImage(new Image(new FileInputStream(tilePath(space.getTile()))));
                }
                else {
                    imageView.setOnMouseClicked(null);
                    imageView.setImage(null);
                }
            }
        }
    }

    private void radioButtonPressed(MouseEvent event) {
        System.out.println("Button pressed");
        RadioButton buttonEvent = (RadioButton) event.getSource();
        this.column = Integer.parseInt(buttonEvent.getId().replaceAll("Column", ""))-1;
        System.out.println(column);
        columnSelected = true;
    }

    private void packTileClicked(MouseEvent event) {
        if (columnSelected){
            System.out.println("carta sel");
            ImageView sourceImageView = (ImageView) event.getSource();
            for(int r=bookshelf.getRowCount()-1; r>0; r--){
                ImageView bookshelfImage = (ImageView) bookshelf.getChildren().get(r*5 + column);
                if (bookshelfImage.getImage() == null) {
                    bookshelfImage.setImage(sourceImageView.getImage());
                    sourceImageView.setImage(null);
                }
            }
        }
    }

    public void boardTileClicked(MouseEvent event){
        ImageView sourceImageView = (ImageView) event.getSource();
        for (int i=0; i<board.getChildren().size(); i++){
            if (board.getChildren().get(i).equals(sourceImageView)){
                int r = i/9;
                int c = i%9;
                System.out.println(r + " " + c);
                Platform.runLater(()->this.gui.chosenPosition(r,c));
                break;
            }
        }
    }

    public void changeBookshelf(MouseEvent event) throws FileNotFoundException {
        if (event.getSource().equals(nextPlayer)){
            if (watchedPlayer < players.size()-1){
                watchedPlayer +=1;
            } else watchedPlayer = 0;
            if (watchedPlayer == personalGameIndex){
                if (watchedPlayer < players.size()-1){
                    watchedPlayer +=1;
                } else watchedPlayer = 0;
            }
            playerBookshelf.setText(players.get(watchedPlayer));
            updateBookshelf(gameView.getPlayer(players.get(watchedPlayer)).getBookshelf(), otherBookshelf);
        }
        else if (event.getSource().equals(previousPlayer)){

            if (watchedPlayer > 0){
                watchedPlayer -=1;
            } else watchedPlayer = players.size()-1;
            if (watchedPlayer == personalGameIndex){
                if (watchedPlayer > 0){
                    watchedPlayer -=1;
                } else watchedPlayer = players.size()-1;
            }
            playerBookshelf.setText(players.get(watchedPlayer));
            updateBookshelf(gameView.getPlayer(players.get(watchedPlayer)).getBookshelf(), otherBookshelf);
        }
    }


    public void scoringTokenClicked(MouseEvent event){
        First_8.setVisible(false);
    }

    public void setGui(GUI gui){
        this.gui = gui;
    }

    public static String getCommonGoalCardPic(CommonGoalCardView card){
        String path = "src/main/resources/it/polimi/ingsw/client/view/common goal cards/";
        switch (card.getType()) {
            case "TwoSquaresCommonGoalCard" -> {
                return path + "1.jpg";
            }
            case "TwoColumnsCommonGoalCard" -> {
                return path + "2.jpg";
            }
            case "FourGroupsCommonGoalCard" -> {
                return path + "3.jpg";
            }
            case "SixGroupsCommonGoalCard" -> {
                return path + "4.jpg";
            }
            case "ThreeColumnsCommonGoalCard" -> {
                return path + "5.jpg";
            }
            case "TwoLinesCommonGoalCard" -> {
                return path + "6.jpg";
            }
            case "FourLinesCommonGoalCard" -> {
                return path + "7.jpg";
            }
            case "CornersCommonGoalCard" -> {
                return path + "8.jpg";
            }
            case "EightTilesCommonGoalCard" -> {
                return path + "9.jpg";
            }
            case "CrossCommonGoalCard" -> {
                return path + "10.jpg";
            }
            case "DiagonalCommonGoalCard" -> {
                return path + "11.jpg";
            }
            case "IncreasingColumnsCommonGoalCard" -> {
                return path + "12.jpg";
            }
            default -> {
                return path + "back.jpg";
            }
        }
    }

    private boolean stopPickingTiles=false;
    private boolean anotherTile=false;

    public boolean WantStopPickingTiles(){
        return stopPickingTiles;

    }
    public boolean WantPickAnotherTile(){
        return anotherTile;

    }

    public void resetStopPickingTiles(){
        this.stopPickingTiles=true;
    }

    public void resetAnotherTile(){
        this.anotherTile=true;
    }

    public void StopTiles(MouseEvent mouseEvent) {
        stopPickingTiles=true;
    }

    public void AnotherTile(MouseEvent mouseEvent) {
        anotherTile=true;


    }

    public void InsertInColumn1(MouseEvent mouseEvent) {
        columnSelected=true;
        column=0;
        Column2.setDisable(true);
        Column3.setDisable(true);
        Column4.setDisable(true);
        Column5.setDisable(true);
        Platform.runLater(()->this.gui.stopPickingTiles(0));
    }

    public void InsertInColumn2(MouseEvent mouseEvent) {
        columnSelected=true;
            Column1.setDisable(true);
            Column3.setDisable(true);
            Column4.setDisable(true);
            Column5.setDisable(true);
        this.gui.stopPickingTiles(1);
    }

    public void InsertInColumn3(MouseEvent mouseEvent) {
        columnSelected=true;
        Column1.setDisable(true);
        Column2.setDisable(true);
        Column4.setDisable(true);
        Column5.setDisable(true);
        this.gui.stopPickingTiles(2);

    }

    public void InsertInColumn4(MouseEvent mouseEvent) {
        columnSelected=true;
        Column1.setDisable(true);
        Column3.setDisable(true);
        Column2.setDisable(true);
        Column5.setDisable(true);
        this.gui.stopPickingTiles(3);

    }

    public void InsertInColumn5(MouseEvent mouseEvent) {
        columnSelected=true;
        Column1.setDisable(true);
        Column3.setDisable(true);
        Column4.setDisable(true);
        Column2.setDisable(true);
        this.gui.stopPickingTiles(4);
    }
}

