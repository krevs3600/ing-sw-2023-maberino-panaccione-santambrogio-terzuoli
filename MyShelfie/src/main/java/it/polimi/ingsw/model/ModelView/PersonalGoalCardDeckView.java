package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.PersonalGoalCardDeck;


import java.io.Serial;
import java.io.Serializable;
import java.util.List;
/**
 * <h1>Class PersonalGoalCardDeckView</h1>
 * This class is the immutable version of class PersonalGoalCardDeck
 *
 * @author Francesca Santambrogio
 * @version 1.0
 * @since 5/07/2023
 */
public class PersonalGoalCardDeckView implements Serializable {

    /**
     * List of PersonalGoalCards (that are already immutable)
     */
    private final List<PersonalGoalCard> personalGoalCardDeck;
    /**
     * UID version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for class PersonalGoalCardDeckView
     * @param personalGoalCardDeck object to create immutable version
     */
    public PersonalGoalCardDeckView (PersonalGoalCardDeck personalGoalCardDeck) {
        this.personalGoalCardDeck = personalGoalCardDeck.getDeck();
    }

    /**
     * Getter method for the size of the deck
     * @return the size of the deck
     */
    public int getSize () {
        return personalGoalCardDeck.size();
    }

    /**
     * Getter method for the list of PersonalGoalCards
     * @return the list of PersonalGoalCards
     */
    public List<PersonalGoalCard> getDeck () {
        return this.personalGoalCardDeck;
    }
}
