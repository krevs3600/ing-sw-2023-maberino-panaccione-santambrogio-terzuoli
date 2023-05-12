package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class ItemTileIndexMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private int index;

    public ItemTileIndexMessage (String nickName, int index) {
        super(nickName, MessageType.ITEM_TILE_INDEX);
        this.index = index;
    }

    public int getIndex () {
        return this.index;
    }
    @Override
    public String toString() {
        return getNickName() + " is inserting the item tile from position " + index + " of the tilepack";
    }

}