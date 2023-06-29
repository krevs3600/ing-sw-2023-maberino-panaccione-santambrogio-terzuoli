package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

/**
 * <h1>Class UpperBoundTilePackErrorMessage</h1>
 * The class UpperBoundTilePackErrorMessage extends the abstract class ErrorMessage. The purpose of this message is to
 * tell the {@link Client} it is trying to pick too many {@link ItemTile}s during the current turn (more than 3), and
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/24/2023
 */
public class UpperBoundTilePackErrorMessage extends ErrorMessage {

    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Client} trying to pick too many {@link ItemTile}s
     * @param message content of the message to be sent
     */
    public UpperBoundTilePackErrorMessage(String nickname, String message) {
        super(nickname, message, MessageToClientType.UPPER_BOUND_TILEPACK);
    }
}
