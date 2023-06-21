package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.ItemTile;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class BagView implements Serializable {

    private final List<ItemTile> bag;
    @Serial
    private static final long serialVersionUID = 1L;

    public BagView (Bag bag) {
        this.bag = bag.getBag();
    }

    public int getSize () {
        return bag.size();
    }

    public List<ItemTile> getBag () {
        return this.bag;
    }
}
