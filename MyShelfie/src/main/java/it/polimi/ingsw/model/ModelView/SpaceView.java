package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.io.Serializable;

public class SpaceView extends Observable implements Observer {

    private final Space space;

    public SpaceView(Space space){
        this.space = space;
    }

    public SpaceView getSpace() {
        return new SpaceView(space);
    }

    public SpaceType getType() { return space.getType(); }
    public boolean isFree() { return space.isFree(); }
    public Position getPosition(){
        return space.getPosition();
    }
    public ItemTile getTile() { return space.getTile(); }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        if (!this.isFree() && this.getType() == SpaceType.PLAYABLE){
            return this.getTile().toString();
        } else if (this.getType() == SpaceType.FORBIDDEN) {
            return " X ";
        } else {
            return "   ";
        }
    }

}
