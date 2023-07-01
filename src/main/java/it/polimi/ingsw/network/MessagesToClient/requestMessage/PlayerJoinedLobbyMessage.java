package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;
/**
 * <h1>Class PlayerJoinedLobbyMessage</h1>
 * The class PlayerJoinedLobbyMessage extends the abstract class RequestMessage. This type of message is used by
 * the {@link Server} to tell a {@link Client} that he/she has been inserted in the lobby
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/25/2023
 */
public class PlayerJoinedLobbyMessage extends RequestMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickName of the {@link Client} whom to send the message to
     */
    public PlayerJoinedLobbyMessage(String nickName) {
        super(nickName, MessageToClientType.PLAYER_JOINED_LOBBY_RESPONSE);
    }
}
