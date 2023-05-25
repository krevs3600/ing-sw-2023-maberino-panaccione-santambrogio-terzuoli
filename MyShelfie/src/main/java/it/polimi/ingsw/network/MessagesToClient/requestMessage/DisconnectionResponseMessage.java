package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class DisconnectionResponseMessage extends RequestMessage{
    public DisconnectionResponseMessage() {
        super(MessageToClientType.DISCONNECTION_RESPONSE);
    }
}
