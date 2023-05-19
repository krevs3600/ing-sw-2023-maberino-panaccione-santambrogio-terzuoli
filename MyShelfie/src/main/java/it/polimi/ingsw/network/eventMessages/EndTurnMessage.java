package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;

public class EndTurnMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public EndTurnMessage(String nickName) {
        super(nickName, MessageType.END_TURN);
    }

    public String toString(){
        return "" + getNickName() + " decided to start placing tiles";
    }
}
