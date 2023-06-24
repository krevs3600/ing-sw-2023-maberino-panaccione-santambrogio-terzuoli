package it.polimi.ingsw.network.eventMessages;

public class ItemTileIndexMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int index;

    public ItemTileIndexMessage (String nickName, int index) {
        super(nickName, EventMessageType.ITEM_TILE_INDEX);
        this.index = index;
    }

    public int getIndex () {
        return this.index;
    }
    @Override
    public String toString() {
        return getNickname() + " is inserting the item tile from position " + index + " of the tilepack";
    }

}