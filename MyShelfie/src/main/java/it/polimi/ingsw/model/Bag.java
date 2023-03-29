package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class Bag: it contains all the item tiles that are not placed on the board or on the Players' shelves
 */


/**
 * Class contructor
 */
public class Bag {
    private final int MAX_SIZE = 132;
    private final int MAX_SIZE_PER_TYPE = 22;

    private int size = 0;
    private List<ItemTile> bag;

    public Bag(){
        for(TileType type: TileType.values()){
            for(int i=0; i<MAX_SIZE_PER_TYPE; i++){
                bag.add(new ItemTile(type));
                this.size++;
            }
        }
    }

    /**
     * Used in order to draw from the bag a random item tile, that will be later placed on the board
      * @return the item that was drawn from the bag
     */
    public ItemTile drawTile(){
        int randNumber = ThreadLocalRandom.current().nextInt(0, bag.size() + 1);
        ItemTile toBeDrawn = bag.get(randNumber);
        bag.remove(randNumber);
        return toBeDrawn;
    }

    /**
     * Used in order to take from the bag a specific amount of random item tiles, that will later be
     * placed on the board
     * @param amount number of tiles to be drawn from the bag
     * @return the item tiles that were picked from the bag
     */
    public List<ItemTile> drawTile(int amount){
        List<ItemTile> toBeDrawn = new ArrayList();
        for(int i=0; i<amount; i++){
            int randNumber = ThreadLocalRandom.current().nextInt(0, bag.size() + 1);
            ItemTile elem = bag.get(randNumber);
            toBeDrawn.add(elem);
            bag.remove(randNumber);
        }
        return toBeDrawn;
    }

    /**
     * Inserts in the bag a list of item tiles
     * @param leftovers list of item tiles to be inserted in the bag
     */
    public void insertTiles(List<ItemTile> leftovers){
        for(ItemTile item: leftovers){
            bag.add(item);
        }
    }

    /**
     * 
     * @return how many item tiles are left in the bag
     */
    public int getSize(){
        return size;
    }
}
