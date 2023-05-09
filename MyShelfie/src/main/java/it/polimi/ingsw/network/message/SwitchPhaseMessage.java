package it.polimi.ingsw.network.message;

public class SwitchPhaseMessage extends Message{
    public SwitchPhaseMessage(String nickName) {
        super(nickName, MessageType.SWITCH_PHASE);
    }

    public String toString(){
        return "" + getNickName() + " decided to start placing tiles";
    }
}
