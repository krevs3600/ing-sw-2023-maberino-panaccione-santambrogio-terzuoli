package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.network.MessagesToServer.MessageToClientType;

public class GameCreationResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validGameCreation;



    public GameCreationResponseMessage(boolean validGameCreation) {
        super(MessageToClientType.GAME_CREATION);
        this.validGameCreation = validGameCreation;

    }

    public boolean isValidGameCreation(){
        return validGameCreation;
    }
}
