package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class PersonalGoalCardDeck implements Drawable {
    private List<PersonalGoalCard> deck;

    public PersonalGoalCardDeck () {
        this.deck = new ArrayList<PersonalGoalCard>();
    }

    @Override
    public GoalCard draw() {
        return null;
    }
    
}