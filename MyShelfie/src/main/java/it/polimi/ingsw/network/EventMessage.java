package it.polimi.ingsw.network;

import it.polimi.ingsw.network.eventMessages.MessageType;

import java.io.Serializable;

public abstract class EventMessage implements Serializable {
    private String nickName;
    private MessageType type;

    public EventMessage(String nickName, MessageType messageType){
        this.nickName = nickName;
        this.type = messageType;
    }
    public String getNickName(){
        return this.nickName;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString(){
        return this.nickName + " sends message of type " + type.name();
    }

}
