package it.polimi.ingsw.network.MessagesToServer;

public enum MessageToClientType {

    // request messages type
    LOGIN_RESPONSE,
    GAME_CREATION,
    JOINGAME_RESPONSE,

    GAMENAME_RESPONSE,
    CREATOR_LOGIN_RESPONSE,

    // error messages types
    JOIN_GAME_ERROR,

    WAIT_PLAYERS;

}
