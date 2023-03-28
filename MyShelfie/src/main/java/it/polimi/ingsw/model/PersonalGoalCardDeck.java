package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCardDeck implements Drawable {
    private List<PersonalGoalCard> deck;

    public CommonGoalCardDeck () {
        this.deck = new ArrayList<PersonalGoalCard>();
    }

    @Override
    public GoalCard draw() {}
}