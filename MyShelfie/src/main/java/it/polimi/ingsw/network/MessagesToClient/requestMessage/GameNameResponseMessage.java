package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class GameNameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validGameName;

   private final String GameName;


    public GameNameResponseMessage(String nickname, String GameName,boolean validGameName) {
        super(nickname, MessageToClientType.GAME_NAME_RESPONSE);
        this.validGameName = validGameName;
        this.GameName=GameName;
    }

    public boolean isValidGameName(){
        return validGameName;
    }
    public String getGameName(){
        return this.GameName;
    }
}
