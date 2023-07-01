package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Client;

/**
 * <h1>Class PingMessage</h1>
 * The class PingMessage extends the abstract class EventMessage. This type of message is used by a
 *   {@link Client} to notify that it is still playing the game and has not disconnected
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/25/2023
 */
public class PingMessage extends EventMessage {

    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickName of the {@link Player} that is being checked
     */
    public PingMessage(String nickName) {
        super(nickName, EventMessageType.PING);
    }
    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    public String toString(){
        return getNickname() + " is sending a ping";
    }
}
