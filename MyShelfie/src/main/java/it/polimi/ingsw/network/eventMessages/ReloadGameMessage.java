package it.polimi.ingsw.network.eventMessages;

public class ReloadGameMessage extends EventMessage{
    private final long serialVersionUID = 1L;


    public ReloadGameMessage(String nickName) {
        super(nickName, EventMessageType.RELOAD_GAME_REQUEST);
    }
    public String toString(){
        return getNickname() + " asks to resume a saved game";
    }


}