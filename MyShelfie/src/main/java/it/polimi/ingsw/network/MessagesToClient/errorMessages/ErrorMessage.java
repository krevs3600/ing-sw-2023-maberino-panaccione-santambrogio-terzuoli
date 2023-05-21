package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.io.Serializable;

public abstract class ErrorMessage implements Serializable, MessageToClient {
    private final long serialVersionUID = 1L;
    private final String nickName;

    private MessageToClientType type;
    private final String errorMessage;

    public ErrorMessage(String nickName, String errorMessage, MessageToClientType messageType) {
        this.nickName=nickName;
        this.errorMessage = errorMessage;
        this.type=messageType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getNickName(){
        return this.nickName;
    }

    public MessageToClientType getType() {
        return type;
    }

    @Override
    public String toString(){
        return getNickName() + " got error: " + errorMessage;
    }

}
