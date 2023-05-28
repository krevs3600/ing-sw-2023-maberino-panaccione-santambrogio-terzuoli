package it.polimi.ingsw.network.eventMessages;

public class PlayerTurnMessage extends EventMessage {
    private final long serialVersionUID = 1L;

    public PlayerTurnMessage(String player) {
        super(player, EventMessageType.PLAYER_TURN);
    }

    @Override
    public String toString() {
        return getNickname() + " is your turn!";
    }
}
