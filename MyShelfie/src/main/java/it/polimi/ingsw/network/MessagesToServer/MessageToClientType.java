package it.polimi.ingsw.network.MessagesToServer;

public enum MessageToClientType {

    // request messages type
    LOGIN_RESPONSE,
    GAME_CREATION,
    JOIN_GAME_RESPONSE,
    PLAYER_JOINED_LOBBY_RESPONSE,

    GAME_NAME_RESPONSE,
    CREATOR_LOGIN_RESPONSE,

    // error messages types
    JOIN_GAME_ERROR,

    WAIT_PLAYERS,
    ILLEGAL_POSITION, SCORE;

}
