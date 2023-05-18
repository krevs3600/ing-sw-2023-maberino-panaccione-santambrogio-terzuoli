package it.polimi.ingsw.network.MessagesToServer.requestMessage.errorMessages;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;

public abstract class ErrorMessage implements Serializable, Message {
    private final long serialVersionUID = 1L;
    private final String nickName;

    private MessageType type;
    private final String errorMessage;

    public ErrorMessage(String nickName, String errorMessage,MessageType messageType) {
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

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString(){
        return getNickName() + " got error: " + errorMessage;
    }

}
