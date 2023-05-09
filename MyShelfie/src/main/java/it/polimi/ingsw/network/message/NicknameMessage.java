package it.polimi.ingsw.network.message;

public class NicknameMessage extends Message{
    private final String nickname;

    public NicknameMessage(String nickName) {
        super(nickName, MessageType.NICKNAME);
        this.nickname = nickName;
    }

    public String getNickName() {
        return nickname;
    }

    @Override
    public String toString(){
        return getNickName() + " asks to be subscribed";
    }
}
