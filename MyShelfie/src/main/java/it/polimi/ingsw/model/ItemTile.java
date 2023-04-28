package it.polimi.ingsw.model;

/**
 * <h1>Class ItemTile</h1>
 * The class ItemTile represents the pivotal objects of the game occupying spaces in the living room board
 * and in the bookshelves of the players giving them scores according to their disposition
 * */
public class ItemTile {
    private final TileType type;

    /**
     * Class constructor
     * @param type the identifying item tile type
     */
    public ItemTile(TileType type){
        this.type = type;
    }

    /**
     * This getter method gets the type of the item tile
     * @return TileType It returns the type of the item tile
     */
    public TileType getType() {
        return type;
    }
}
