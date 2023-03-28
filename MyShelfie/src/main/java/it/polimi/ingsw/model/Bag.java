package it.polimi.ingsw.model;

import java.util.List;


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
        return null;
    }
    public List<ItemTile> drawTile(int amount){
        return null;
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
