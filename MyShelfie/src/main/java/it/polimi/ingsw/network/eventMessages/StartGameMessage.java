package it.polimi.ingsw.network.eventMessages;

public class StartGameMessage extends EventMessage{
    private final long serialVersionUID = 1L;


    public StartGameMessage(String nickName) {
        super(nickName, EventMessageType.START_GAME);
    }
    public String toString(){
        return getNickname() + " asks to start a new game";
    }


}


