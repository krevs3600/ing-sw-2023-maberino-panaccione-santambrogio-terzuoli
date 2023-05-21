package it.polimi.ingsw.network.eventMessages;

public class NumOfPlayersRequestMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public NumOfPlayersRequestMessage(String nickName) {
        super(nickName, EventMessageType.NUM_OF_PLAYERS_REQUEST);
    }
}
