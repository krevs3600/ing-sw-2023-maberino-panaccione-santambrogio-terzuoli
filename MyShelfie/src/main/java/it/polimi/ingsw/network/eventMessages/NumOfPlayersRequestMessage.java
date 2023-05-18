package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;

public class NumOfPlayersRequestMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public NumOfPlayersRequestMessage(String nickName) {
        super(nickName, MessageType.NUM_OF_PLAYERS_REQUEST);
    }
}
