package it.polimi.ingsw.network.eventMessages;

public class PickingTilesMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public PickingTilesMessage(String player) {
        super(player, EventMessageType.PICKING_TILES);
    }

    @Override
    public String toString() {
        return getNickname() + " is picking tiles";
    }
}
