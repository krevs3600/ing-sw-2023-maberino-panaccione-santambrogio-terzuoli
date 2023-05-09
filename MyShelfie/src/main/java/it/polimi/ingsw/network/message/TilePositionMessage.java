package it.polimi.ingsw.network.message;
import it.polimi.ingsw.model.utils.Position;

public class TilePositionMessage extends Message {

    private final Position position;
    public TilePositionMessage(String nickName, Position position) {
        super(nickName, MessageType.TILE_POSITION);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString(){
        return getNickName() + " sends position of tile " + getPosition().toString();
    }
}
