package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.TilePack;


import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Class TilePackView</h1>
 * This class is the immutable version of class TilePack
 *
 * @author Carlo Terzuoli
 * @version 1.0
 * @since 5/07/2023
 */
public class TilePackView implements Serializable {
    /**
     * List of ItemTile in the TilePack
     */
    private final List<ItemTile> tilePack;
    /**
     * UID version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for TilePackView
     * @param tilePack list of tiles contained in the TilePack
     */
    public TilePackView (TilePack tilePack) {
        this.tilePack = tilePack.getTiles();
    }

    /**
     * Getter method for the list of tiles
     * @return the list of tiles
     */
    public List<ItemTile> getTiles () {
        return this.tilePack;
    }

    /**
     * Override of method to string to print the TilePack
     * @return the string representation of the TilePack
     */
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
    /**
     * Method useful for GameView string representation. Each line of the TilePack is indexed by an integer
     * to print the TilePack line by line where is needed.
     * @return map HashMap indexing line's number with string's line
     */
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
