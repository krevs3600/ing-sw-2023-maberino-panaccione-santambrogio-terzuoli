package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ScoringToken;

import java.io.Serializable;
import java.util.Stack;

public class CommonGoalCardView implements Serializable {
    private final Stack<ScoringToken> stack;
    private final String toString;
    private static final long serialVersionUID = 1L;
    private final String getType;

    public CommonGoalCardView(CommonGoalCard commonGoalCard){
        this.stack = commonGoalCard.getStack();
        this.toString = commonGoalCard.toString();
        this.getType = commonGoalCard.getType();
    }


    @Override
    public String toString(){
        return this.toString;
    }

    public String getType() {return this.getType;}
}
