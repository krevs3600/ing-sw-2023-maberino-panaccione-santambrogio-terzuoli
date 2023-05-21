package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

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
