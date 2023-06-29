package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;
/**
 * <h1>Class PingToClientMessage</h1>
 * The class PingToClientMessage extends the abstract class RequestMessage. This type of message is used by the {@link Server}
 * to check if the {@link Client} is still playing the game and has not disconnected
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/25/2023
 */
public class PingToClientMessage extends RequestMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     */
    public PingToClientMessage(String nickname) {
        super(nickname, MessageToClientType.PING);
    }
}
