package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class IllegalTilePositionErrorMessage extends ErrorMessage {

    private final long serialVersionUID = 1L;
    public IllegalTilePositionErrorMessage(String nickname, String message) {
        super(nickname, message, MessageToClientType.ILLEGAL_POSITION);
    }
}
