package it.polimi.ingsw.network.MessagesToClient;

import it.polimi.ingsw.network.eventMessages.EventMessage;

/**
 * <h1>Enumeration MessageToClientType</h1>
 * The enumeration MessageToClientType contains all the types of a {@link MessageToClient}.
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio, Francesco Maberino, Carlo Terzuoli
 * @version 1.0
 * @since 5/18/2023
 */
public enum MessageToClientType {

    // request messages type
    PING,
    LOGIN_RESPONSE,
    GAME_CREATION,
    JOIN_GAME_RESPONSE,
    PLAYER_JOINED_LOBBY_RESPONSE,
    RESUME_GAME_RESPONSE,
    WAIT_FOR_OTHER_PLAYERS,

    GAME_NAME_RESPONSE,
    CREATOR_LOGIN_RESPONSE,
    CLIENT_DISCONNECTION,
    WAIT_PLAYERS,
    ILLEGAL_POSITION,
    PLAYER_OFFLINE,
    KILL_GAME,

    // error messages types
    JOIN_GAME_ERROR,
    RESUME_GAME_ERROR,
    RELOAD_GAME_ERROR,
    NOT_ENOUGH_INSERTABLE_TILES,
    NOT_ENOUGH_INSERTABLE_TILES_IN_COLUMN,
    UPPER_BOUND_TILEPACK,
    GAME_SPECS,
    DISCONNECTION_RESPONSE

}
