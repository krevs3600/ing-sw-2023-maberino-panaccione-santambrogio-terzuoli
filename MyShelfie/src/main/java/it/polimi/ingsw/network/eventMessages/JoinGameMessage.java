package it.polimi.ingsw.network.eventMessages;

public class JoinGameMessage extends EventMessage{
    private final long serialVersionUID = 1L;


    public JoinGameMessage(String nickName) {
        super(nickName, EventMessageType.JOIN_GAME_REQUEST);
    }
    public String toString(){
        return getNickname() + " asks to Join a Game";
    }


}
