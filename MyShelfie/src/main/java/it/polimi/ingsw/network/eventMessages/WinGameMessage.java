package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.PlayerView;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Game;

/**
 * <h1>Class WinGameMessage</h1>
 * The class WinGameMessage extends the abstract class EventMessage. The purpose of this message is to
 * communicate who is the {@link Player} who has won the {@link Game}
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 5/9/2023
 */
public class WinGameMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final PlayerView player;

    /**
     * Class constructor
     * @param player  who has won the {@link Game}
     */
    public WinGameMessage(PlayerView player) {
        super(player.getName(), EventMessageType.END_GAME);
        this.player = player;
    }

    /**
     * Getter method
     * @return the {@link Player} who has won the game
     */
    public PlayerView getPlayer() {
        return player;
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString() {
        return "End game: winner is " + getPlayer();
    }
}
