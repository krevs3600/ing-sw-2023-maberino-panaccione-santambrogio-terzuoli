package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.io.Serializable;

public abstract class ErrorMessage extends MessageToClient {
    private final long serialVersionUID = 1L;

    private final MessageToClientType type;
    private final String errorMessage;

    public ErrorMessage(String nickName, String errorMessage, MessageToClientType messageType) {
        super(nickName, messageType);
        this.errorMessage = errorMessage;
        this.type=messageType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public MessageToClientType getType() {
        return type;
    }

    @Override
    public String toString(){
        return getNickname() + " got error: " + errorMessage;
    }

}
