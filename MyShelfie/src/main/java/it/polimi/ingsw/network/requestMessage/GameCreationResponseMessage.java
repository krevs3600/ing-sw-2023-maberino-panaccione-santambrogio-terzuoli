package it.polimi.ingsw.network.requestMessage;

public class GameCreationResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validGameCreation;



    public GameCreationResponseMessage(boolean validGameCreation) {
        super(RequestMessageType.GAME_CREATION);
        this.validGameCreation = validGameCreation;

    }

    public boolean isValidGameCreation(){
        return validGameCreation;
    }
}
