package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class GameSpecsResponseMessage extends RequestMessage{

    private final long serialVersionUID = 1L;
    private final boolean validGameName;
    private String gameName;
    private final boolean validGameCreation;


    public GameSpecsResponseMessage(String gameName,boolean validGameName, boolean validGameCreation) {
        super(MessageToClientType.GAME_SPECS);
        this.validGameCreation = validGameCreation;
        this.validGameName = validGameName;
        this.gameName = gameName;
    }
    public GameSpecsResponseMessage(boolean validGameName, boolean validGameCreation) {
        super(MessageToClientType.GAME_SPECS);
        this.validGameName = validGameName;
        this.validGameCreation = validGameCreation;
    }

    public boolean isValidGameCreation(){
        return validGameCreation;
    }
    public boolean isValidGameName(){
        return this.validGameName;
    }
    public String getGameName(){
        return this.gameName;
    }
}
