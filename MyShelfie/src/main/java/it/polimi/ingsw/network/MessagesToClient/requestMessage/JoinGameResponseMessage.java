package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;

import java.util.Set;

/**
 * <h1>Class JoinGameResponseMessage</h1>
 * The class JoinGameResponseMessage extends the abstract class RequestMessage. The purpose of this message is for the {@link Server}
 * respond to a {@link Client} who has tried to join a new {@link Game}, responding to him/her by providing a list of the names of
 * the available {@link Game}
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 5/18/2023
 */
public class JoinGameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final Set<String> AvailableGamesInLobby;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     * @param availableGamesInLobby list containing the names of the available {@link Game}s in the lobby
     */
    public JoinGameResponseMessage(String nickname,Set<String> availableGamesInLobby) {
        super(nickname, MessageToClientType.JOIN_GAME_RESPONSE);
        this.AvailableGamesInLobby=availableGamesInLobby;
    }

    /**
     * Getter method
     * @return a list containing all the available {@link Game}s in the lobby
     */
    public Set<String> getAvailableGamesInLobby(){
        return this.AvailableGamesInLobby;
    }
}
