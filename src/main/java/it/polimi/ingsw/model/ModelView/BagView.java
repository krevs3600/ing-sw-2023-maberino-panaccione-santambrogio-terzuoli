package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.ItemTile;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
/**
 * <h1>Class BagView</h1>
 * This class is the immutable version of class Bag
 *
 * @author Francesca Santambrogio
 * @version 1.0
 * @since 5/07/2023
 */
public class BagView implements Serializable {

    /**
     * List of bag's ItemTile
     */
    private final List<ItemTile> bag;
    /**
     * UID version
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Constructor for class BagView
     * @param bag Bag object to create immutable version
     */
    public BagView (Bag bag) {
        this.bag = bag.getBag();
    }

    /**
     * Getter method for the bag's size
     * @return bag's size
     */
    public int getSize () {
        return bag.size();
    }
    /**
     * Getter method for the bag
     * @return bag's size
     */
    public List<ItemTile> getBag () {
        return this.bag;
    }
}
