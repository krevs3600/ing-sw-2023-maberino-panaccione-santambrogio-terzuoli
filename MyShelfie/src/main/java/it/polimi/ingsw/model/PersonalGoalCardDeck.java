package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Class PersonalGoalCardDeck</h1>
 * The class PersonalGoalCardDeck contains a list of personal goal cards
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 3/28/2023
 */

public class PersonalGoalCardDeck implements Drawable {
    private final List<PersonalGoalCard> deck;

    /**
     * Class constructor
     */
    public PersonalGoalCardDeck () {
        this.deck = new ArrayList<PersonalGoalCard>();
    }

    /**
     * This method is used to draw a random personal goal card from the deck
     * @return GoalCard the randomly drawn goal card
     */
    //TODO: draw method
    @Override
    public GoalCard draw() { return null;}

    /**
     * This getter method gets the deck of personal goal cards
     * @return List<PersonalGoalCard> It returns the deck
     */
    public List<PersonalGoalCard> getDeck() {
        return deck;
    }
}