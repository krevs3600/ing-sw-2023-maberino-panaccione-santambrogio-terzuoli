package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;

/**
 * <h1>Class PlayerOfflineMessage</h1>
 * The class PlayerOfflineMessage extends the abstract class RequestMessage. This message is used by the {@link Server}
 * the {@link Client}s taking part in a {@link Game} that one of them is offline and got disconnected
 * he/she chose is valid.
 *
 * @author Carlo Terzuoli
 * @version 1.0
 * @since 5/21/2023
 */
public class PlayerOfflineMessage extends RequestMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     */
    public PlayerOfflineMessage(String nickname) {
        super(nickname, MessageToClientType.PLAYER_OFFLINE);
    }
}
