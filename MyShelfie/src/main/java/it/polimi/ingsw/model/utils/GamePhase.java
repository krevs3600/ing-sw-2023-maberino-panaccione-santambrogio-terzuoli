package it.polimi.ingsw.model.utils;
/**
 * <h1>Interface GamePhase</h1>
 * GamePhase is the enumeration that contains all phases that happen during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio, Francesco Maberino, Carlo Terzuoli
 * @version 1.0
 * @since 3/28/2023
 */

public enum GamePhase {
    INIT_GAME,
    INIT_TURN,
    PICKING_TILES,
    COLUMN_CHOICE,
    PLACING_TILES,
    WAITING,
    @Deprecated
    END_TURN,
    @Deprecated
    END_GAME;
}