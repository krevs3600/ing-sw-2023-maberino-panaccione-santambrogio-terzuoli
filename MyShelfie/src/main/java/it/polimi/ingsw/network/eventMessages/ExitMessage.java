package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;

/**
 * <h1>Class ExitMessage</h1>
 * The class ExitMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * that a {@link Player} who had previously entered the menu, has decided to log off the game
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/27/2023
 */
public class ExitMessage extends EventMessage{
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Player} from which originated the message
     */
    public ExitMessage(String nickname) {
        super(nickname, EventMessageType.EXIT);
    }


    }

