package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.model.Game;
/**
 * <h1>Class JoinErrorMessage</h1>
 * The class JoinErrorMessage extends the abstract class ErrorMessage. The purpose of this message is to
 * tell the {@link Client} that there are no such available {@link Game} in the lobby
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/18/2023
 */
public class JoinErrorMessage extends ErrorMessage {

    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickName of the {@link Client} trying to join a {@link Game}
     * @param errorMessage content of the message to be sent
     */
    public JoinErrorMessage(String nickName, String errorMessage) {
        super(nickName, errorMessage, MessageToClientType.JOIN_GAME_ERROR);
    }

}
