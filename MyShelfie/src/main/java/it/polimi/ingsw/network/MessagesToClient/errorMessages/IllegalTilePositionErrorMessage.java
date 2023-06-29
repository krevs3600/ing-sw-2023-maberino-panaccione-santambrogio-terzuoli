package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class IllegalTilePositionMessage</h1>
 * The class IllegalTilePositionMessage extends the abstract class ErrorMessage. The purpose of this message is to
 * tell the {@link Client} that the {@link Position} he/she intended to pick from is illegal, and invites him/her to
 * choose a new one
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/24/2023
 */
public class IllegalTilePositionErrorMessage extends ErrorMessage {

    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Player} the message is being sent to
     * @param message content of the message
     */
    public IllegalTilePositionErrorMessage(String nickname, String message) {
        super(nickname, message, MessageToClientType.ILLEGAL_POSITION);
    }
}
