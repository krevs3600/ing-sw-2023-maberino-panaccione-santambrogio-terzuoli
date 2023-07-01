package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.TilePack;

/**
 * <h1>Class ItemTileIndexMessage</h1>
 * The class ItemTileIndexMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * that the current {@link Player} has selected from which index of the {@link TilePack} to pick the tile to insert in the {@link Bookshelf}
 *
 * @author Carlo Terzuoli, Francesco Santambrogio
 * @version 1.0
 * @since 5/12/2023
 */
public class ItemTileIndexMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int index;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     * @param index of the position inside the {@link TilePack}
     */
    public ItemTileIndexMessage (String nickName, int index) {
        super(nickName, EventMessageType.ITEM_TILE_INDEX);
        this.index = index;
    }

    /**
     * Getter method
     * @return index selected by the {@link Player}
     */
    public int getIndex () {
        return this.index;
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString() {
        return getNickname() + " is inserting the item tile from position " + index + " of the tilepack";
    }

}