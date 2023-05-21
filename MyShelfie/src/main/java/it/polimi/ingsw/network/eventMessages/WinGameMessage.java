package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.PlayerView;

public class WinGameMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final PlayerView player;
    public WinGameMessage(PlayerView player) {
        super(player.getName(), EventMessageType.END_GAME);
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
