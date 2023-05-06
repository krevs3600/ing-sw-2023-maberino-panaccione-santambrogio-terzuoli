package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.TilePack;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

public class TilePackView extends Observable implements Observer {

    private final TilePack tilePack;

    public TilePackView (TilePack tilePack) {
        this.tilePack = tilePack;
    }

    public List<ItemTile> getTiles () {
        return tilePack.getTiles();
    }
}
