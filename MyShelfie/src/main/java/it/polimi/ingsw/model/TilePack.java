package it.polimi.ingsw.model;

import java.util.*;

/**
 * <h1>Class TilePack</h1>
 * The class TilePack is used to keep track of the item tiles drawn by a player from the living room board
 * waiting to be inserted in the bookshelf
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 3/28/2023
 */

public class TilePack {
    private final List<ItemTile> tiles;

    private static final int MAX_LENGTH = 3;

    /**
     * Class constructor:
     * this method initializes the {@link TilePack#tiles} list representing the {@link TilePack}
     */
    public TilePack () {
        this.tiles = new ArrayList<>();
    }

    /**
     * This method is used to insert an item tile in the pack
     * @param tile the specified item tile to insert
     */
    public void insertTile(ItemTile tile) throws IndexOutOfBoundsException {
        if(this.getSize() < getMaxLength()) {
            this.tiles.add(tile);
        }
        else {
            throw new IndexOutOfBoundsException("Inserting too many tiles in the tile pack");
        }
    }

    /**
     * Getter method
     * @return the {@link TilePack#tiles}, namely list of tiles representing the tile pack
     */
    public List<ItemTile> getTiles() {
        return this.tiles;
    }

    /**
     * Getter method
     * @return the size of the list of item tiles representing the tile pack
     */
    public int getSize(){
        return getTiles().size();
    }

    /**
     * Getter method
     * @return the {@link TilePack#MAX_LENGTH}
     */
    public static int getMaxLength() {
        return MAX_LENGTH;
    }
}
