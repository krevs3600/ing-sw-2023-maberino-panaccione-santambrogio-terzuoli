package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class DisconnectionResponseMessage extends RequestMessage{
    private final long serialVersionUID = 1L;
    public DisconnectionResponseMessage(String nickname) {
        super(nickname, MessageToClientType.DISCONNECTION_RESPONSE);
    }
}
