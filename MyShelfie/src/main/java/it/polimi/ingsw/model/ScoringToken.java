package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * <h1>Class ScoringToken</h1>
 * The class ScoringToken represents the score obtained by the player that achieves a common goal card
 * on which the token is stacked
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 3/28/2023
 */
public class ScoringToken implements Serializable {

    private final RomanNumber romanNumber;
    private final int value;
    private static final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param rn the roman number on the back of the scoring item
     * @param v the value of the scoring item
     */
    public ScoringToken(RomanNumber rn, int v) {
        this.romanNumber = rn;
        this.value = v;
    }

    /**
     * This getter method gets the roman number of the scoring token
     * @return RomanNumber It returns the identifying roman number
     */
    public RomanNumber getRomanNumber() {
        return romanNumber;
    }

    /**
     * This getter method gets the value of the token
     * @return int It returns the integer value of the token
     */
    public int getValue() {
        return value;
    }
}
