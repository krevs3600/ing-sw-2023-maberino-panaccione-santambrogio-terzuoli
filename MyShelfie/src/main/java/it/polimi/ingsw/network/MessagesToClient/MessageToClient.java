package it.polimi.ingsw.network.MessagesToClient;

import java.io.Serializable;

public abstract class MessageToClient implements Serializable {

    private final MessageToClientType type;
    private final String clientNickname;
    public MessageToClient (String nickname, MessageToClientType type) {
        this.clientNickname = nickname;
        this.type = type;
    }

    public String getNickname () {
        return clientNickname;
    }

    public MessageToClientType getType () {
        return type;
    }

}
