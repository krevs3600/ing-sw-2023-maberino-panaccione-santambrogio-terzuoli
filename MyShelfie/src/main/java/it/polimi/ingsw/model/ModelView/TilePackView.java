package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.TilePack;


import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class TilePackView implements Serializable {

    private final List<ItemTile> tilePack;
    @Serial
    private static final long serialVersionUID = 1L;

    public TilePackView (TilePack tilePack) {
        this.tilePack = tilePack.getTiles();
    }

    public List<ItemTile> getTiles () {
        return this.tilePack;
    }

    @Override
    public String toString(){
        String tilePackTxt = "";
        HashMap<Integer, String> tilePack = toDict();
        for (int i = 0; i<tilePack.size(); i++){
            tilePackTxt = tilePackTxt.concat(tilePack.get(i));
            if (i < tilePack.size()-1) {
                tilePackTxt = tilePackTxt.concat("\n");
            }
        }
        return tilePackTxt;
    }

    public HashMap<Integer, String> toDict(){
        HashMap<Integer, String> map = new HashMap<>();
        // first line
        String positions = "  ";
        for (int i=0; i<tilePack.size();i++){
            positions = positions.concat(String.valueOf(i)).concat("   ");
        }
        map.put(0, positions);
        // second line
        String tilePackCli = "";
        for (int i=0; i<tilePack.size();i++){
            if (i==0) {
                tilePackCli = tilePackCli.concat("[");
            }
            tilePackCli = tilePackCli.concat(getTiles().size() > i ? this.getTiles().get(i).toString() : "   ");
            if (i < tilePack.size()-1){
                tilePackCli = tilePackCli.concat(",");
            } else {
                tilePackCli = tilePackCli.concat("]");
            }
        }
        map.put(1, tilePackCli);
        return map;
    }
}
