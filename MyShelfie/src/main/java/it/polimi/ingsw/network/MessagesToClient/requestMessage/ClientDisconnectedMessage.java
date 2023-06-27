package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class ClientDisconnectedMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    public ClientDisconnectedMessage(String nickname) {
        super(nickname, MessageToClientType.CLIENT_DISCONNECTION);
    }
}