package it.polimi.ingsw.network.MessagesToServer;

public enum MessageToServerType {

    // request messages type
    LOGIN_RESPONSE,
    GAME_CREATION,
    JOINGAME_RESPONSE,

    GAMENAME_RESPONSE, CREATOR_LOGIN_RESPONSE,

    // error messages types
    JOIN_GAME_ERROR;

}
