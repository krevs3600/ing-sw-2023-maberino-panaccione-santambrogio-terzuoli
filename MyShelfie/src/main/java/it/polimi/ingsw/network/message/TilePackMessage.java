package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.ModelView.TilePackView;

public class TilePackMessage extends Message{

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
