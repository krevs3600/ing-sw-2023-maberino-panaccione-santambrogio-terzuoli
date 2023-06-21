package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import java.io.Serial;
import java.io.Serializable;

public class SpaceView implements Serializable {
    private final Position position;
    private final boolean isFree;
    private final SpaceType type;
    private final ItemTile tile;

    @Serial
    private static final long serialVersionUID = 1L;

    public SpaceView(Space space){
        this.position = space.getPosition();
        this.isFree = space.isFree();
        this.type = space.getType();
        this.tile = space.getTile();

    }

    public SpaceView getSpace() {
        return this;
    }

    public SpaceType getType() { return this.type; }
    public boolean isFree() { return this.isFree; }
    public Position getPosition(){
        return this.position;
    }
    public ItemTile getTile() { return this.tile; }

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
