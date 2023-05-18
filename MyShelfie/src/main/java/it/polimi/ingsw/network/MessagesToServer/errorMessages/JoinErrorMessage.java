package it.polimi.ingsw.network.MessagesToServer.errorMessages;

import it.polimi.ingsw.network.MessagesToServer.MessageToServerType;

public class JoinErrorMessage extends ErrorMessage {
    public JoinErrorMessage(String nickName, String errorMessage) {
        super(nickName, errorMessage, MessageToServerType.JOIN_GAME_ERROR);
    }

}
