package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;


/**
 * <h1>Class CommonGoalCardDeck</h1>
 * The class CommonGoalCardDeck contains a list of common goal cards
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 3/28/2023
 */

public class CommonGoalCardDeck implements Drawable {
    private final List<CommonGoalCard> deck;

    /**
     * Class constructor
     */
    public CommonGoalCardDeck () {
        this.deck = new ArrayList<CommonGoalCard>();
    }

    /**
     * This method is used to draw a random common goal card from the deck
     * @return GoalCard the randomly drawn goal card
     */
    //TODO: draw method
    @Override
    public GoalCard draw() { return null;}

    /**
     * This getter method gets the deck of common goal cards
     * @return List<CommonGoalCard> It returns the deck
     */
    public List<CommonGoalCard> getDeck() {
        return deck;
    }
}