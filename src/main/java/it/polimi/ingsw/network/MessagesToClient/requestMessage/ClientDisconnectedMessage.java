package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

/**
 * <h1>Class ClientDisconnectedMessage</h1>
 * The class ClientDisconnectedMessage extends the abstract class RequestMessage. The purpose of this message is to
 * let the others know that the {@link Client} has been disconnected from the {@link Game}
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/18/2023
 */
public class ClientDisconnectedMessage extends RequestMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     */
    public ClientDisconnectedMessage(String nickname) {
        super(nickname, MessageToClientType.CLIENT_DISCONNECTION);
    }
}