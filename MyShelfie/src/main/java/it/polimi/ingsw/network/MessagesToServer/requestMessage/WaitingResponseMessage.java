package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.network.MessagesToServer.MessageToClientType;

public class WaitingResponseMessage extends RequestMessage{
    private final int missingPlayers;

    public WaitingResponseMessage(int missingPlayers) {
        super(MessageToClientType.WAIT_PLAYERS);
        this.missingPlayers = missingPlayers;
    }

    public int getMissingPlayers() {
        return missingPlayers;
    }
}
