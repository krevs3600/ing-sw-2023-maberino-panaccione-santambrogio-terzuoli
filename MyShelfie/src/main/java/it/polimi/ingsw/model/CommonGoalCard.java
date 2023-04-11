package it.polimi.ingsw.model;

import java.util.Stack;

public abstract class CommonGoalCard {

    private final Stack<ScoringToken> stack = new Stack<>();

    public CommonGoalCard (NumberOfPlayers nop, RomanNumber roman) {
        if (nop.equals(nop.TWOPLAYERS)) {
            this.stack.push(new ScoringToken(roman, 4));
            this.stack.push(new ScoringToken(roman, 8));
        }
        if (nop.equals(nop.THREEPLAYERS)) {
            this.stack.push(new ScoringToken(roman, 4));
            this.stack.push(new ScoringToken(roman, 6));
            this.stack.push(new ScoringToken(roman, 8));
        }
        if (nop.equals(nop.FOURPLAYERS)) {
            this.stack.push(new ScoringToken(roman, 2));
            this.stack.push(new ScoringToken(roman, 4));
            this.stack.push(new ScoringToken(roman, 6));
            this.stack.push(new ScoringToken(roman, 8));
        }

    }


    public ScoringToken pop() {
        return this.stack.pop();
    }

    public Stack<ScoringToken> getStack (){
        return this.stack;
    }

    public abstract boolean toBeChecked (Bookshelf b);

    public abstract boolean CheckPattern (Bookshelf b);
}
