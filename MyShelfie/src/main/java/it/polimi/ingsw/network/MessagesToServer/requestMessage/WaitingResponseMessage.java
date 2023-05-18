package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.network.MessagesToServer.MessageToClientType;

public class WaitingResponseMessage extends RequestMessage{
    public WaitingResponseMessage() {
        super(MessageToClientType.WAIT_PLAYERS);
    }
}
