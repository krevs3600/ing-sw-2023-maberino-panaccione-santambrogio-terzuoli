package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class NotEnoughInsertableTilesErrorMessage extends ErrorMessage {

    private final long serialVersionUID = 1L;
    public NotEnoughInsertableTilesErrorMessage(String nickname, String message) {
        super(nickname, message, MessageToClientType.NOT_ENOUGH_INSERTABLE_TILES);
    }
}
