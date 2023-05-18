package it.polimi.ingsw.network.MessagesToServer.errorMessages;

import it.polimi.ingsw.network.MessagesToServer.MessageToClientType;

public class JoinErrorMessage extends ErrorMessage {
    public JoinErrorMessage(String nickName, String errorMessage) {
        super(nickName, errorMessage, MessageToClientType.JOIN_GAME_ERROR);
    }

}
