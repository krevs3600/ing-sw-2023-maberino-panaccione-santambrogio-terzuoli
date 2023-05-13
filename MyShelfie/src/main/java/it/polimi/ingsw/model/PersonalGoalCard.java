package it.polimi.ingsw.model;
import it.polimi.ingsw.model.utils.TileType;

import java.io.Serializable;
import java.util.*;

/**
 * <h1>Class PersonalGoalCard</h1>
 * The class PersonalGoalCard represents the personal goal of a player to be achieved to gain extra scores
 * A personal goal basically consists of a specific configuration of tiles to have in the bookshelf
 *
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
     * @param configuration the map containing the specific configuration of item tiles to have in the bookshelf in order to achieve the personal goal
     * the keys are the positions of the item tiles, while the values are the types of item tiles
     */
    public PersonalGoalCard(HashMap<Integer,TileType> configuration, int path ){
        this.scoringItem = configuration;
        this.path = path;
    }

    public int getPath(){return path;}

    /**
     * This getter method gets the scoring item
     * @return Map<Position, TileType> It returns the map with the configuration of the personal goal
     */
    public Map<Integer, TileType> getScoringItem() {
        return scoringItem;
    }

    @Override
    public String toString() {
        String number = "";
        for(int i=0; i<5; i++){
            number = number.concat("   " + String.valueOf(i));
        }

        String rows = "";
        Set<Integer> keys = scoringItem.keySet();
        for(int i=0; i<29;i++){
            // new row
            if (i%5 == 0){
                rows = rows.concat("\n").concat(String.valueOf(5-i/5)+ " ");
            }

            if(keys.contains(i)){
                TileType tileType = scoringItem.get(i);
                rows = rows.concat(tileType.getColorBackground() + " " + tileType.getAbbreviation() + " " + "\033[0m ");
            }
            else{
                rows = rows.concat("    ");
            }


        }
        return number.concat("\n").concat(rows);

    }
   
}
