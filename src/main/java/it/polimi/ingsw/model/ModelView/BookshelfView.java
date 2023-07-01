package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.NumberOfPlayers;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

/**
 * <h1>Class BookshelfView</h1>
 * This class is the immutable version of class Bookshelf
 *
 * @author Francesca Santambrogio, Carlo Terzuoli
 * @version 1.0
 * @since 5/06/2023
 */
public class BookshelfView implements Serializable {

    /**
     * Matrix of the bookshelf's ItemTile
     */
    private final ItemTile[][] bookshelf;
    /**
     * Bookshelf's width
     */
    private final int MAX_WIDTH;
    /**
     * Bookshelf's height
     */
    private final int MAX_HEIGHT;
    /**
     * UID version
     */
    @Serial
    private static final long  serialVersionUID = 1L;
    /**
     * Bookshelf's player name
     */
    private final String playerName;

    /**
     * Constructor for class BookshelfView
     * @param bookshelf Bookshelf object to create immutable version
     * @param playerName name of the player owning the bookshelf
     */
    public BookshelfView (Bookshelf bookshelf, String playerName) {
        this.bookshelf = bookshelf.getGrid();
        this.MAX_WIDTH= bookshelf.getMaxWidth();
        this.MAX_HEIGHT = bookshelf.getMaxHeight();
        this.playerName = playerName;
    }
    /**
     * Getter method to return the bookshelf's grid
     * @return bookshelf's grid
     */
    public ItemTile[][] getGrid () {
        return bookshelf;
    }

    /**
     * Getter method for bookshelf's width
     * @return bookshelf's width
     */
    public int getMaxWidth () { return this.MAX_WIDTH;}
    /**
     * Getter method for bookshelf's height
     * @return bookshelf's height
     */
    public int getMaxHeight () { return this.MAX_HEIGHT;}
    /**
     * Getter method for bookshelf's player name
     * @return bookshelf's player name
     */
    public String getPlayerName() { return playerName;}

    /**
     * Override of method to string to print the bookshelf
     * @return string representation of bookshelf
     */
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

    /**
     * Method useful for GameView string representation. Each line of the bookshelf is indexed by an integer
     * to print the bookshelf line by line where is needed.
     * @return map HashMap indexing line's number with string's line
     */
    public HashMap<Integer, String> toDict(){
        int WIDTH = 24;
        HashMap<Integer, String> map = new HashMap<>();
        // title playerName's bookshelf
        String title;
        title = getPlayerName().toUpperCase().concat("'s bookshelf");
        title = titleGenerator(WIDTH, title);
        map.put(0, title);
        // columns' indexes
        String number = "";
        for(int i=0; i<getMaxWidth(); i++){
            number = number.concat("   " + i);
        }
        map.put(1, number.concat("    "));
        // rows generation
        String rows = "";
        for(int i=0; i<getMaxHeight();i++){
            rows = rows.concat(getMaxHeight() - 1 - i + " ");
            for(int j=0; j<getMaxWidth();j++){
                rows = rows.concat(getGrid()[i][j] != null ? getGrid()[i][j].toString() + " " : "    ");
            }
            map.put(i+2, rows.concat("  "));
            rows = "";
        }
        return map;
    }

    /**
     * It fills the given string with spaces to reach the desired width
     * otherwise the last three characters are replaced by a dot.
     * @param width length of the returned string
     * @param string actual string
     * @return
     */
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
}
