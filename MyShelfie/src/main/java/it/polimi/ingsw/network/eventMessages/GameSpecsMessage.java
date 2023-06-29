package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.utils.NumberOfPlayers;

/**
 * <h1>Class GameSpecsMessage</h1>
 * The class GameSpecsMessage extends the abstract class EventMessage. With this type of message the {@link Player} can signal to the {@link Server}
 * the name of the game he/she wants create and can specify the {@link NumberOfPlayers} for the game.
 *
 * @author Francesco Maberino
 * @version 1.0
 * @since 5/24/2023
 */
public class GameSpecsMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int numOfPlayers;
    private final String gameName;

    /**
     * Class constructor
     * @param nickName of the {@link Player} creating the game
     * @param gameName name of the game
     * @param numOfPlayers number of players for the game
     */
    public GameSpecsMessage(String nickName, String gameName, int numOfPlayers) {
        super(nickName, EventMessageType.GAME_SPECS);
        this.numOfPlayers = numOfPlayers;
        this.gameName = gameName;
    }

    /**
     * Getter method
     * @return {@link NumberOfPlayers}
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * Getter method
     * @return the name of the game
     */
    public String getGameName () { return gameName;}

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     *  */
    public String toString(){
        return getNickname() + " wants to create a game with " + String.valueOf(getNumOfPlayers()) + " players";
    }
}
