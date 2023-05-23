package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class UpperBoundTilePackErrorMessage extends ErrorMessage {

    private final long serialVersionUID = 1L;
    public UpperBoundTilePackErrorMessage(String nickname, String message) {
        super(nickname, message, MessageToClientType.UPPER_BOUND_TILEPACK);
    }
}
