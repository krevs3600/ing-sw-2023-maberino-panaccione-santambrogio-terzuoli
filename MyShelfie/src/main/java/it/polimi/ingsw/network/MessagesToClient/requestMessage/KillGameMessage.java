package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.eventMessages.EventMessageType;

public class KillGameMessage extends RequestMessage {
    private final String nickname;
    public KillGameMessage(String nickname) {
        super(MessageToClientType.KILL_GAME);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
