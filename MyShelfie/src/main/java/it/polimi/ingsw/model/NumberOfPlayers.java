package it.polimi.ingsw.model;


/**
 * <h1>Enumeration NumberOfPlayers</h1>
 * The enumeration NumberOfPlayers represents the number of players that can be two, three or four
 * it is useful for the characterization of some objects that change according to the number of players
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
