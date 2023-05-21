package it.polimi.ingsw.network.eventMessages;

public class PlayerTurnMessage extends EventMessage {

    public PlayerTurnMessage(String player) {
        super(player, EventMessageType.PLAYER_TURN);
    }

    @Override
    public String toString() {
        return getNickname() + " is your turn!";
    }
}
