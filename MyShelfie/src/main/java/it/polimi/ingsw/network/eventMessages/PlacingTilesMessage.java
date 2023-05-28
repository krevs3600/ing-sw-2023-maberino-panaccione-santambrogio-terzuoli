package it.polimi.ingsw.network.eventMessages;

public class PlacingTilesMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public PlacingTilesMessage(String player) {
        super(player, EventMessageType.PLACING_TILES);
    }

    @Override
    public String toString() {
        return getNickname() + " is placing the tiles on the bookshelf";
    }
}
