package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class PlayerOfflineMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    public PlayerOfflineMessage(String nickname) {
        super(nickname, MessageToClientType.PLAYER_OFFLINE);
    }
}
