package it.polimi.ingsw.network.MessagesToClient.requestMessage;


import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;
import java.util.Set;

/**
 * <h1>Class ReloadGameResponseMessage</h1>
 * The class ReloadGameResponseMessage extends the abstract class RequestMessage. This message is sent by the {@link Server}
 * a {@link Client} who tried to reload a {@link Game}
 *
 * @author Francesca Pia Panaccione
 * @version 1.0
 * @since 6/27/2023
 */
public class ReloadGameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     */
    public ReloadGameResponseMessage(String nickname) {
        super(nickname, MessageToClientType.JOIN_GAME_RESPONSE);
    }

}