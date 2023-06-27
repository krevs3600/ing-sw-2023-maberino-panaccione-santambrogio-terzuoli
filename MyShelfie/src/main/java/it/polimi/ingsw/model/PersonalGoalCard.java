package it.polimi.ingsw.model;
import it.polimi.ingsw.model.utils.TileType;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * <h1>Class PersonalGoalCard</h1>
 * The class PersonalGoalCard is a {@link GoalCard} unique to a {@link Player} during a game.
 * It can yield extra points to him if his/her {@link Bookshelf} satisfies the configuration on the {@link PersonalGoalCard}
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public class PersonalGoalCard extends GoalCard implements Serializable {
    private static final int DIMENSION = 6;
    private final int[] scores = new int[]{1, 2, 4, 6, 9, 12};
    private final int path;
    @Serial
    private static final long serialVersionUID = 1L;

    private final HashMap<Integer, TileType> scoringItemTiles;

    /**
     * Class constructor
     * @param configuration the map containing the specific configuration of {@link ItemTile} to have in the {@link Bookshelf}
     * the keys are the positions of the {@link ItemTile}s in the {@link Bookshelf}, while the values correspond to the type of {@link ItemTile}
     */
    public PersonalGoalCard(HashMap<Integer,TileType> configuration, int path){
        this.scoringItemTiles = configuration;
        this.path = path;
    }

    /**
     * Getter method
     * @return the {@link PersonalGoalCard#path}
     */
    public int getPath(){return path;}

    /**
     * Getter method
     * @return the {@link PersonalGoalCard#scoringItemTiles}, namely the map with the configuration of specific {@link TileType}s in specific positions
     */
    public Map<Integer, TileType> getScoringItemTiles() {
        return scoringItemTiles;
    }

    /**
     * To String method
     * @return a {@code String} version of the {@link PersonalGoalCard}
     */
    @Override
    public String toString() {
        String card = "";
        HashMap<Integer, String> cardMap = toDict();
        for(int i = 0; i< cardMap.size(); i++){
            card = card.concat(cardMap.get(i));
            if (i< cardMap.size()-1){
                card = card.concat("\n");
            }
        }
        return card;

    }
    /**
     * To Dictionary method
     * @return a dictionary version of the {@link PersonalGoalCard}
     */
    public HashMap<Integer, String> toDict(){
        HashMap<Integer, String> map = new HashMap<>();
        String number = "";
        for(int i=0; i<5; i++){
            number = number.concat("   " + String.valueOf(i));
        }
        map.put(0, number.concat("    "));

        String rows = "";
        Set<Integer> keys = scoringItemTiles.keySet();
        int j = 0;
        for(int i=0; i<30;i++){
            // new row
            if (i%5 == 0){
                j += 1;
                rows = rows.concat(String.valueOf(5-i/5)+ " ");
            }
            if(keys.contains(i)){
                TileType tileType = scoringItemTiles.get(i);
                rows = rows.concat(tileType.getColorBackground() + " " + tileType.getAbbreviation() + " " + "\033[0m ");
            }
            else{
                rows = rows.concat("    ");
            }
            if (i%5 == 4) {
                map.put(j, rows.concat("  "));
                rows = "";
            }
        }
        return map;
    }
}
