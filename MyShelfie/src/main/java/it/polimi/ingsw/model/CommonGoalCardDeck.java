package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCardDeck implements Drawable {
    private List<CommonGoalCard> deck;

    public CommonGoalCardDeck () {
        this.deck = new ArrayList<CommonGoalCard>();
    }

    @Override
    public GoalCard draw() {}
}
