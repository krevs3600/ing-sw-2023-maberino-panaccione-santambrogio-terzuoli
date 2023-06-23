package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCardDeck;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Class CommonGoalCardDeckView</h1>
 * This class is the immutable version of class CommonGoalCardDeck
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/07/2023
 */
public class CommonGoalCardDeckView implements Serializable {

    /**
     * List of immutable CommonGoalCards
     */
    private final List<CommonGoalCardView> commonGoalCardDeck;
    /**
     * UID version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for class CommonGoalCardDeckView
     * @param deck object to create immutable version
     */
    public CommonGoalCardDeckView (CommonGoalCardDeck deck) {
        commonGoalCardDeck = new ArrayList<>();
        for(CommonGoalCard card : deck.getDeck()){
            this.commonGoalCardDeck.add(new CommonGoalCardView(card));
        }
    }

    /**
     * Getter method for the deck's size
     * @return the deck's size
     */
    public int getSize() {
        return commonGoalCardDeck.size();
    }

    /**
     * Getter method for the list of CommonGoalCards in the deck
     * @return the a new list of CommonGoalCards of the deck
     */
    public List<CommonGoalCardView> getDeck () {
        return new ArrayList<>(this.commonGoalCardDeck);
    }
}
