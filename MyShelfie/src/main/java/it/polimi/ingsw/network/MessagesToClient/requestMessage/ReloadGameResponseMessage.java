package it.polimi.ingsw.network.MessagesToClient.requestMessage;


import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.util.Set;

public class ReloadGameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    public ReloadGameResponseMessage(String nickname) {
        super(nickname, MessageToClientType.JOIN_GAME_RESPONSE);
    }

}