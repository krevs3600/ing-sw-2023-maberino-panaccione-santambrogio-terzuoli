package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.ModelView.PlayerView;
import it.polimi.ingsw.model.Player;

public class EndGameMessage extends Message {

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
