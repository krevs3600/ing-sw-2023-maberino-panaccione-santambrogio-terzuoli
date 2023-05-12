package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class SwitchPhaseMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public SwitchPhaseMessage(String nickName) {
        super(nickName, MessageType.SWITCH_PHASE);
    }

    public String toString(){
        return "" + getNickName() + " decided to start placing tiles";
    }
}
