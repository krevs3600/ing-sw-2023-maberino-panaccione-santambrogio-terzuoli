package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;

/**
 * <h1>Class GameCreationMessage</h1>
 * The class GameCreationMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * that a {@link Player} has requested to create a new game. In the request he/she must also specify the number of
 * players of the game
 *
 * @author Francesco Maberino, Francesco Santambrogio, Carlo Terzuoli
 * @version 1.0
 * @since 5/9/2023
 */
public class GameCreationMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int numOfPlayers;
    private final String gameName;
    public GameCreationMessage(String nickName, int numOfPlayers, String gameName) {
        super(nickName, EventMessageType.GAME_CREATION);
        this.numOfPlayers = numOfPlayers;
        this.gameName = gameName;
    }

    /**
     * Getter method
     * @return the number of players selected
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
     */
    @Override
    public String toString(){
        return getNickname() + " wants to create a game with " + getNumOfPlayers() + " players";
    }
}
