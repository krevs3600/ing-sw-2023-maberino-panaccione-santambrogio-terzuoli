package it.polimi.ingsw.model.utils;

import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.Space;

/**
 * <h1>Enumeration SpaceType</h1>
 * The enumeration SpaceType contains all the types of a {@link Space} of the {@link LivingRoomBoard}
 * A {@link Space} can be either playable or not (forbidden). The playable spaces change according to the {@link NumberOfPlayers}
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public enum SpaceType {
    PLAYABLE,
    FORBIDDEN
}
