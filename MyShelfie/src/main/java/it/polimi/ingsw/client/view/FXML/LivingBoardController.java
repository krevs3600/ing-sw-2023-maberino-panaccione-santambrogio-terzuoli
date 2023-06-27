package it.polimi.ingsw.client.view.FXML;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.ModelView.*;
import it.polimi.ingsw.model.ScoringToken;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * <h1>Class LivingBoardController</h1>
 *  This class represents the controller of the fxml scene with
 *  the game's board
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/25/2023
 */
public class LivingBoardController {
    /**
    * JavaFX GridPane with dimensions 9x9 representing the board
    */
    @FXML
    public GridPane board;

    /**
     * JavaFX Group containing 3 ImageView for the tiles of the tilePack
     */
    @FXML
    public Group tilePack;

    /**
     * JavaFX Group containing 5 buttons for the selection of the bookshelf's column
     */
    @FXML
    public Group columnButtons;

    /**
     * JavaFX GridPane containing 6x5 ImageView for the tiles of the bookshelf
     */
    @FXML
    public GridPane bookshelf;
    /**
     * JavaFX GridPane containing 6x5 ImageView for the tiles of the other player's bookshelf
     */
    @FXML
    public GridPane otherBookshelf;

    /**
     * JavaFX ImageView showing the first available token to be assigned for the completion of the first common goal
     */
    @FXML
    public ImageView tokenCommonCard1;
    /**
     * JavaFX ImageView showing the first available token to be assigned for the completion of the second common goal
     */
    @FXML
    public ImageView tokenCommonCard2;

    /**
     * AnchorPane element
     */
    @FXML
    public AnchorPane playerBookshelves;
    /**
     * JavaFX ImageView showing the personalGoalCard
     */
    @FXML
    public ImageView personalCard;
    /**
     * JavaFX ImageView for visualizing the first commonGoalCard
     */
    @FXML
    public ImageView commonGoalCard1;

    public String descriptioncommonGoalCard1;

    public String descriptioncommonGoalCard2;


    /**
     * JavaFX ImageView for visualizing the second commonGoalCard
     */
    @FXML
    public ImageView commonGoalCard2;
    /**
     * JavaFX ImageView for the first player seat
     */
    @FXML
    public ImageView seatToken;

    /**
     * JavaFX Button for navigating backward between players
     */
    @FXML
    public Button previousPlayer;

    /**
     * JavaFX Button for navigating forward between players
     */
    @FXML
    public Button nextPlayer;
    /**
     * JavaFX Button containing the name of the current player's bookshelf
     */
    @FXML
    public Button playerBookshelf;

    @FXML
    public Label OtherPlayerTurnLabel;
    @FXML
    public ImageView firstCommonGC;
    public ImageView SeconCommonGC;

    @FXML
    public Text Description_1GC;
    @FXML
    public Text Description_2GC;

    @FXML
    public AnchorPane PaneForPopup;
    @FXML
    public Text textPopUp;

    @FXML
    public ImageView CommonGoalAchieved;

    @FXML
    public Text CommonGoalText;


    @FXML
    public Text textForPopUp;

    @FXML
    public TitledPane descriptionFirstCG;
    @FXML
    public TitledPane descriptionSecondCG;
    @FXML
    public Text descriptionCommonGoalCard1_text;

    @FXML
    public Text descriptionCommonGoalCard2_text;

    @FXML
    public Button ExitGame_button;

    @FXML
    public RadioButton YesExit;

    @FXML
    public AnchorPane PaneForExit;

    @FXML
    public RadioButton NoExit;

    @FXML
    public AnchorPane PaneForResilience;

    @FXML
    public Text TextForResilience;
    @FXML
    public Sphere sphereTurn;

    @FXML
    public Text turnLabel;



    /**
     * JavaFX ImageView for assigning the token for the completion of the first common goal
     */
    @FXML
    ImageView tokenCommonGoalCard1;

    /**
     * JavaFX ImageView for assigning the token for the completion of the secondo common goal
     */
    @FXML
    ImageView tokenCommonGoalCard2;


    @FXML
    public AnchorPane anchorPaneForTheCandPGoalCards;

    /**
     * Boolean to keep track of the selection of the bookshelf's column
     */
    private boolean columnSelected = false;
    /**
     * Nickname of the player
     */
    private String nickname;
    /**
     * Reference to the current view/gui
     */
    private GUI gui;
    /**
     * Player index according to the order of the subscribers' list in game
     */
    private int personalGameIndex;

    /**
     * Enemy's index according to the order of the subscribers' list in game
     */
    private int watchedPlayer;


    /**
     * Map to link player's index to its name
     */
    private Map<Integer, String> players = new HashMap<>();
    /**
     * Internal copy of the GameView
     */
    private GameView gameView;

    /**
     * Setter to save a copy of the GameView whenever is needed
     * @param gameView GameView
     */
    public void setGameView(GameView gameView){
        this.gameView = gameView;
    }

    /**
     * Given an ItemTile it return its graphical representation's path
     * @param itemTile ItemTile
     * @return image's path of the ItemTile
     */
    private static String tilePath(ItemTile itemTile){
        String path = "src/main/resources/it/polimi/ingsw/client/view/itemtiles/";
        return path.concat(itemTile.getType().toString()).concat("1.").concat(itemTile.getImageIndex()).concat(".png");
    }

    /**
     * Operations to load and set graphical elements for the livingBoard_scene
     * @param gameView gameView
     * @param nickname player's nickname
     */
    public void initialize(GameView gameView, String nickname) {
        setGameView(gameView);
        this.nickname = nickname;
        personalGameIndex = gameView.getPersonalGameIndex(nickname);
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
            descriptioncommonGoalCard1=gameView.getLivingRoomBoard().getCommonGoalCards().get(0).toString();
            descriptionFirstCG.setText("First common goal card ");
            descriptionCommonGoalCard1_text.setText(descriptioncommonGoalCard1);

            commonGoalCard2.setImage(new Image(new FileInputStream(getCommonGoalCardPic(gameView.getLivingRoomBoard().getCommonGoalCards().get(1)))));
            descriptioncommonGoalCard2=gameView.getLivingRoomBoard().getCommonGoalCards().get(1).toString();

            descriptionSecondCG.setText("Second common goal card");
            descriptionCommonGoalCard2_text.setText(descriptioncommonGoalCard2);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }



    /**
     * Initialize ImageViews of the tilePack in the tilePack group
     */
    private void initTilePack(){
        for (Node node : tilePack.getChildren()){
            ImageView imageView = (ImageView) node;
            imageView.setOnMouseClicked(null);
        }
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

    /**
     * Load the current tilePack's tiles
     * @param tilePackView tilePack
     */
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

    /**
     * Load the current tiles' displacement in the board
     * @param livingRoomBoardView board
     */
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

    /**
     * On mouse event notify through insertInColumn() that the tile with index i has to be inserted in the bookshelf
     * @param event
     */
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

    /**
     * On mouse event notify through chosenPosition() that a tile in position (r,c) has been clicked
     * @param event
     */
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

    /**
     * On mouse event changes the player's bookshelf to observe
     * @param event
     */
    public void changeBookshelf(MouseEvent event) {
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

    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */
    public void setGui(GUI gui){
        this.gui = gui;
    }

    /**
     * Method to get the correct image's path for the card
     * @param card CommonGoalCard whose image needs to be retrieved
     * @return String image's path to the correct representation of the commonGoalCard
     */
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

    /**
     * Set the column where the player wants to insert the tilePack's tiles and send an eventMessage through stopPickingTiles()
     * @param event
     */
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
    /**
     * Reset the selection of the buttons for the column's selection
     */
    public void resetColumnChoice() {
        for (Node node : columnButtons.getChildren()){
            Button button = (Button) node;
            button.setDisable(false);
            button.getStyleClass().setAll("column");
        }
        columnSelected = false;
    }

    /**
     * Makes the button for the column's selection unavailable
     */
    public void disableColumnChoice(){
        for (Node node : columnButtons.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }
        columnSelected = false;
    }

    /**
     * Method used to update ImageViews when a common goal is achieved
     * @param gameView
     * @param commonGoalCardIndex
     */
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

    /**
     * Getter for watchedPlayer
     * @return int Return the index of the player whose bookshelf is being observed
     */
    public int getWatchedPlayer() {
        return watchedPlayer;
    }

    /**
     * Getter for map players
     * @return Map<Integer, String> Returns a map with as key the index of the player and value the name of the player
     */
    public Map<Integer, String> getPlayers() {
        return players;
    }

    public void ExitGame(MouseEvent mouseEvent) {
        PaneForExit.setVisible(true);

    }

    public void keep_playing(MouseEvent mouseEvent) {
        PaneForExit.setVisible(false);
    }

    public void leave_definitively(MouseEvent mouseEvent) {
        //todo: gestire direttamente nella gui, chiudere lo stage ed invia il messaggio a tutti gli altri
        GuiApp.getWindow().close();

    }

    public void setSphereTurnGreen(){
        // Creazione del materiale con il colore desiderato
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.SPRINGGREEN); // Imposta il colore diffuso
        material.setSpecularColor(Color.SPRINGGREEN); // Imposta il colore speculare
        // material.set... // Puoi impostare altre proprietà del materiale se necessario

        // Imposta il materiale sulla sfera
        sphereTurn.setMaterial(material);

    }

    public void setSphereTurnRed(){
        // Creazione del materiale con il colore desiderato
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.ORANGERED); // Imposta il colore diffuso
        material.setSpecularColor(Color.ORANGERED); // Imposta il colore speculare
        // material.set... // Puoi impostare altre proprietà del materiale se necessario

        // Imposta il materiale sulla sfera
        sphereTurn.setMaterial(material);

    }

    public void setTurnLabelRed(){
        turnLabel.setFill(Color.ORANGERED);
    }

    public void setTurnLabelGreen(){
        turnLabel.setFill(Color.SPRINGGREEN);
    }


}


