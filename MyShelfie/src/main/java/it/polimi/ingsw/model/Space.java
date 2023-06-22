package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import it.polimi.ingsw.observer_observable.Observable;

import java.io.Serial;
import java.io.Serializable;

/**
 * <h1>Class Space</h1>
 * The class Space represents the unit out of which the {@link LivingRoomBoard} is composed
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/21/2023
 */


public class Space extends Observable  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private boolean free;
    private final SpaceType type;
    private final Position position;
    private ItemTile tile;

    /**
     * Class constructor:
     * this method initializes a {@link Space} thanks to given parameters
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
            if (isFree()) {
                throw new IllegalAccessError("The selected space is free");
            }
            else throw new IllegalAccessError("The selected space is not playable");
        }

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
     * Getter method
     * @return the {@link ItemTile} placed on the {@link Space} or {@code null} otherwise
     */
    public ItemTile getTile() {
        return this.tile;
    }

    /**
     * This method places an {@link ItemTile} on the {@link Space}
     * @param tile the {@link ItemTile}  to be placed on the {@link Space}
     * @throws IllegalAccessError The exception is thrown if the {@link Space} is not free
     */
    public void setTile(ItemTile tile) throws IllegalAccessError{
        if (getType().equals(SpaceType.PLAYABLE) && this.isFree()) {
            this.tile = tile;
            this.free = false;
        } else {
            throw new IllegalAccessError("The space is not available, please choose another space to put the tile on");
        }
    }

}

