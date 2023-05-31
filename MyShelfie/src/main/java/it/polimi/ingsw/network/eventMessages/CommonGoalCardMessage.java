package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ScoringToken;

import java.util.Stack;

public class CommonGoalCardMessage extends EventMessage {

    private final long serialVersionUID = 1L;
    private final int commonGoalCardIndex;
    private final CommonGoalCard commonGoalCard;

    public CommonGoalCardMessage(String nickName, CommonGoalCard commonGoalCard, int commonGoalCardIndex) {
        super(nickName, EventMessageType.COMMON_GOAL_CARD);
        this.commonGoalCardIndex = commonGoalCardIndex;
        this.commonGoalCard = commonGoalCard;
    }

    public int getCommonGoalCardIndex() {
        return commonGoalCardIndex;
    }
    public CommonGoalCard getCommonGoalCard() {return commonGoalCard;}

    @Override
    public String toString() {
        return getCommonGoalCard().toString();
    }
}
