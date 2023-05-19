package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.network.MessagesToServer.MessageToClientType;

public class PlayerJoinedLobbyMessage extends RequestMessage {
    private String nickname;
    public PlayerJoinedLobbyMessage(String nickName) {
        super(MessageToClientType.PLAYER_JOINED_LOBBY_RESPONSE);
        this.nickname = nickName;
    }

    public String getNickname() {
        return nickname;
    }
}
