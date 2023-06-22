package it.polimi.ingsw.persistence;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;
import java.io.Serializable;

public record PersistentGame(GameController gameController) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
