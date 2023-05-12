package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;

import java.io.Serializable;


public class BookshelfView implements Serializable {

    private final ItemTile[][] bookshelf;
    private final int MAX_WIDTH;
    private final int MAX_HEIGHT;
    private static final long  serialVersionUID = 1L;

    public BookshelfView (Bookshelf bookshelf) {
        this.bookshelf = bookshelf.getGrid();
        this.MAX_WIDTH= bookshelf.getMaxWidth();
        this.MAX_HEIGHT = bookshelf.getMaxHeight();
    }

    //public int getNumberOfTiles () {
    //    return bookshelf.getNumberOfTiles();
    //}

    public ItemTile[][] getGrid () {
        return bookshelf;
    }

    public int getMaxWidth () { return this.MAX_WIDTH;}
    public int getMaxHeight () { return this.MAX_HEIGHT;}

    @Override
    public String toString(){
        String number = "";
        for(int i=0; i<getMaxWidth(); i++){
            number = number.concat("   " + String.valueOf(i));
        }

        String rows = "";
        for(int i=0; i<getMaxHeight();i++){
            rows = rows.concat(String.valueOf(getMaxHeight()-1-i) + " ");
            for(int j=0; j<getMaxWidth();j++){
                rows = rows.concat(getGrid()[i][j] != null ? getGrid()[i][j].toString() + " " : "    ");
            }
            rows = rows.concat("\n");
        }
        return number.concat("\n").concat(rows);
    }
}
