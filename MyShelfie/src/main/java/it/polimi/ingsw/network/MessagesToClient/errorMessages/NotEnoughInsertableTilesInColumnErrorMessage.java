package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class NotEnoughInsertableTilesInColumnErrorMessage extends ErrorMessage {

    private final long serialVersionUID = 1L;
    public NotEnoughInsertableTilesInColumnErrorMessage(String nickname, String message) {
        super(nickname, message, MessageToClientType.NOT_ENOUGH_INSERTABLE_TILES_IN_COLUMN);
    }
}
