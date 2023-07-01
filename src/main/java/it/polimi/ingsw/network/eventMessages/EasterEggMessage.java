package it.polimi.ingsw.network.eventMessages;

public class EasterEggMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public EasterEggMessage(String nickName) {
        super(nickName, EventMessageType.EASTER_EGG);
    }

    public String toString(){
        return "" + getNickname() + " has found the easter egg";
    }
}
