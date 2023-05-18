package it.polimi.ingsw.network.eventMessages.RequestMessage;

import it.polimi.ingsw.network.eventMessages.GameNameMessage;

public class GameNameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validGameName;

   private String GameName;


    public GameNameResponseMessage(String GameName,boolean validGameName) {
        super(RequestMessageType.LOGIN_RESPONSE);
        this.validGameName = validGameName;
        this.GameName=GameName;
    }

    public GameNameResponseMessage(boolean validGameName) {
        super(RequestMessageType.LOGIN_RESPONSE);
        this.validGameName = validGameName;
    }

    public boolean isValidGameName(){
        return isValidGameName();
    }
    public String getGameName(){
        return  this.GameName;

    }
}
