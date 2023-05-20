package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;

public class PlayerTurnMessage extends EventMessage {

    public PlayerTurnMessage(String player) {
        super(player, MessageType.PLAYER_TURN);
    }

    @Override
    public String toString() {
        return getNickname() + " is your turn!";
    }
}
