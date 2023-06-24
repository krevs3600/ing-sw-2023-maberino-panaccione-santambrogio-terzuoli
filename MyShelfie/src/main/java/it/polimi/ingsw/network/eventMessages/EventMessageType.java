package it.polimi.ingsw.network.eventMessages;

public enum EventMessageType {
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

    START_GAME,
    RESUME_GAME_REQUEST,
    // error
    GAME_SPECS, COLUMN_CHOICE;



}
