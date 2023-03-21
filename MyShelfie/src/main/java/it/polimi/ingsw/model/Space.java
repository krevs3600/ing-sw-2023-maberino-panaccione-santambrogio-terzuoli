package it.polimi.ingsw.model;

public class Space {
    private boolean isActive;
    private SpaceType type;
    private Position position;

    public Space(){

    }

    public SpaceType getType(){
        return type;
    }

    public boolean getIsActive(){
        return isActive;
    }

    public boolean hasAdjacent(){
        return false;
    }

    public boolean hasFreeSide(){
        return false;
    }

    public Position getPosition(){
        return position;
    }

}
