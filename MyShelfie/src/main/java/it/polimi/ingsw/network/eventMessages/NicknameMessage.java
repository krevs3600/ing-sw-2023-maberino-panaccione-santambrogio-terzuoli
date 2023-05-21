package it.polimi.ingsw.network.eventMessages;

public class NicknameMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public NicknameMessage(String nickName) {
        super(nickName, EventMessageType.NICKNAME);
    }

    @Override
    public String toString(){
        return getNickname() + " asks to be subscribed";
    }
}
