package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.model.utils.Position;

public class TilePositionMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final Position position;
    public TilePositionMessage(String nickName, Position position) {
        super(nickName, EventMessageType.TILE_POSITION);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString(){
        return getNickname()+ " sends position of tile " + getPosition().toString();
    }
}
