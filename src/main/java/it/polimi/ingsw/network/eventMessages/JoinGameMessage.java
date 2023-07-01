package it.polimi.ingsw.network.eventMessages;


import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;

/**
 * <h1>Class JoinGameMessage</h1>
 * The class JoinGameMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * that a {@link Player} has requested selected the option to join a game from the menu in the View. Such {@link Player}
 * will be added to the lobby
 *
 * @author Francesca Pia Panaccione
 * @version 1.0
 * @since 5/18/2023
 */
public class JoinGameMessage extends EventMessage{
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     */
    public JoinGameMessage(String nickName) {
        super(nickName, EventMessageType.JOIN_GAME_REQUEST);
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    public String toString(){
        return getNickname() + " asks to join a game in the lobby";
    }


}
