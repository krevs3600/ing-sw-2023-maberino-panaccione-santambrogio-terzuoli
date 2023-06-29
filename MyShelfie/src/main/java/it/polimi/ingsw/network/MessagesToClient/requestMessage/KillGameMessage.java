package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;
/**
 * <h1>Class KillGameMessage</h1>
 * The class KillGameMessage extends the abstract class RequestMessage. Message the {@link Server} sends in order to kill
 * the {@link Game}
 *
 * @author Carlo Terzuoli
 * @version 1.0
 * @since 5/28/2023
 */
public class KillGameMessage extends RequestMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     */
    public KillGameMessage(String nickname) { super(nickname, MessageToClientType.KILL_GAME);}
}
