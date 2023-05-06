package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class BagView extends Observable implements Observer {

    private final Bag bag;

    public BagView (Bag bag) {
        this.bag = bag;
    }

    public int getSize () {
        return bag.getSize();
    }

    public List<ItemTile> getBag () {
        return bag.getBag();
    }
}
