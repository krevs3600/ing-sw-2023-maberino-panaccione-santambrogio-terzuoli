package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.io.Serializable;

public abstract class RequestMessage extends MessageToClient {


    private final long serialVersionUID = 1L;
    public RequestMessage(String nickname, MessageToClientType messageType){

        super (nickname, messageType);
    }

    @Override
    public String toString(){
        return "Server is sending the following message: " + getType().name();
    }

}