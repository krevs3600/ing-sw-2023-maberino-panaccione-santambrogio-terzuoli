package it.polimi.ingsw.network.eventMessages;

public class ResumeGameMessage extends EventMessage{
    private final long serialVersionUID = 1L;


    public ResumeGameMessage(String nickName) {
        super(nickName, EventMessageType.RESUME_GAME_REQUEST);
    }
    public String toString(){
        return getNickname() + " asks to resume a saved game";
    }


}