package it.polimi.ingsw.model;

/**
 * <h1>Class Space</h1>
 * The class Space represents the unit of which the living room board is composed
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/21/2023
 */


public class Space {
    private boolean free;
    private final SpaceType type;
    private final Position position;
    private ItemTile tile;

    /**
     * Class contructor
     * @param type the type of the space
     * @param position the position of the space on the board
     */
    public Space(SpaceType type, Position position){
        this.type = type;
        this.position = position;
        this.tile = null;
        this.free = this.getType() != SpaceType.FORBIDDEN;
    }

    /**
     * This getter method gets the type of the space
     * @return SpaceType It returns the type of the space
     */
    public SpaceType getType(){
        return type;
    }

    /**
     * This getter method gets the attribute free
     * @return boolean It returns true if the space is free, false otherwise
     */
    public boolean isFree(){
        return free;
    }

    /**
     * This getter method gets the position of the space
     * @return Position It returns the position of the space
     */
    public Position getPosition(){
        return position;
    }

    /**
     * This setter method places an ItemTile on the space
     * @param tile the tile to be placed on the space
     * @throws IllegalAccessError The exception is thrown if the space is not free
     */
    public void setTile(ItemTile tile) throws IllegalAccessError{
        if (getType().equals(SpaceType.DEFAULT) && this.isFree()) {
            this.tile = tile;
            this.free = false;
        } else {
            throw new IllegalAccessError("The space is not available, please choose another space to put the tile");
        }
    }

    /**
     * This getter method gets the tile of the space
     * @return ItemTile It returns the tile present on the space
     * @exception IllegalAccessError The exception is thrown if the selected space is free or forbidden, namely not actually part of the living room board
     */
    public ItemTile getTile() throws IllegalAccessError{
        if(this.getType().equals(SpaceType.FORBIDDEN) || this.isFree()){
            throw new IllegalAccessError("The selected space is not playable or not free");
        } else {
            return this.tile;
        }
    }

    /**
     * This method removes the ItemTile from the related space.
     * @return ItemTile It returns the item tile upon the space
     * @throws IllegalAccessError The exception is thrown if the space is free or not playable
     */
    public ItemTile drawTile() throws IllegalAccessError{
        if (!isFree() && this.getType().equals(SpaceType.DEFAULT)){
            ItemTile tempTile = this.tile;
            this.tile = null;
            this.free = true;
            return tempTile;
        }
        else {
            throw new IllegalAccessError("The selected space is not playable or not free");
        }

    }

    @Override
    public String toString(){
        if (!this.isFree() && this.getType() == SpaceType.DEFAULT){
            return this.getTile().getType().colorBackground + " " + this.getTile().getType().abbreviation + " " + "\033[0m";
        } else if (this.getType() == SpaceType.FORBIDDEN) {
            return " X ";
        } else {
            return "   ";
        }
    }

}
