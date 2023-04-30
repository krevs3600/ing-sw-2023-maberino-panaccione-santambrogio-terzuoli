package it.polimi.ingsw.model;
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
public class PersonalGoalCard extends GoalCard{
    private static final int DIMENSION=6;
    private final int[] scores = new int[]{1, 2, 4, 6, 9, 12};

    private Map<Position,TileType> scoringItem = new HashMap<>();

    /**
     * Class constructor
     * @param configuration the map containing the specific configuration of item tiles to have in the bookshelf in order to achieve the personal goal
     * the keys are the positions of the item tiles, while the values are the types of item tiles
     */
    public PersonalGoalCard(HashMap<Position,TileType> configuration){
        this.scoringItem = configuration;
    }

    /**
     * This getter method gets the scoring item
     * @return Map<Position, TileType> It returns the map with the configuration of the personal goal
     */
    public Map<Position, TileType> getScoringItem() {
        return scoringItem;
    }
}
