package it.polimi.ingsw.client.view.FXML;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import it.polimi.ingsw.model.utils.TileType;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LivingBoardController {
    @FXML
    GridPane board;

    @FXML
    Group tilePack;

    @FXML
    Group columnOptions;

    @FXML
    GridPane bookshelf;

    @FXML
    ImageView First_2;

    @FXML
    ImageView First_4;

    @FXML
    ImageView First_6;

    @FXML
    ImageView First_8;

    @FXML
    ImageView Second_2;
    @FXML
    ImageView Second_4;

    @FXML
    ImageView Second_6;

    @FXML
    ImageView Second_8;





    private boolean columnSelected = false;
    private int column;


    public void initialize() throws FileNotFoundException {
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

        // init livingBoard
        LivingRoomBoard livingBoard = new LivingRoomBoard(NumberOfPlayers.FOUR_PLAYERS);
        System.out.println(new LivingRoomBoardView(livingBoard).toString());
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                Space space = livingBoard.getSpace(new Position(r,c));
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
        // init tilePack
        for (Node node : tilePack.getChildren()){
            ImageView imageView = (ImageView) node;
            ((ImageView) node).setImage(null);
            imageView.setOnMouseClicked(this::packTileClicked);
        }


        // init buttons
        for (Node node : columnOptions.getChildren()){
            RadioButton radioButton = (RadioButton) node;
            radioButton.setOnMouseClicked(this::buttonPressed);
        }

        // init bookshelf
        for (int r=0; r<6;r++){
            for (int c=0; c<5; c++){
                ImageView imageView = new ImageView();
                imageView.setFitWidth((bookshelf.getPrefWidth() - (int)bookshelf.getPadding().getLeft() -(int)bookshelf.getPadding().getRight() - ((bookshelf.getColumnCount()-1)*bookshelf.getHgap()))/bookshelf.getColumnCount());
                imageView.setFitHeight((bookshelf.getPrefHeight() - (int)bookshelf.getPadding().getTop() - (int)bookshelf.getPadding().getBottom() - ((bookshelf.getRowCount()-1)*bookshelf.getVgap()))/bookshelf.getRowCount());
                imageView.setImage(null);
                bookshelf.add(imageView, c,r);
            }
        }
    }

    private void buttonPressed(MouseEvent event) {
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


    public void scoringTokenClicked(MouseEvent event){
        First_8.setVisible(false);
    }





}
