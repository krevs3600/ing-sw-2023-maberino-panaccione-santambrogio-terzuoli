package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.PlayerView;
import it.polimi.ingsw.network.MessageType;

public class EndGameMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final PlayerView player;
    public EndGameMessage(PlayerView player) {
        super(player.getName(), MessageType.END_GAME);
        this.player = player;
    }

    public PlayerView getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "End game: winner is " + getPlayer();
    }
}
