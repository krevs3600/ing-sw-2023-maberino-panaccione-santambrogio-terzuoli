package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class ResumeGameErrorMessage extends ErrorMessage {

    private final long serialVersionUID = 1L;
    public ResumeGameErrorMessage(String nickName, String errorMessage) {
        super(nickName, errorMessage, MessageToClientType.RESUME_GAME_ERROR);
    }

}