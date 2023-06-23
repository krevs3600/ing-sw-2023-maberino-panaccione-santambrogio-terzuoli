package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.view.cli.ColorCLI;

import java.io.Serial;
import java.io.Serializable;

/**
 * <h1>Class ScoringToken</h1>
 * The class ScoringToken represents the amount of points that gets added to a Player's score, when it is achieved
 * the {@link CommonGoalCard} on which the token is stacked
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 3/28/2023
 */
public class ScoringToken implements Serializable {

    private final RomanNumber romanNumber;
    private final int value;
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Class constructor:
     * this method initializes a {@link ScoringToken} thanks to given parameters
     * @param rn the {@link ScoringToken#romanNumber} on the back of the scoring item
     * @param v the {@link ScoringToken#value} in points of the scoring item
     */
    public ScoringToken(RomanNumber rn, int v) {
        this.romanNumber = rn;
        this.value = v;
    }

    /**
     * Getter method
     * @return the {@link ScoringToken#romanNumber} of the token
     */
    public RomanNumber getRomanNumber() {
        return romanNumber;
    }

    /**
     * Getter method
     * @return the {@code int} {@link ScoringToken#value} corresponding to the token
     */
    public int getValue() {
        return value;
    }

    public String toString(){
        return "[" + ColorCLI.RED.getCode() + " " + String.valueOf(getValue()) + " " + ColorCLI.RESET.getCode() + "]";
    }
}
