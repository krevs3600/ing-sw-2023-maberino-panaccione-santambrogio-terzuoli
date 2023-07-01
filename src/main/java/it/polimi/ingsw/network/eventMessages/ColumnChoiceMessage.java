package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;

/**
 * <h1>Class ColumnChoiceMessage</h1>
 * The class ColumnChoiceMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * that the current {@link Player} has selected in which column of his {@link Bookshelf} to insert the tiles he/she has
 * picked
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/9/2023
 */
public class ColumnChoiceMessage extends EventMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class Constructor
     * @param player who has sent the message
     */
    public ColumnChoiceMessage(String player) {
        super(player, EventMessageType.COLUMN_CHOICE);
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString() {
        return getNickname() + " is choosing the column where to insert the tiles";
    }
}
