package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.TilePack;


import java.io.Serializable;
import java.util.List;

public class TilePackView implements Serializable {

    private final List<ItemTile> tilePack;
    private static final long serialVersionUID = 1L;

    public TilePackView (TilePack tilePack) {
        this.tilePack = tilePack.getTiles();
    }

    public List<ItemTile> getTiles () {
        return this.tilePack;
    }

    @Override
    public String toString(){
        String tilePackCli = "";
        String position = "  ";
        for (int i=0; i<tilePack.size(); i++){
            position = position.concat(String.valueOf(i)).concat("   ");
        }

        tilePackCli = tilePackCli.concat("[");
        for (int i=0; i<tilePack.size();i++){
            tilePackCli = tilePackCli.concat(getTiles().size() > i ? this.getTiles().get(i).toString() : "   ");
            if (i < tilePack.size()-1){
                tilePackCli = tilePackCli.concat(",");
            } else {
                tilePackCli = tilePackCli.concat("]");
            }
        }
        return position.concat("\n").concat(tilePackCli);
    }
}
