package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class PersonalGoalCard {
    private static final int DIM=6;
    private final int[] scores=new int[]{1, 2, 4, 6, 9, 12};

    private Map<Position,TileType> scoringItem=new HashMap<>();

    public PersonalGoalCard(HashMap<Position,TileType> m){
        this.scoringItem=m;

    }

    public Map<Position, TileType> getScoringItem() {
        return scoringItem;
    }
}
