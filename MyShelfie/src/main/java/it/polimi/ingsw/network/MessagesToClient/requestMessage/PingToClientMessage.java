package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class PingToClientMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    public PingToClientMessage(String nickname) {
        super(nickname, MessageToClientType.PING);
    }
}
