package it.polimi.ingsw.model;

/**
 * The Tile<br>
 * This is one of the main classes of the game. Its placement in the LivingRoomBoard and in the Bookshelf determines
 * points and game strategies.
 * */
public class ItemTile {
    private final TileType type;

    /**
     * Constructor of class ItemTile
     * @param type the type of ItemTile
     */
    public ItemTile(TileType type){
        this.type = type;
    }

    /**
     * @return the type of ItemTile
     */
    public TileType getType() {
        return type;
    }
}
