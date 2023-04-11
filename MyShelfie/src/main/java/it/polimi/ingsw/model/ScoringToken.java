package it.polimi.ingsw.model;

public class ScoringToken {

    private final RomanNumber romanNumber;
    private final int value;

    public ScoringToken(RomanNumber rn, int v) {
        this.romanNumber = rn;
        this.value = v;
    }

    public RomanNumber getRomanNumber() {
        return romanNumber;
    }

    public int getValue() {
        return value;
    }
}
