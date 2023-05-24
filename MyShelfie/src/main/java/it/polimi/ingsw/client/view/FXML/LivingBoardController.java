package it.polimi.ingsw.client.view.FXML;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.CommonGoalCard.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.ModelView.SpaceView;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import it.polimi.ingsw.model.utils.TileType;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

    private boolean columnSelected = false;
    private int column;
    private String nickname = "carlo";
    private GUI gui;



    public void initialize(GameView gameView, String nickname) throws FileNotFoundException {
        // loading itemTiles paths and creating support structures
        String path = "src/main/resources/it/polimi/ingsw/client/view/itemtiles/";
        Map<TileType, String[]> images = new HashMap<>();
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
        images.put(TileType.TROPHY, trophey);


        // INIT LIVING_ROOM_BOARD
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                SpaceView space = gameView.getLivingRoomBoard().getSpace(new Position(r,c));
                if (space.getType() == SpaceType.PLAYABLE){
                    String randImage = images.get(space.getTile().getType())[new Random().nextInt(0,2)];
                    ImageView imageView = new ImageView(new Image(new FileInputStream(path + randImage)));
                    imageView.setFitWidth((board.getPrefWidth() - ((board.getColumnCount()-1)*board.getHgap()))/board.getColumnCount()-10);
                    imageView.setFitHeight((board.getPrefHeight() - ((board.getRowCount()-1)*board.getVgap()))/board.getRowCount()-10);
                    imageView.setOnMouseClicked(this::boardTileClicked);
                    board.add(imageView, c, r);
                }
            }
        }



        // INIT TILEPACK
        for (Node node : tilePack.getChildren()){
            ImageView imageView = (ImageView) node;
            ((ImageView) node).setImage(null);
            imageView.setOnMouseClicked(this::packTileClicked);
        }


        // INIT COLUMN BUTTONS
        for (Node node : columnOptions.getChildren()){
            RadioButton radioButton = (RadioButton) node;
            radioButton.setOnMouseClicked(this::radioButtonPressed);
        }

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

        // INIT BUTTONS
        // first make buttons invisible, then enable the right ones
        for(Node node : playerBookshelves.getChildren()){
            Button button = (Button) node;
            button.setVisible(false);
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
        personalCard.setImage(new Image(new FileInputStream("src/main/resources/it/polimi/ingsw/client/view/personal goal cards/Personal_Goals" + String.valueOf(num) + ".png")));

        //init common goal card
        commonGoalCard1.setImage(new Image(new FileInputStream(getCommonGoalCardPic(commonGoalCard1))));
        commonGoalCard2.setImage(new Image(new FileInputStream(getCommonGoalCardPic(commonGoalCard2))));
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
        System.out.println("carta cliccata");
        // tilePack needs to be updated
        ImageView sourceImageView = (ImageView) event.getSource();
        for (Node node : tilePack.getChildren()){
            ImageView imageView = (ImageView) node;
            if (imageView.getImage() == null){
                ((ImageView) node).setImage(sourceImageView.getImage());
                sourceImageView.setImage(null);
            }
        }
    }

    public void bookshelfButtonPressed(MouseEvent event) {
        Stage stage = new Stage();
        stage.show();
    }


    public void scoringTokenClicked(MouseEvent event){
        First_8.setVisible(false);
    }


    public void setGui(GUI gui){
        this.gui = gui;
    }

    public static String getCommonGoalCardPic(Object o){
        String path = "src/main/resources/it/polimi/ingsw/client/view/common goal cards/";
        if (o instanceof TwoSquaresCommonGoalCard){
            return path + "1.jpg";
        }
        else if (o instanceof TwoColumnsCommonGoalCard){
            return path + "2.jpg";
        }
        else if (o instanceof FourGroupsCommonGoalCard){
            return path + "3.jpg";
        }
        else if (o instanceof SixGroupsCommonGoalCard){
            return path + "4.jpg";
        }
        else if (o instanceof ThreeColumnsCommonGoalCard){
            return path + "5.jpg";
        }
        else if (o instanceof TwoLinesCommonGoalCard){
            return path + "6.jpg";
        }
        else if (o instanceof FourLinesCommonGoalCard){
            return path + "7.jpg";
        }
        else if (o instanceof CornersCommonGoalCard){
            return path + "8.jpg";
        }
        else if (o instanceof EightTilesCommonGoalCard){
            return path + "9.jpg";
        }
        else if (o instanceof CrossCommonGoalCard){
            return path + "10.jpg";
        }
        else if (o instanceof DiagonalCommonGoalCard){
            return path + "11.jpg";
        }
        else if (o instanceof IncreasingColumnsCommonGoalCard){
            return path + "12.jpg";
        }
        else {
            return "back.jpg";
        }
    }
}

