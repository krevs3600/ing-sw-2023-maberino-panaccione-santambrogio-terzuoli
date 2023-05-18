package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import java.io.Serializable;

public abstract class RequestMessage implements Serializable {
    private MessageToServerType type;

    public RequestMessage(MessageToServerType messageType){
        this.type = messageType;
    }

    public MessageToServerType getType() {
        return type;
    }

    @Override
    public String toString(){
        return "Server is sending the following message: " + type.name();
    }

}