package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.LivingRoomBoard;

/**
 * <h1>Class PickingTilesMessage</h1>
 * The class PickingTilesMessage extends the abstract class EventMessage. A {@link Player} is picking tiles
 * form the {@link LivingRoomBoard}
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/28/2023
 */
public class PickingTilesMessage extends EventMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param player who is picking tiles
     */
    public PickingTilesMessage(String player) {
        super(player, EventMessageType.PICKING_TILES);
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString() {
        return getNickname() + " is picking tiles";
    }
}
