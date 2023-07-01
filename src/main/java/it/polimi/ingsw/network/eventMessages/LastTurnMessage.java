package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Bookshelf;

/**
 * <h1>Class LastTurnMessage</h1>
 * The class LastTurnMessage extends the abstract class EventMessage. This type of message is used signal to the {@link Server}
 * that the {@link Player} who has finished his/her turn, has completed the {@link Bookshelf}. Thus the {@link Server} is reminded
 * the present one will be the last turn of the game
 * will be
 *
 * @author Carlo Terzuoli
 * @version 1.0
 * @since 5/20/2023
 */
public class LastTurnMessage extends EventMessage{
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickName of the {@link Player} who has finished the turn
     */
    public LastTurnMessage(String nickName) {
        super(nickName, EventMessageType.LAST_TURN);
    }
}
