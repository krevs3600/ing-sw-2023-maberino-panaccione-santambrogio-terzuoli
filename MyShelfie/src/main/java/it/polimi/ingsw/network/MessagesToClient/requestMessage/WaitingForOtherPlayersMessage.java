package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class WaitingForOtherPlayersMessage extends RequestMessage {

    private final long serialVersionUID = 1L;
    public WaitingForOtherPlayersMessage(String nickname) {
        super(nickname, MessageToClientType.WAIT_FOR_OTHER_PLAYERS);
    }
}
