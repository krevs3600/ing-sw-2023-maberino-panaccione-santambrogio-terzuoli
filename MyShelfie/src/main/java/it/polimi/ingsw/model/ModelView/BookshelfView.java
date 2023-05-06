package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public class BookshelfView extends Observable implements Observer {

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

}
