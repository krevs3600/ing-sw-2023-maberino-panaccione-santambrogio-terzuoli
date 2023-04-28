package it.polimi.ingsw.model;

import java.util.*;

/**
 * <h1>Class TilePack</h1>
 * The class TilePack is used to keep track of the item tiles selected by a player from the living room board
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
     * Class constructor
     */
    public TilePack () {
        this.tiles = new ArrayList<ItemTile>();
    }

    /**
     * This method is used to insert an item tile in the pack
     * @param tile the specified item tile to insert
     */
    public void insertTile(ItemTile tile) {
        this.tiles.add(tile);
    }

    /**
     * This method is used to remove all the item tiles from the pack
     */
    public void removeAllTiles() {
        for (int i = 0; i < MAX_LENGTH; i++) {
            this.removeLast();
        }
    }

    /**
     * This method is used to remove the last item tile from the pack
     */
    public void removeLast() {
        this.tiles.remove(this.getTiles().size()-1);
    }

    /**
     * This method is used to switch the position within the pack of two item tiles
     * @param position1 the specified first position to be switched with the second one
     * @param position2 the specified second position to be switched with the first one
     */
    //TODO: exception
    public void switchTiles (int position1, int position2) {
        ItemTile it = new ItemTile(this.getTiles().get(position1).getType());
        this.tiles.set(position1, this.tiles.get(position2));
        this.tiles.set(position2, it);
    }

    /**
     * This getter method gets the item tiles present in the tile pack
     * @return List<ItemTile> It returns the list of tiles representing the tile pack
     */
    public List<ItemTile> getTiles() {
        return this.tiles;
    }

}
