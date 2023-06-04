package it.polimi.ingsw.model;

/**
 * <h1>Enumeration PlayerStatus</h1>
 * The enumeration PlayerStatus contains all the statuses that a {@link Player} can assume during his turn
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 4/30/2023
 */
public enum PlayerStatus {
    INACTIVE,
    PICKING_TILES,
    @Deprecated
    INSERTING_TILES,
}
