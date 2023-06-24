package it.polimi.ingsw.network.eventMessages;

public class PingMessage extends EventMessage {

    private final long serialVersionUID = 1L;


    public PingMessage(String nickName) {
        super(nickName, EventMessageType.PING);
    }
    public String toString(){
        return getNickname() + " is sending a ping";
    }
}
