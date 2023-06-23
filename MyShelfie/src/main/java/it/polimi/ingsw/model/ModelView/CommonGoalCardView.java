package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ScoringToken;

import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;

/**
 * <h1>Class CommonGoalCardView</h1>
 * This class is the immutable version of class CommonGoalCard
 *
 * @author Carlo Terzuoli
 * @version 1.0
 * @since 5/09/2023
 */
public class CommonGoalCardView implements Serializable {
    /**
     * Stack of ScoringTokens
     */
    private final Stack<ScoringToken> stack;
    /**
     * Brief description of the immutable CommonGoalCard
     */
    private final String description;
    /**
     * UID version
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Type of CommonGoalCard
     */
    private final String getType;

    /**
     * Constructor for class CommonGoalCardView
     * @param commonGoalCard object to create immutable version
     */
    public CommonGoalCardView(CommonGoalCard commonGoalCard){
        Stack<ScoringToken> temp;
        this.description = commonGoalCard.toString();
        this.getType = commonGoalCard.getType();
        temp =  (Stack<ScoringToken>) commonGoalCard.getStack().clone();
        this.stack = temp;
    }

    /**
     * Getter method for the CommonGoalCard's type
     * @return the CommonGoalCard's type
     */
    public String getType() {return this.getType;}

    /**
     * Getter method for the CommonGoalCard's ScoringToken stack
     * @return the ScoringToken stack
     */
    public Stack<ScoringToken> getStack() {
        return this.stack;
    }

    /**
     * Getter method for the description
     * @return the description
     */
    @Override
    public String toString(){
        return this.description;
    }
}
