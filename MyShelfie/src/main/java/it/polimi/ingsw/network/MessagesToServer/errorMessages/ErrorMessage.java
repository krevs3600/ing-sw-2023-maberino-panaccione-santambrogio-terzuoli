package it.polimi.ingsw.network.MessagesToServer.errorMessages;

import it.polimi.ingsw.network.MessagesToServer.MessageToServer;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.MessagesToServer.MessageToServerType;

import java.io.Serializable;

public abstract class ErrorMessage implements Serializable, MessageToServer {
    private final long serialVersionUID = 1L;
    private final String nickName;

    private MessageToServerType type;
    private final String errorMessage;

    public ErrorMessage(String nickName, String errorMessage,MessageToServerType messageType) {
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

    public MessageToServerType getType() {
        return type;
    }

    @Override
    public String toString(){
        return getNickName() + " got error: " + errorMessage;
    }

}
