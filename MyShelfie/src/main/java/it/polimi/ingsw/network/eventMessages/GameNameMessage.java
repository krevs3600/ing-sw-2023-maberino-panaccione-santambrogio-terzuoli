package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class GameNameMessage extends EventMessage {
    private String GameName;
    private final long serialVersionUID = 1L;
    public GameNameMessage(String nickName,String GameName) {
        super(nickName,MessageType.GAMENAME);
        this.GameName=GameName;


    }

    public String getGameName() {
        return GameName;
    }

    @Override
    public String toString(){
        return getNickName() + " asks to choose a specific Game Name";
    }
}
