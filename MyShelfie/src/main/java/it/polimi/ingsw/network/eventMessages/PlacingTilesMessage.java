package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Bookshelf;
/**
 * <h1>Class PlacingTilesMessage</h1>
 * The class PlacingTilesMessage extends the abstract class EventMessage. A {@link Player} is placing tiles
 * in the {@link Bookshelf}
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/28/2023
 */
public class PlacingTilesMessage extends EventMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param player the{@link Player} who is placing tiles
     */
    public PlacingTilesMessage(String player) {
        super(player, EventMessageType.PLACING_TILES);
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString() {
        return getNickname() + " is placing the tiles on the bookshelf";
    }
}
