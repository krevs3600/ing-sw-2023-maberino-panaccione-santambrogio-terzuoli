package it.polimi.ingsw.persistence;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;
import java.io.Serializable;
/**
 * <h1>Class PersistentGame</h1>
 * The class PersistentGame is an immutable class that contains the {@link GameController} that has to be saved
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/27/2023
 */
public record PersistentGame(GameController gameController) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
