package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.PersonalGoalCardDeck;


import java.util.List;

public class PersonalGoalCardDeckView {

    private final PersonalGoalCardDeck personalGoalCardDeck;

    public PersonalGoalCardDeckView (PersonalGoalCardDeck personalGoalCardDeck) {
        this.personalGoalCardDeck = personalGoalCardDeck;
    }

    public int getSize () {
        return personalGoalCardDeck.getSize();
    }

    public List<PersonalGoalCard> getDeck () {
        return personalGoalCardDeck.getDeck();
    }
}
