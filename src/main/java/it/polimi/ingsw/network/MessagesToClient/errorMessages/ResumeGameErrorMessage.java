package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

/**
 * <h1>Class ResumeGameErrorMessage</h1>
 * The class ResumeGameErrorMessage extends the abstract class ErrorMessage. The purpose of this message is to
 * tell the {@link Client} that it is trying to resume a {@link Game} that does not exist
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/22/2023
 */
public class ResumeGameErrorMessage extends ErrorMessage {
    private final long serialVersionUID = 1L;

    /**
     * Constructor method
     * @param nickName of the {@link Client} trying to resume the {@link Game}
     * @param errorMessage content of the message
     */
    public ResumeGameErrorMessage(String nickName, String errorMessage) {
        super(nickName, errorMessage, MessageToClientType.RESUME_GAME_ERROR);
    }

}