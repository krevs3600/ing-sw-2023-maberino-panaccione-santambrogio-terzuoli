package it.polimi.ingsw.network.MessagesToServer.requestMessage;

public class GameNameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validGameName;

   private String GameName;


    public GameNameResponseMessage(String GameName,boolean validGameName) {
        super(MessageToServerType.GAMENAME_RESPONSE);
        this.validGameName = validGameName;
        this.GameName=GameName;
    }

    public GameNameResponseMessage(boolean validGameName) {
        super(MessageToServerType.GAMENAME_RESPONSE);
        this.validGameName = validGameName;
    }

    public boolean isValidGameName(){
        return validGameName;
    }
    public String getGameName(){
        return  this.GameName;
    }
}
