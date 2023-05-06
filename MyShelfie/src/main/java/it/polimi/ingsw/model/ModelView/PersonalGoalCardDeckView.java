package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.PersonalGoalCardDeck;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

public class PersonalGoalCardDeckView extends Observable implements Observer {

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
