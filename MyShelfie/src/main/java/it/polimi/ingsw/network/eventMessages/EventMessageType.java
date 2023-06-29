package it.polimi.ingsw.network.eventMessages;

/**
 * <h1>Enumeration MessageType</h1>
 * The enumeration SpaceType contains all the types of a {@link EventMessage}.
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio, Francesco Maberino, Carlo Terzuoli
 * @version 1.0
 * @since 5/9/2023
 */
public enum EventMessageType {
    PING,
    NICKNAME,
    GAME_NAME,
    GAME_CREATION,
    GAME_CHOICE,
    TILE_POSITION,
    BOOKSHELF_COLUMN,
    END_TURN,
    BOARD,
    BOOKSHELF,
    JOIN_GAME_REQUEST,
    PLAYER_TURN,
    TILE_PACK,
    COMMON_GOAL_CARD,
    LAST_TURN,
    END_GAME,
    ITEM_TILE_INDEX,
    FILL_BOOKSHELF,
    SWITCH_PHASE,
    DISCONNECT_CLIENT,
    PICKING_TILES,
    PLACING_TILES,
    EXIT,
    EASTER_EGG,

    START_GAME,
    RESUME_GAME_REQUEST,
    RELOAD_GAME_REQUEST,
    // error
    GAME_SPECS, COLUMN_CHOICE, SECONDCOMMONGOAL, FIRSTCOMMONGOAL;



}
