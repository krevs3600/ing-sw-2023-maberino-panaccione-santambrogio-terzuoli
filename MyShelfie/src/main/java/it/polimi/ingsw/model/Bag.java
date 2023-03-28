package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


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

    // to be done
    public ItemTile drawTile(){
        int randNumber = ThreadLocalRandom.current().nextInt(0, bag.size() + 1);
        it.polimi.ingsw.model.ItemTile toBeDrawn = bag.get(randNumber);
        bag.remove(randNumber);
        return toBeDrawn;
    }
    public List<ItemTile> drawTile(int amount){
        List<it.polimi.ingsw.model.ItemTile> toBeDrawn = new ArrayList();
        for(int i=0; i<amount; i++){
            int randNumber = ThreadLocalRandom.current().nextInt(0, bag.size() + 1);
            it.polimi.ingsw.model.ItemTile elem = bag.get(randNumber);
            toBeDrawn = bag.add(elem);
            bag.remove(randNumber);
        }
        return toBeDrawn;
    }
    public void insertTiles(ItemTile[] leftovers){
        for(ItemTile item: leftovers){
            bag.add(item);
        }
    }

    public int getSize(){
        return size;
    }
}
