package it.polimi.ingsw.model;

public class Space {
    private boolean free;
    private SpaceType type;
    private Position position;
    private ItemTile tile;

    public Space(SpaceType type, Position position){
        this.type = type;
        this.position = position;
        this.tile = null;
    }

    public SpaceType getType(){
        return type;
    }

    public boolean isFree(){
        return free;
    }

    public Position getPosition(){
        return position;
    }

    public void setTile(ItemTile tile) throws IllegalAccessError{
        if (getType() == SpaceType.DEFAULT) {
            this.tile = tile;
            this.free = true;
        } else {
            throw new IllegalAccessError();
        }
    }
    public ItemTile getTile(){
        return this.tile;

    }

    public ItemTile drawTile() throws IllegalAccessError{
        if (this.free){
            ItemTile tempTile = this.tile;
            this.tile = null;
            this.free = false;
            return tempTile;
        }
        else {
            throw new IllegalAccessError();
        }

    }

}
