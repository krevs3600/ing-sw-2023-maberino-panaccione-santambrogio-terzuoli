package it.polimi.ingsw.network.message;

public abstract class Message {
    private String nickName;
    private MessageType type;

    public Message(String nickName, MessageType messageType){
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
