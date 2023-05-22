package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.utils.GamePhase;

public class SwitchPhaseMessage extends EventMessage{

    private final long serialVersionUID = 1L;

    GamePhase gamePhase;

    public SwitchPhaseMessage(String nickname, GamePhase gamePhase) {
        super(nickname, EventMessageType.SWITCH_PHASE);
        gamePhase = gamePhase;
    }

    public GamePhase getGamePhase (){ return this.gamePhase;}
}
