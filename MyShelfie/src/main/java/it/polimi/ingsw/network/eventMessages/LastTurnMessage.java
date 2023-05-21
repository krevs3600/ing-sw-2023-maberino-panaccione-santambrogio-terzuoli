package it.polimi.ingsw.network.eventMessages;

public class LastTurnMessage extends EventMessage{

    public LastTurnMessage(String nickName) {
        super(nickName, EventMessageType.LAST_TURN);
    }
}
