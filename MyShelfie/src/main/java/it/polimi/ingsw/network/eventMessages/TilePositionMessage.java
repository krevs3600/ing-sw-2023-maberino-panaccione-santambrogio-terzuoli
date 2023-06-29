package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.LivingRoomBoard;

/**
 * <h1>Class TilePositionMessage</h1>
 * The class TilePositionMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * that the current {@link Player} has selected a {@link Position} inside the {@link LivingRoomBoard} from which to pick an {@link ItemTile}
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 5/9/2023
 */
public class TilePositionMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final Position position;

    /**
     * Class constructor
     * @param nickName of the {@link Player} picking tiles
     * @param position of{@link ItemTile} he/she intends to pick
     */
    public TilePositionMessage(String nickName, Position position) {
        super(nickName, EventMessageType.TILE_POSITION);
        this.position = position;
    }

    /**
     * Getter method
     * @return the {@link Position} the {@link Player } has selected
     */
    public Position getPosition() {
        return position;
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString(){
        return getNickname()+ " sends position of tile " + getPosition().toString();
    }
}
