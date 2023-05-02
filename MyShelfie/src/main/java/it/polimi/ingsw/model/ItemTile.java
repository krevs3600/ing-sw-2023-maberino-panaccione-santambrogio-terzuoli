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

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString() {
        return getType().colorBackground + " " + getType().abbreviation + " " + "\033[0m";
    }
}
