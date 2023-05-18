package it.polimi.ingsw.network.requestMessage;

import java.io.Serializable;

public abstract class RequestMessage implements Serializable {
    private RequestMessageType type;

    public RequestMessage(RequestMessageType messageType){
        this.type = messageType;
    }

    public RequestMessageType getType() {
        return type;
    }

    @Override
    public String toString(){
        return "Server is sending the following message: " + type.name();
    }

}