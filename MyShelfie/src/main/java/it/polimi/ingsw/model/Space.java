package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;

import java.util.Observable;
import java.util.Observer;

/**
 * <h1>Class Space</h1>
 * The class Space represents the unit out of which the {@link LivingRoomBoard} is composed
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/21/2023
 */


public class Space extends Observable {
    private boolean free;
    private SpaceType type;
    private Position position;
    private ItemTile tile;

    /**
     * Class contructor
     * @param type the type of the space
     * @param position the {@link Space#position} of the space on the {@link LivingRoomBoard}
     */
    public Space(SpaceType type, Position position){
        this.type = type;
        this.position = position;
        this.tile = null;
        this.free = this.getType() != SpaceType.FORBIDDEN;
    }

    /**
     * Getter method
     * @return the {@link Space#type} of the {@link Space}
     */
    public SpaceType getType(){
        return type;
    }

    /**
     * Getter method
     * @return a {@code boolean} value, corresponding to {@code true} if the {@link Space} is currently free, and {@code false} otherwise
     */
    public boolean isFree(){
        return free;
    }

    /**
     * Getter method
     * @return the {@link Space#position} of the {@link Space}
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Setter method places an {@link ItemTile} on the {@link Space}
     * @param tile the {@link ItemTile}  to be placed on the {@link Space}
     * @throws IllegalAccessError The exception is thrown if the {@link Space} is not free
     */
    public void setTile(ItemTile tile) throws IllegalAccessError{
        if (getType().equals(SpaceType.PLAYABLE) && this.isFree()) {
            this.tile = tile;
            this.free = false;
        } else {
            throw new IllegalAccessError("The space is not available, please choose another space to put the tile");
        }
    }

    /**
     * Getter method
     * @return the {@link ItemTile} placed on the {@link Space} and {@code null} otherwise
     * @exception IllegalAccessError The exception is thrown if the selected {@link Space} is free or forbidden, namely not actually part of the {@link LivingRoomBoard}
     */
    public ItemTile getTile() {
        if(this.getType().equals(SpaceType.FORBIDDEN) || this.isFree()){
            //throw new IllegalAccessError("The selected space is not playable or not free");
            return null;
        } else {
            return this.tile;
        }
    }

    /**
     * This method removes the {@link ItemTile} from the {@link Space}.
     * @return the {@link ItemTile}  placed over the {@link Space}
     * @throws IllegalAccessError The exception is thrown if the {@link Space} is free or not playable
     */
    public ItemTile drawTile() throws IllegalAccessError{
        if (!isFree() && this.getType().equals(SpaceType.PLAYABLE)){
            ItemTile tempTile = this.tile;
            this.tile = null;
            this.free = true;
            return tempTile;
        }
        else {
            throw new IllegalAccessError("The selected space is not playable or not free");
        }

    }

}
