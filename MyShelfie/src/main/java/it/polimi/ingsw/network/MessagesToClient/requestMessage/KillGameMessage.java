package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.eventMessages.EventMessageType;

public class KillGameMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    public KillGameMessage(String nickname) {
        super(nickname, MessageToClientType.KILL_GAME);
    }
}
