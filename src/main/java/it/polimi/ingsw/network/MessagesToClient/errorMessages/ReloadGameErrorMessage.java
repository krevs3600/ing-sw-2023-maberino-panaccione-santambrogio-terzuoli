package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.Client;
/**
 * <h1>Class ReloadGameErrorMessage</h1>
 * The class ReloadGameErrorMessage extends the abstract class ErrorMessage. The purpose of this message is to
 * tell the {@link Client} that it is trying to reload a {@link Game} that does not exist
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/27/2023
 */
public class ReloadGameErrorMessage extends ErrorMessage {
    private final long serialVersionUID = 1L;

    /**
     * Constructor Method
     * @param nickName of the {@link Client} who is trying to reload the {@link Game}
     * @param errorMessage content of the message
     */
    public ReloadGameErrorMessage(String nickName, String errorMessage) {
        super(nickName, errorMessage, MessageToClientType.RELOAD_GAME_ERROR);
    }

}