package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;


public class BookshelfView{

    private final Bookshelf bookshelf;

    public BookshelfView (Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public int getNumberOfTiles () {
        return bookshelf.getNumberOfTiles();
    }

    public ItemTile[][] getGrid () {
        return bookshelf.getGrid();
    }

    public int getMaxWidth () { return bookshelf.getMaxWidth();}
    public int getMaxHeight () { return bookshelf.getMaxHeight();}

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
