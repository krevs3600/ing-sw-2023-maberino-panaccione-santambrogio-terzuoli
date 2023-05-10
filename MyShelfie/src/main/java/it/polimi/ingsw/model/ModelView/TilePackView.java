package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.TilePack;


import java.util.List;

public class TilePackView{

    private final TilePack tilePack;

    public TilePackView (TilePack tilePack) {
        this.tilePack = tilePack;
    }

    public List<ItemTile> getTiles () {
        return tilePack.getTiles();
    }

    @Override
    public String toString(){
        String tilePack = "";
        String position = "  ";
        for (int i=0; i<3; i++){
            position = position.concat(String.valueOf(i)).concat("   ");
        }
        tilePack = tilePack.concat("[");
        for (int i=0; i<3;i++){
            tilePack = tilePack.concat(getTiles().size() > i ? this.getTiles().get(i).toString() : "   ");
            if (i==2){
                tilePack = tilePack.concat("]");
            } else {
                tilePack = tilePack.concat(",");
            }
        }
        return position.concat("\n").concat(tilePack);
    }
}
