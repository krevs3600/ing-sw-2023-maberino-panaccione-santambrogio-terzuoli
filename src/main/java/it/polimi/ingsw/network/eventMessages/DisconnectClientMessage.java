package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.Client;
/**
 * <h1>Class DisconnectClientMessage</h1>
 * The class DisconnectClientMessage extends the abstract class EventMessage. This type of message is used signal to the {@link Server}
 * that it has to to disconnect the {@link Client}. It is used at the end of the game
 *
 * @author Carlo Terzuoli, Francesco Santambrogio
 * @version 1.0
 * @since 5/21/2023
 */
public class DisconnectClientMessage extends EventMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the Player to be disconnected
     */
    public DisconnectClientMessage(String nickname) {
        super(nickname, EventMessageType.DISCONNECT_CLIENT);
    }
}
