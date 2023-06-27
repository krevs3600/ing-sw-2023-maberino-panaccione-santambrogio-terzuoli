package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.util.Set;

public class ResumeGameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    public ResumeGameResponseMessage(String nickname) {
        super(nickname, MessageToClientType.RESUME_GAME_RESPONSE);
    }
}