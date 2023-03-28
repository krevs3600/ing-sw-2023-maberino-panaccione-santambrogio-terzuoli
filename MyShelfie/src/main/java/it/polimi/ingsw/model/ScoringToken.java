package it.polimi.ingsw.model;

public class ScoringToken {

    private int romanNumber;
    private int value;

    public ScoringToken(int rn, int v) {
        this.romanNumber = rn;
        this.value = v;
    }

    public int getRomanNumber() {
        return romanNumber;
    }

    public int getValue() {
        return value;
    }
}
