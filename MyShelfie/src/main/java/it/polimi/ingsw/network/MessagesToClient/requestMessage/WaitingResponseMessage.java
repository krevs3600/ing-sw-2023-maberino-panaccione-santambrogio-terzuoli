package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class WaitingResponseMessage extends RequestMessage{
    private final long serialVersionUID = 1L;
    private final int missingPlayers;

    public WaitingResponseMessage(String nickname, int missingPlayers) {
        super(nickname, MessageToClientType.WAIT_PLAYERS);
        this.missingPlayers = missingPlayers;
    }

    public int getMissingPlayers() {
        return missingPlayers;
    }
}
