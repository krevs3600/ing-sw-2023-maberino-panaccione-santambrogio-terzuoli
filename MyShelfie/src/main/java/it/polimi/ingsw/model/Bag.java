package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.TileType;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h1>Class Bag</h1>
 * The class Bag contains all the item tiles that are not placed on the board or on the Players' shelves
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/28/2023
 */
public class Bag {
    private static final int MAX_SIZE = 132;
    private static final int MAX_SIZE_PER_TYPE = 22;

    //private Bag bag = new Bag();
    private int size = 0;
    private final List<ItemTile> bag = new ArrayList<ItemTile>();

    /**
     * Class constructor
     */
    public Bag(){
        for(TileType type: TileType.values()){
            for(int i=0; i<MAX_SIZE_PER_TYPE; i++){
                bag.add(new ItemTile(type));
                this.size++;
            }
        }
    }

    /**
     * This method is used to draw from the bag a random item tile that will be later placed on the board
     * @return ItemTile It returns the item that was drawn from the bag
     * @exception IndexOutOfBoundsException The exception is thrown if the method is called when there are no tiles inside the bag
     */
    public ItemTile drawTile() throws IndexOutOfBoundsException{
        if(this.size > 0){
            int randNumber = ThreadLocalRandom.current().nextInt(0, bag.size());
            ItemTile toBeDrawn = bag.get(randNumber);
            bag.remove(randNumber);
            size--;
            return toBeDrawn;
        } else {
            throw new IndexOutOfBoundsException("The bag is empty ");
        }
    }

    /**
     * This method is used to take from the bag a specific amount of random item tiles, that will later be
     * placed on the board
     * @param amount number of tiles to be drawn from the bag
     * @return List<ItemTile> It returns the item tiles that were picked from the bag
     * @exception IllegalArgumentException The exception is thrown if the amount of items to be drawn exceeds the size of the bag
     */
    public List<ItemTile> drawTile(int amount) throws IllegalArgumentException{
        if (amount >= 0 && amount <= this.getSize()) {
            List<ItemTile> toBeDrawn = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                int randNumber = ThreadLocalRandom.current().nextInt(0, bag.size());
                ItemTile elem = bag.get(randNumber);
                toBeDrawn.add(elem);
                bag.remove(randNumber);
                size--;

            }
            return toBeDrawn;
        } else {
            throw new IllegalArgumentException("You're taking too main items. Bag size is: " + this.getSize());
        }
    }

    /**
     * This method inserts in the bag a list of item tiles
     * @param leftovers list of item tiles to be inserted in the bag
     * @exception IllegalArgumentException The exception is thrown if the number of item tiles to insert exceeds the available space of the bag
     */
    public void insertTiles(List<ItemTile> leftovers) throws IllegalArgumentException{
        if (this.getSize() + leftovers.size() < MAX_SIZE) {
            for(ItemTile item: leftovers){
                bag.add(item);
                size++;
            }
        } else {
            throw new IllegalArgumentException("Exceeding bag capacity");
        }

    }

    /**
     * This getter method gets the size of the bag
     * @return int It returns how many item tiles are left in the bag
     */
    public int getSize(){
        return size;
    }

    public List<ItemTile> getBag () {
        return this.bag;
    }
}
