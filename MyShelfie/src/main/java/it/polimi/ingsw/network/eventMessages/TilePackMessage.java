package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.TilePackView;
import it.polimi.ingsw.network.EventMessage;

public class TilePackMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private TilePackView tilePackView;
    public TilePackMessage(String nickName, TilePackView tilePackView) {
        super(nickName, MessageType.TILE_PACK);
        this.tilePackView = tilePackView;
    }

    public TilePackView getTilePackView(){
        return this.tilePackView;
    }

    public String toString(){
        return getNickName() + "'s " + "\n" + getTilePackView().toString();
    }


}
