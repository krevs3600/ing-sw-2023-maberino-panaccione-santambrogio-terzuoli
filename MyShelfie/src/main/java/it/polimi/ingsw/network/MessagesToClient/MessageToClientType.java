package it.polimi.ingsw.network.MessagesToClient;

public enum MessageToClientType {

    // request messages type
    LOGIN_RESPONSE,
    GAME_CREATION,
    JOIN_GAME_RESPONSE,
    PLAYER_JOINED_LOBBY_RESPONSE,

    GAME_NAME_RESPONSE,
    CREATOR_LOGIN_RESPONSE,

    WAIT_PLAYERS,
    ILLEGAL_POSITION, SCORE,
    PLAYER_OFFLINE,
    KILL_GAME,

    // error messages types
    JOIN_GAME_ERROR,
    NOT_ENOUGH_INSERTABLE_TILES,
    NOT_ENOUGH_INSERTABLE_TILES_IN_COLUMN,
    UPPER_BOUND_TILEPACK,
    GAME_SPECS;

}
