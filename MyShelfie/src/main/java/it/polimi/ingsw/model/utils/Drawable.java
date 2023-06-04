package it.polimi.ingsw.model.utils;

import it.polimi.ingsw.model.GoalCard;

import java.io.Serial;
import java.io.Serializable;

/**
 * <h1>Interface Drawable</h1>
 * Drawable is the interface implemented by all the objects from which goal cards can be drawn, i.e. decks
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 3/28/2023
 */

public interface Drawable {
    public GoalCard draw ();
}
