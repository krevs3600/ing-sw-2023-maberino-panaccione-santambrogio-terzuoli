package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class KillGameMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    public KillGameMessage(String nickname) { super(nickname, MessageToClientType.KILL_GAME);}
}
