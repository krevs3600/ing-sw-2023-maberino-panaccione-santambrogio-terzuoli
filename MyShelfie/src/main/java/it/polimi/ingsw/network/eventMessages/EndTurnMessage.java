package it.polimi.ingsw.network.eventMessages;

public class EndTurnMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public EndTurnMessage(String nickName) {
        super(nickName, EventMessageType.END_TURN);
    }

    public String toString(){
        return "" + getNickname() + " decided to start placing tiles";
    }
}
