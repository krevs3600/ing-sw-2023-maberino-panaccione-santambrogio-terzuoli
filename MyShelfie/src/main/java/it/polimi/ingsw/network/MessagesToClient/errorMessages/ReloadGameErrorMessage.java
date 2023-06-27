package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class ReloadGameErrorMessage extends ErrorMessage {

    private final long serialVersionUID = 1L;
    public ReloadGameErrorMessage(String nickName, String errorMessage) {
        super(nickName, errorMessage, MessageToClientType.RELOAD_GAME_ERROR);
    }

}