package it.polimi.ingsw.client.view.FXML;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import it.polimi.ingsw.model.utils.TileType;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LivingBoardController {
    @FXML
    GridPane board;



    public void initialize() throws FileNotFoundException {
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

        LivingRoomBoard livingBoard = new LivingRoomBoard(NumberOfPlayers.TWO_PLAYERS);
        System.out.println(new LivingRoomBoardView(livingBoard).toString());
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                Space space = livingBoard.getSpace(new Position(r,c));
                if (space.getType() == SpaceType.PLAYABLE){
                    String randImage = images.get(space.getTile().getType())[new Random().nextInt(0,2)];
                    ImageView imageView = new ImageView(new Image(new FileInputStream(path + randImage)));
                    imageView.setFitHeight(36);
                    imageView.setFitWidth(36);
                    imageView.setOnMouseClicked(this::onMouseClicked);
                    board.add(imageView, c, r);
                }
            }
        }
    }

    public void onMouseClicked(MouseEvent event){
        System.out.println("carta cliccata");
    }



}
