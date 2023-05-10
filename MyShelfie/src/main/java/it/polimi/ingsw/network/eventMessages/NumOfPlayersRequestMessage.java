package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class NumOfPlayersRequestMessage extends EventMessage {
    public NumOfPlayersRequestMessage(String nickName) {
        super(nickName, MessageType.NUM_OF_PLAYERS_REQUEST);
    }
}
