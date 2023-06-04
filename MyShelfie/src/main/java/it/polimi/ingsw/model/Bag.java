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
     * It initializes the {@link Bag#bag} containing all the {@link ItemTile}s at the beginning of the game
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
     * This method is used to draw a random {@link ItemTile} from the {@link Bag#bag}. It will be later placed on the {@link LivingRoomBoard}
     * @return the {@link ItemTile} that was drawn from the {@link Bag#bag}
     * @exception IndexOutOfBoundsException The exception is thrown if the method is called when there are no {@link ItemTile} left inside the {@link Bag#bag}
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
     * This method is used to draw at random a specific number of {@link ItemTile}s from the {@link Bag#bag}.
     * @param amount number of {@link ItemTile}s to be drawn from the {@link Bag#bag}
     * @return a list containing all the {@link ItemTile}s that were picked from the {@link Bag#bag}
     * @exception IllegalArgumentException The exception is thrown if the amount of items to be drawn exceeds the {@link Bag#size} of the {@link Bag#bag}
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
     * This method inserts in the {@link Bag#bag} a list of {@link ItemTile}s
     * @param leftovers a list of {@link ItemTile}s to be inserted in the {@link Bag#bag}
     * @exception IllegalArgumentException The exception is thrown if the number of items to insert exceeds the available {@link Bag#size} of the {@link Bag#bag}
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
     * Getter method
     * @return the {@link Bag#size} of the {@link Bag#bag}
     */
    public int getSize(){
        return size;
    }

    /**
     * Getter method
     * @return the current {@link Bag#bag}
     */
    public List<ItemTile> getBag () {
        return this.bag;
    }
}
