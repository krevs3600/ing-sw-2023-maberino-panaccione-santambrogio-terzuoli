package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.PersonalGoalCardDeck;


import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PersonalGoalCardDeckView implements Serializable {

    private final List<PersonalGoalCard> personalGoalCardDeck;
    @Serial
    private static final long serialVersionUID = 1L;

    public PersonalGoalCardDeckView (PersonalGoalCardDeck personalGoalCardDeck) {
        this.personalGoalCardDeck = personalGoalCardDeck.getDeck();
    }

    public int getSize () {
        return personalGoalCardDeck.size();
    }

    public List<PersonalGoalCard> getDeck () {
        return this.personalGoalCardDeck;
    }
}
