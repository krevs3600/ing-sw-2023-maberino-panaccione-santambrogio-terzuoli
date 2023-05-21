package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class PlayerOfflineMessage extends RequestMessage {
    private final String nickname;
    public PlayerOfflineMessage(String nickname) {
        super(MessageToClientType.PLAYER_OFFLINE);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
