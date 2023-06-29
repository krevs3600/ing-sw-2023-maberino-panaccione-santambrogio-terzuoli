package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class SwitchPhaseMessage</h1>
 * The class SwitchPhaseMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * a switch in the {@link GamePhase} happened
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/22/2023
 */
public class SwitchPhaseMessage extends EventMessage{

    private final long serialVersionUID = 1L;

    private final GamePhase gamePhase;

    /**
     * Class constructor
     * @param nickname of the {@link Player}
     * @param gamePhase
     */
    public SwitchPhaseMessage(String nickname, GamePhase gamePhase) {
        super(nickname, EventMessageType.SWITCH_PHASE);
        this.gamePhase = gamePhase;
    }

    /**
     * Getter method
     * @return the {@link GamePhase} of the game
     */
    public GamePhase getGamePhase (){ return this.gamePhase;}
}
