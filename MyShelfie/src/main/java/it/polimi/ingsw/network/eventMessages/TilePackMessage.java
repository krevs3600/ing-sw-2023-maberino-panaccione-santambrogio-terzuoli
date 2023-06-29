package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.ModelView.TilePackView;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class TilePackMessage</h1>
 * The class TilePackMessage extends the abstract class EventMessage. This type of message is used to send to the View
 * the {@link TilePackView}
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/10/2023
 */
public class TilePackMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final TilePackView tilePackView;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     * @param tilePackView at the current moment
     */
    public TilePackMessage(String nickName, TilePackView tilePackView) {
        super(nickName, EventMessageType.TILE_PACK);
        this.tilePackView = tilePackView;
    }

    /**
     * Getter method
     * @return the {@link TilePackView} at the current moment
     */
    public TilePackView getTilePackView(){
        return this.tilePackView;
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    public String toString(){
        return getNickname() + " " + "\n" + getTilePackView().toString();
    }


}
