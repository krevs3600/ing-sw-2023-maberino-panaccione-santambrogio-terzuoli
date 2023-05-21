package it.polimi.ingsw.network.eventMessages;

import java.io.Serializable;

public abstract class EventMessage implements Serializable {
    private String nickname;
    private EventMessageType type;

    public EventMessage(String nickName, EventMessageType messageType){
        this.nickname = nickName;
        this.type = messageType;
    }
    public String getNickname(){
        return this.nickname;
    }

    public EventMessageType getType() {
        return type;
    }

    @Override
    public String toString(){
        return this.nickname + " sends message of type " + type.name();
    }

}
