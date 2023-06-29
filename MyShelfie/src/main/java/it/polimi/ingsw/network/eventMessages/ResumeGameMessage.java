package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;

/**
 * <h1>Class ResumeGameMessage</h1>
 * The class ResumeGameMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * that a {@link Player} is trying to resume an already existing game, that he/she was previously playing
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/27/2023
 */
public class ResumeGameMessage extends EventMessage{
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickName of the {@link Player} trying to resume the game
     */
    public ResumeGameMessage(String nickName) {
        super(nickName, EventMessageType.RESUME_GAME_REQUEST);
    }
    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    public String toString(){
        return getNickname() + " asks to resume a saved game";
    }
}