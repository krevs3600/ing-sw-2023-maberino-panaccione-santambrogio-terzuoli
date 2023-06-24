package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class GameSpecsResponseMessage extends RequestMessage{

    private final long serialVersionUID = 1L;
    private final boolean validGameName;
    private final boolean validGameCreation;


    public GameSpecsResponseMessage(String nickname, boolean validGameName, boolean validGameCreation) {
        super(nickname, MessageToClientType.GAME_SPECS);
        this.validGameName = validGameName;
        this.validGameCreation = validGameCreation;
    }

    public boolean isValidGameCreation(){
        return validGameCreation;
    }
    public boolean isValidGameName(){
        return this.validGameName;
    }
}
