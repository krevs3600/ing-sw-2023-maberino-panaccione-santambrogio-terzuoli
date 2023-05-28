package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class PlayerJoinedLobbyMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    public PlayerJoinedLobbyMessage(String nickName) {
        super(nickName, MessageToClientType.PLAYER_JOINED_LOBBY_RESPONSE);
    }
}
