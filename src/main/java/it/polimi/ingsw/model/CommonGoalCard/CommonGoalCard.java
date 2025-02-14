package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.utils.NumberOfPlayers;

import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;

/**
 * <h1>Abstract Class CommonGoalCard</h1>
 * The class CommonGoalCard represents the common goal of a game to be achieved to gain extra scores
 * A Common goal basically consists of a specific configuration of tiles to have in the bookshelf
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public abstract class CommonGoalCard extends GoalCard  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final Stack<ScoringToken> stack = new Stack<>();

    /**
     * Class constructor: common to all subclasses
     * @param nop the number of players which affects the stack of scoring items upon a common goal card
     * @param roman the roman number on the back of a scoring item which defines on which of the two common goal cards the item is stacked
     */


    //@TODO: spostare in controller
    public void stackScoringTokens (NumberOfPlayers nop, RomanNumber roman) {
        if (nop.equals(NumberOfPlayers.TWO_PLAYERS)) {
            this.stack.push(new ScoringToken(roman, 4));
            this.stack.push(new ScoringToken(roman, 8));
        }
        if (nop.equals(NumberOfPlayers.THREE_PLAYERS)) {
            this.stack.push(new ScoringToken(roman, 4));
            this.stack.push(new ScoringToken(roman, 6));
            this.stack.push(new ScoringToken(roman, 8));
        }
        if (nop.equals(NumberOfPlayers.FOUR_PLAYERS)) {
            this.stack.push(new ScoringToken(roman, 2));
            this.stack.push(new ScoringToken(roman, 4));
            this.stack.push(new ScoringToken(roman, 6));
            this.stack.push(new ScoringToken(roman, 8));
        }

    }

    /**
     * This getter method gets the stack of the common goal card
     * @return Stack<ScoringToken> It returns the stack of scoring tokens
     */
    public Stack<ScoringToken> getStack (){
        return this.stack;
    }

    public abstract boolean toBeChecked (Bookshelf b);

    public abstract boolean checkPattern(Bookshelf b);

    public abstract String getType();
}
