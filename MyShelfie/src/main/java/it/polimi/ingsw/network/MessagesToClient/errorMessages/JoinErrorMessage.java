package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class JoinErrorMessage extends ErrorMessage {
    public JoinErrorMessage(String nickName, String errorMessage) {
        super(nickName, errorMessage, MessageToClientType.JOIN_GAME_ERROR);
    }

}
