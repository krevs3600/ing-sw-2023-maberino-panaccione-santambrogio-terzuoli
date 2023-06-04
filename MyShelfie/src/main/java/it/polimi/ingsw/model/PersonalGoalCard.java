package it.polimi.ingsw.model;
import it.polimi.ingsw.model.utils.TileType;

import java.io.Serializable;
import java.util.*;

/**
 * <h1>Class PersonalGoalCard</h1>
 * The class PersonalGoalCard is a goal card unique to a {@link Player} during a game.
 * It can yield extra points to him if his {@link Bookshelf} satisfies the configuration on the personal goal card
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public class PersonalGoalCard extends GoalCard implements Serializable {
    private static final int DIMENSION=6;
    private final int[] scores = new int[]{1, 2, 4, 6, 9, 12};
    private final int path;
    private static final long serialVersionUID = 1L;

    private final HashMap<Integer, TileType> scoringItem;

    /**
     * Class constructor
     * @param configuration the map containing the specific configuration of {@link ItemTile} to have in the {@link Bookshelf}
     * the keys are the positions of the {@link ItemTile}s in the {@link Bookshelf}, while the values correspond to the type of {@link ItemTile}
     */
    public PersonalGoalCard(HashMap<Integer,TileType> configuration, int path){
        this.scoringItem = configuration;
        this.path = path;
    }

    public int getPath(){return path;}

    /**
     * Getter method for the scoring item
     * @return The map with the configuration of the {@link PersonalGoalCard}
     */
    public Map<Integer, TileType> getScoringItem() {
        return scoringItem;
    }

    /**
     * To String method
     * @return a String version of the {@link PersonalGoalCard}
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
        Set<Integer> keys = scoringItem.keySet();
        int j = 0;
        for(int i=0; i<30;i++){
            // new row
            if (i%5 == 0){
                j += 1;
                rows = rows.concat(String.valueOf(5-i/5)+ " ");
            }
            if(keys.contains(i)){
                TileType tileType = scoringItem.get(i);
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
