package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Player;
/**
 * <h1>Class StartGameMessage</h1>
 * The class StartGameMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * that all {@link Player}s have successfully joined the game, thus the game can start
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/22/2023
 */
public class StartGameMessage extends EventMessage{
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     */
    public StartGameMessage(String nickName) {
        super(nickName, EventMessageType.START_GAME);
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    public String toString(){
        return getNickname() + " asks to start a new game";
    }

}


