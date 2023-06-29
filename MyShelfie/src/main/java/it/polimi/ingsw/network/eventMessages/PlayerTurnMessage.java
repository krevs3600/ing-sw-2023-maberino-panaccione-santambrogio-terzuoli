package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class PlayerTurnMessage</h1>
 * The class PlayerTurnMessage extends the abstract class EventMessage. The purpose of this message is to
 * communicate that it' a {@link Player}'s turn to play
 *
 * @author Francesco Santambrogio, Carlo Terzuoli
 * @version 1.0
 * @since 5/19/2023
 */
public class PlayerTurnMessage extends EventMessage {
    private final long serialVersionUID = 1L;

    /**
     * Cosntructor class
     * @param player whose turn it is to play
     */
    public PlayerTurnMessage(String player) {
        super(player, EventMessageType.PLAYER_TURN);
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString() {
        return getNickname() + " is your turn!";
    }
}
