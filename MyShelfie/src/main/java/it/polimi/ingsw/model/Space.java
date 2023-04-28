package it.polimi.ingsw.model;

/**
 * Class Space <br>
 * it consists of all the single spaces that make up the LivingRoomBoard
 */


public class Space {
    private boolean free;
    private SpaceType type;
    private Position position;
    private ItemTile tile;

    /**
     * Class contructor
     * @param type type of the space
     * @param position position of the space on the board
     */
    public Space(SpaceType type, Position position){
        this.type = type;
        this.position = position;
        this.tile = null;
        this.free = this.getType() != SpaceType.FORBIDDEN;
    }

    /**
     *
     * @return the type of the space
     */
    public SpaceType getType(){
        return type;
    }

    /**
     *
     * @return whether the space is free or occupied
     */
    public boolean isFree(){
        return free;
    }

    /**
     *
     * @return the position the space is in
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Places an ItemTile on the space
     * @param tile the tile to be placed on the space
     * @throws IllegalAccessError if the space was not free
     */
    public void setTile(ItemTile tile) throws IllegalAccessError{
        if (getType().equals(SpaceType.DEFAULT) && this.isFree()) {
            this.tile = tile;
            this.free = false;
        } else {
            throw new IllegalAccessError();
        }
    }

    /**
     *
     * @return the tile that is placed on the space
     */
    public ItemTile getTile() throws IllegalAccessError{
        if(this.getType().equals(SpaceType.FORBIDDEN) || this.isFree()){
            throw new IllegalAccessError("The selected space is not playable");
        } else {
            return this.tile;
        }
    }

    /**
     * takes the ItemTile from the space.
     * @return The ItemTile that was taken
     * @throws IllegalAccessError if the space was free or not playable
     */
    public ItemTile drawTile() throws IllegalAccessError{
        if (!isFree() && this.getType().equals(SpaceType.DEFAULT)){
            ItemTile tempTile = this.tile;
            this.tile = null;
            this.free = true;
            return tempTile;
        }
        else {
            throw new IllegalAccessError();
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
