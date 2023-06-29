package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;

/**
 * <h1>Class EndTurnMessage</h1>
 * The class EndTurnMessage extends the abstract class EventMessage. This type of message is used signal to the {@link Server}
 * that a {@link Player} has finished his/her turn
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/9/2023
 */
public class EndTurnMessage extends EventMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class Constructor
     * @param nickName of the {@link Player} who has finished the turn
     */
    public EndTurnMessage(String nickName) {
        super(nickName, EventMessageType.END_TURN);
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    public String toString(){
        return "" + getNickname() + " decided to start placing tiles";
    }
}
