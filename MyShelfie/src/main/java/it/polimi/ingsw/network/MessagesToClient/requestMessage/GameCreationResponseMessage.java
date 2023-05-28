package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class GameCreationResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validGameCreation;



    public GameCreationResponseMessage(String nickname, boolean validGameCreation) {
        super(nickname, MessageToClientType.GAME_CREATION);
        this.validGameCreation = validGameCreation;

    }

    public boolean isValidGameCreation(){
        return validGameCreation;
    }
}
