package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class NicknameMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public NicknameMessage(String nickName) {
        super(nickName, MessageType.NICKNAME);
    }

    @Override
    public String toString(){
        return getNickName() + " asks to be subscribed";
    }
}
