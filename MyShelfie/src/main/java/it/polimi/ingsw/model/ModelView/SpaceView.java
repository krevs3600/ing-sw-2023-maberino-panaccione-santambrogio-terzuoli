package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import java.io.Serial;
import java.io.Serializable;
/**
 * <h1>Class SpaceView</h1>
 * This class is the immutable version of class Space
 *
 * @author Carlo Terzuoli
 * @version 1.0
 * @since 5/06/2023
 */
public class SpaceView implements Serializable {
    /**
     * Space's position in the LivingRoomBoard
     */
    private final Position position;
    /**
     * boolean is true if no tiles are present in the space, otherwise is false
     */
    private final boolean isFree;
    /**
     * Type of space
     */
    private final SpaceType type;
    /**
     * ItemTile present in Space
     */
    private final ItemTile tile;
    /**
     * UID version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for class SpaceView
     * @param space Space object to create immutable version
     */
    public SpaceView(Space space){
        this.position = space.getPosition();
        this.isFree = space.isFree();
        this.type = space.getType();
        this.tile = space.getTile();

    }

    /**
     * Getter method for SpaceView
     * @return SpaceView
     */
    public SpaceView getSpace() {
        return this;
    }

    /**
     * Getter method for SpaceType
     * @return SpaceType
     */
    public SpaceType getType() { return this.type; }

    /**
     * Getter method for boolean isFree
     * @return boolean isFree
     */
    public boolean isFree() { return this.isFree; }

    /**
     * Getter method for space's position
     * @return the position of the space in the game's board
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * Getter method for the ItemTile
     * @return the ItemTile in the SpaceView
     */
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
