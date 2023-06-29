package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class WaitingForOtherPlayersMessage</h1>
 * The class WaitingForOtherPlayersMessage extends the abstract class RequestMessage. This message is sent by the {@link Server}
 * the {@link Client}s who logged in to a {@link Game}. In this case such {@link Client} is the last one left, as
 * all the others have altrady disconnected from the {@link Game}, and is waiting for them to resume the game to restart
 * playing
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/27/2023
 */
public class WaitingForOtherPlayersMessage extends RequestMessage {

    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     */
    public WaitingForOtherPlayersMessage(String nickname) {
        super(nickname, MessageToClientType.WAIT_FOR_OTHER_PLAYERS);
    }
}
