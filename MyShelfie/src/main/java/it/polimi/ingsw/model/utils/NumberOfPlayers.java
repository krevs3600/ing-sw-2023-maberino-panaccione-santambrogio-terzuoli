package it.polimi.ingsw.model.utils;


import it.polimi.ingsw.model.LivingRoomBoard;

/**
 * <h1>Enumeration NumberOfPlayers</h1>
 * NumberOfPlayers is the enumeration containing all the possible configurations of number of players that can play a game:
 * two, three or four.
 * It is useful for the characterization of some objects that change according to the number of players, e.g.: {@link LivingRoomBoard}
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public enum NumberOfPlayers {
    TWO_PLAYERS(2),
    THREE_PLAYERS(3),
    FOUR_PLAYERS(4);
    private final int value;

    NumberOfPlayers(int numberOfPlayers) {
        this.value = numberOfPlayers;
    }

    public int getValue() {
        return value;
    }
}
