package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;

public class LastTurnMessage extends EventMessage{

    public LastTurnMessage(String nickName) {
        super(nickName, MessageType.LAST_TURN);
    }
}
