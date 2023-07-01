package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Server;

/**
 * <h1>Class WaitingResponseMessage</h1>
 * The class WaitingResponseMessage extends the abstract class RequestMessage. This message is sent by the {@link Server}
 * the {@link Client}s who logged in to a {@link Game}, which still needs one or more {@link Player}s
 * to log in for the {@link Game} to start. In this case the {@link Server} tells already logged in {@link Player}s
 * to wait for the others
 *
 * @author Francesco Santambrogio, Carlo terzuoli
 * @version 1.0
 * @since 6/27/2023
 */
public class WaitingResponseMessage extends RequestMessage{
    private final long serialVersionUID = 1L;
    private final int missingPlayers;

    /**
     * Class contructor
     * @param nickname of the {@link Client} whom to send the message to
     * @param missingPlayers we are still waiting to get logged into the {@link Game} to get started
     */
    public WaitingResponseMessage(String nickname, int missingPlayers) {
        super(nickname, MessageToClientType.WAIT_PLAYERS);
        this.missingPlayers = missingPlayers;
    }

    /**
     * Getter method
     * @return the players we are still waiting to get logged into the {@link Game} to get started
     */
    public int getMissingPlayers() {
        return missingPlayers;
    }
}
