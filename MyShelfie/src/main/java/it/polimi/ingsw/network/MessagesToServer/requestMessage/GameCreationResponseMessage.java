package it.polimi.ingsw.network.MessagesToServer.requestMessage;

public class GameCreationResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validGameCreation;



    public GameCreationResponseMessage(boolean validGameCreation) {
        super(MessageToServerType.GAME_CREATION);
        this.validGameCreation = validGameCreation;

    }

    public boolean isValidGameCreation(){
        return validGameCreation;
    }
}
