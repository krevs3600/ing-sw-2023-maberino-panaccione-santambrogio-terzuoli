package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.NumberOfPlayers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;


public class BookshelfView implements Serializable {

    private final ItemTile[][] bookshelf;
    private final int MAX_WIDTH;
    private final int MAX_HEIGHT;
    private static final long  serialVersionUID = 1L;

    private final String playerName;

    public BookshelfView (Bookshelf bookshelf, String playerName) {
        this.bookshelf = bookshelf.getGrid();
        this.MAX_WIDTH= bookshelf.getMaxWidth();
        this.MAX_HEIGHT = bookshelf.getMaxHeight();
        this.playerName = playerName;
    }

    //public int getNumberOfTiles () {
    //    return bookshelf.getNumberOfTiles();
    //}

    public ItemTile[][] getGrid () {
        return bookshelf;
    }

    public int getMaxWidth () { return this.MAX_WIDTH;}
    public int getMaxHeight () { return this.MAX_HEIGHT;}
    public String getPlayerName() { return playerName;}

    @Override
    public String toString(){
        String result = "";
        HashMap<Integer, String> bookshelf = toDict();
        for(int i = 0; i< bookshelf.size(); i++){
            result = result.concat(bookshelf.get(i));
            if (i< bookshelf.size()-1){
                result = result.concat("\n");
            }
        }
        return result;
    }

    public HashMap<Integer, String> toDict(){
        int WIDTH = 24;
        HashMap<Integer, String> map = new HashMap<>();
        // title playerName's bookshelf
        String title = "";
        title = getPlayerName().toUpperCase().concat("'s bookshelf");
        title = titleGenerator(WIDTH, title);
        map.put(0, title);
        // columns' indexes
        String number = "";
        for(int i=0; i<getMaxWidth(); i++){
            number = number.concat("   " + String.valueOf(i));
        }
        map.put(1, number.concat("    "));
        // rows generation
        String rows = "";
        for(int i=0; i<getMaxHeight();i++){
            rows = rows.concat(String.valueOf(getMaxHeight()-1-i) + " ");
            for(int j=0; j<getMaxWidth();j++){
                rows = rows.concat(getGrid()[i][j] != null ? getGrid()[i][j].toString() + " " : "    ");
            }
            map.put(i+2, rows.concat("  "));
            rows = "";
        }
        return map;
    }

    private String titleGenerator(int width, String string){
        String result = "";
        if (string.length() <= width){
            result = result.concat(string);
            for(int i = 0; i<width-string.length(); i++) {
                result = result.concat(" ");
            }
        } else {
            char[] strArray = string.toCharArray();
            char[] chopped = new char[width];
            for (int i = 0; i<width; i++){
                if (i<width-3){
                    chopped[i] = strArray[i];
                } else {
                    chopped[i] = '.';
                }
            }
            result = Arrays.toString(chopped);
        }
        return result;
    }

    public static void main(String[] args){
        Game game = new Game(NumberOfPlayers.TWO_PLAYERS, "game");
        game.initLivingRoomBoard();
        game.subscribe(new Player("carlo"));
        game.subscribe(new Player("jack"));
        BookshelfView view = new BookshelfView(game.getSubscribers().get(0).getBookshelf(), game.getSubscribers().get(0).getName());
        System.out.println(view.toString());
    }
}
