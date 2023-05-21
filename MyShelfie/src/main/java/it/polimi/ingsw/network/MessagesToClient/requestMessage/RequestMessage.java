package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.io.Serializable;

public abstract class RequestMessage implements Serializable, MessageToClient {
    private MessageToClientType type;

    public RequestMessage(MessageToClientType messageType){
        this.type = messageType;
    }

    public MessageToClientType getType() {
        return type;
    }

    @Override
    public String toString(){
        return "Server is sending the following message: " + type.name();
    }

}