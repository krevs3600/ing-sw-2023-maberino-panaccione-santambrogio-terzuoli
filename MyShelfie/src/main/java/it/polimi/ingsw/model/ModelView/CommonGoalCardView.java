package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ScoringToken;

import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;

public class CommonGoalCardView implements Serializable {
    private final Stack<ScoringToken> stack;
    private final String toString;
    @Serial
    private static final long serialVersionUID = 1L;
    private final String getType;

    public CommonGoalCardView(CommonGoalCard commonGoalCard){
        Stack<ScoringToken> temp;
        this.toString = commonGoalCard.toString();
        this.getType = commonGoalCard.getType();
        temp =  (Stack<ScoringToken>) commonGoalCard.getStack().clone();
        this.stack = temp;
    }


    @Override
    public String toString(){
        return this.toString;
    }

    public String getType() {return this.getType;}

    public Stack<ScoringToken> getStack() {
        return this.stack;
    }
}
