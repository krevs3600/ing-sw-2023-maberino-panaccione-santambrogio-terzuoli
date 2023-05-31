package it.polimi.ingsw.client.view.FXML;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.ModelView.*;
import it.polimi.ingsw.model.ScoringToken;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import it.polimi.ingsw.model.utils.TileType;
import it.polimi.ingsw.network.eventMessages.CommonGoalCardMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class LivingBoardController {
    @FXML
    public GridPane board;
    @FXML
    public Group tilePack;
    @FXML
    public Group columnButtons;
    @FXML
    public GridPane bookshelf;
    @FXML
    public ImageView tokenCommonCard1;
    @FXML
    public ImageView tokenCommonCard2;
    @FXML
    public AnchorPane playerBookshelves;
    @FXML
    public ImageView personalCard;
    @FXML
    public ImageView commonGoalCard1;
    @FXML
    public ImageView commonGoalCard2;
    @FXML
    public ImageView seatToken;
    @FXML
    public GridPane otherBookshelf;
    @FXML
    public Button previousPlayer;
    @FXML
    public Button nextPlayer;
    @FXML
    public Button playerBookshelf;
    @FXML
    public Label OtherPlayerTurnLabel;
    @FXML
    ImageView tokenCommonGoalCard1;
    @FXML
    ImageView tokenCommonGoalCard2;


    @FXML
    public AnchorPane anchorPaneForTheCandPGoalCards;

    private boolean columnSelected = false;
    private String nickname;
    private GUI gui;
    private Map<TileType, String[]> images = new HashMap<>();
    private int personalGameIndex;

    public int getWatchedPlayer() {
        return watchedPlayer;
    }

    private int watchedPlayer;

    public Map<Integer, String> getPlayers() {
        return players;
    }

    private Map<Integer, String> players = new HashMap<>();
    private GameView gameView;

    public void setGameView(GameView gameView){
        this.gameView = gameView;
    }

    private static String tilePath(ItemTile itemTile){
        String path = "src/main/resources/it/polimi/ingsw/client/view/itemtiles/";
        return path.concat(itemTile.getType().toString()).concat("1.").concat(itemTile.getImageIndex()).concat(".png");
    }
    public void initialize(GameView gameView, String nickname) {
        setGameView(gameView);
        this.nickname = nickname;
        personalGameIndex = getPersonalGameIndex(gameView);
        for (int i=0; i< gameView.getSubscribers().size(); i++){
            players.put(i, gameView.getSubscribers().get(i).getName());
        }
        // set firstSeatToken
        if (nickname.equals(gameView.getSubscribers().get(0).getName())){
            try {
                seatToken.setImage(new Image(new FileInputStream("src/main/resources/it/polimi/ingsw/client/view/misc/firstplayertoken.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // INIT LIVING_ROOM_BOARD

        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                SpaceView space = gameView.getLivingRoomBoard().getSpace(new Position(r,c));
                if (!space.isFree() && space.getType() == SpaceType.PLAYABLE){
                    try {
                        ImageView imageView = new ImageView(new Image(new FileInputStream(tilePath(space.getTile()))));
                        imageView.setFitWidth((board.getPrefWidth() - ((board.getColumnCount()-1)*board.getHgap()))/board.getColumnCount()-10);
                        imageView.setFitHeight((board.getPrefHeight() - ((board.getRowCount()-1)*board.getVgap()))/board.getRowCount()-10);
                        imageView.setOnMouseClicked(this::boardTileClicked);
                        board.add(imageView, c, r);
                    } catch (FileNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                }
                else {
                    board.add(new ImageView(), c, r);
                }
            }
        }

        // INIT TILE_PACK
        initTilePack();
        updateTilePack(gameView.getTilePack());
        // INIT BOOKSHELF
        initBookshelf(bookshelf);
        initBookshelf(otherBookshelf);
        // INIT SCORING TOKENS
        Stack<ScoringToken> stack = gameView.getLivingRoomBoard().getCommonGoalCards().get(0).getStack();
        System.out.println(stack);
        stack.forEach(System.out::println);
        String resource = "src/main/resources/it/polimi/ingsw/client/view/scoring tokens/scoring_";
        String initToken = resource.concat(String.valueOf(stack.pop().getValue()) + ".jpg");
        try{
            Image image = new Image(new FileInputStream(initToken));
            tokenCommonCard1.setImage(image);
            tokenCommonCard2.setImage(image);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        // updateBookshelf(gameView.getPlayer(getPlayers().get(personalGameIndex)).getBookshelf(), bookshelf);
        // updateBookshelf(gameView.getPlayer(getPlayers().get(watchedPlayer)).getBookshelf(), otherBookshelf);
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

        //init personal Card
        int num = gameView.getSubscribers().stream().filter(x-> x.getName().equals(nickname)).toList().get(0).getPersonalGoalCard().getPath();
        try {
            personalCard.setImage(new Image(new FileInputStream("src/main/resources/it/polimi/ingsw/client/view/personal goal cards/Personal_Goals" + num + ".png")));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        //init common goal card
        try {
            commonGoalCard1.setImage(new Image(new FileInputStream(getCommonGoalCardPic(gameView.getLivingRoomBoard().getCommonGoalCards().get(0)))));
            commonGoalCard2.setImage(new Image(new FileInputStream(getCommonGoalCardPic(gameView.getLivingRoomBoard().getCommonGoalCards().get(1)))));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void initCommonGoalCards(){

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
            imageView.setOnMouseClicked(null);
        }
    }
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

    public static void updateBookshelf(BookshelfView bookshelf, GridPane bookshelfGrid) {
        initBookshelf(bookshelfGrid);
        for(int r=0;r<bookshelfGrid.getRowCount(); r++){
            for (int c=0; c<bookshelfGrid.getColumnCount(); c++){

                if (bookshelf.getGrid()[r][c] != null){
                    try {
                        Image tile = new Image(new FileInputStream(tilePath(bookshelf.getGrid()[r][c])));
                        ImageView bookshelfImage = (ImageView) bookshelfGrid.getChildren().get(r*5 + c);
                        bookshelfImage.setImage(tile);
                    } catch (IOException e){
                        System.err.println(e.getMessage());
                    }
                } else {
                    ImageView bookshelfImage = (ImageView) bookshelfGrid.getChildren().get(r*5 + c);
                    bookshelfImage.setImage(null);
                }
            }
        }
    }

    public void updateTilePack(TilePackView tilePackView)  {
        initTilePack();
        for (int i=0; i<3; i++){
            ImageView imageView = (ImageView) tilePack.getChildren().get(i);
            if (i<tilePackView.getTiles().size()){
                try {
                    imageView.setImage(new Image(new FileInputStream(tilePath(tilePackView.getTiles().get(i)))));
                } catch (IOException e){
                    System.err.println(e.getMessage());
                }
                imageView.setOnMouseClicked(this::packTileClicked);
            } else {
                imageView.setImage(null);
            }
        }
    }

    public void updateLivingRoomBoard(LivingRoomBoardView livingRoomBoardView) {
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                SpaceView space = livingRoomBoardView.getSpace(new Position(r,c));
                ImageView imageView = (ImageView) board.getChildren().get(r*9 + c);
                if (!space.isFree() && space.getType().equals(SpaceType.PLAYABLE)){
                    try {
                        imageView.setImage(new Image(new FileInputStream(tilePath(space.getTile()))));
                    } catch (FileNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                }
                else {
                    imageView.setImage(null);
                }
            }
        }
    }

    private void packTileClicked(MouseEvent event) {
        if (columnSelected){
            ImageView sourceImageView = (ImageView) event.getSource();
            for (int i=0; i<tilePack.getChildren().size(); i++){
                if (tilePack.getChildren().get(i).equals(sourceImageView)){
                    gui.insertTileInColumn(i);
                }
            }
        }
    }

    public void boardTileClicked(MouseEvent event){
            ImageView sourceImageView = (ImageView) event.getSource();
            if (sourceImageView.getImage() != null){
                for (int i=0; i<board.getChildren().size(); i++){
                    if (board.getChildren().get(i).equals(sourceImageView)){
                        int r = i/9;
                        int c = i%9;
                        System.out.println(nickname + "board clicked in " + r + " " + c);
                        Platform.runLater(()->this.gui.chosenPosition(r,c));
                        break;
                    }
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
            Platform.runLater(()-> {
                playerBookshelf.setText(players.get(watchedPlayer));
                updateBookshelf(gameView.getPlayer(players.get(watchedPlayer)).getBookshelf(), otherBookshelf);
            });

        }
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

    public void insertInColumn(MouseEvent event){
        Button column = (Button) event.getSource();
        int i = 0;
        for (Node node : columnButtons.getChildren()){
            if (node.equals(column)){
                columnSelected = true;
                int j = i;
                Platform.runLater(()->this.gui.stopPickingTiles(j));
            }
            else {
                node.setDisable(true);
            }
            i+=1;
        }
    }

    public void resetColumnChoice() {
        for (Node node : columnButtons.getChildren()){
            Button button = (Button) node;
            button.setDisable(false);
            button.getStyleClass().setAll("column");
        }
        columnSelected = false;
    }

    public void disableColumnChoice(){
        for (Node node : columnButtons.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }
        columnSelected = false;
    }

    public void updateTokens(GameView gameView, int commonGoalCardIndex) {
        Stack<ScoringToken> oldStack = gameView.getLivingRoomBoard().getCommonGoalCards().get(commonGoalCardIndex).getStack();
        String path = "src/main/resources/it/polimi/ingsw/client/view/scoring tokens/scoring_";
        String poppedTokenPath = path.concat(String.valueOf(oldStack.pop().getValue()) + ".jpg");
        Image poppedToken = null;
        Image nextToken = null;
        try {
            poppedToken = new Image(new FileInputStream(poppedTokenPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (oldStack.size()>0){
            String nextTokenPath = path.concat(String.valueOf(oldStack.pop().getValue()) + ".jpg");
            try {
                nextToken = new Image(new FileInputStream(nextTokenPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // todo: make a group
        if (commonGoalCardIndex == 0) {
            tokenCommonCard1.setImage(nextToken);
            if (gameView.getCurrentPlayer().getName().equals(nickname)){
                 tokenCommonGoalCard1.setImage(poppedToken);
            }
        } else if (commonGoalCardIndex == 1) {
            tokenCommonCard2.setImage(nextToken);
            if (gameView.getCurrentPlayer().getName().equals(nickname)){
                    tokenCommonGoalCard2.setImage(poppedToken);
            }
        }
    }
}


