package it.polimi.ingsw.network.eventMessages;

public class LastTurnMessage extends EventMessage{
    private final long serialVersionUID = 1L;

    public LastTurnMessage(String nickName) {
        super(nickName, EventMessageType.LAST_TURN);
    }
}
