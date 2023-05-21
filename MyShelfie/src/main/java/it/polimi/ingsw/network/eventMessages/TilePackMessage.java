package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.TilePackView;

public class TilePackMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private TilePackView tilePackView;
    public TilePackMessage(String nickName, TilePackView tilePackView) {
        super(nickName, EventMessageType.TILE_PACK);
        this.tilePackView = tilePackView;
    }

    public TilePackView getTilePackView(){
        return this.tilePackView;
    }

    public String toString(){
        return getNickname() + "'s " + "\n" + getTilePackView().toString();
    }


}
