package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Bookshelf;

/**
 * <h1>Class NotEnoughInsertableTilesErrorMessage</h1>
 * The class NotEnoughInsertableTilesErrorMessage extends the abstract class ErrorMessage. The purpose of this message is to
 * tell the {@link Client} it has picked too many {@link ItemTile}s during the current turn, and it would not be
 * able to insert all of them inside any column of the {@link Bookshelf}
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/24/2023
 */
public class NotEnoughInsertableTilesErrorMessage extends ErrorMessage {
    private final long serialVersionUID = 1L;

    /**
     * Constructor method
     * @param nickname of the client who is picking too many {@link ItemTile}s
     * @param message the content of the message
     */
    public NotEnoughInsertableTilesErrorMessage(String nickname, String message) {
        super(nickname, message, MessageToClientType.NOT_ENOUGH_INSERTABLE_TILES);
    }
}
