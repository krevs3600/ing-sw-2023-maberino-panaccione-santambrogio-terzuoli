package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.network.MessageType;

public class CommonGoalCardMessage extends EventMessage {

    private final long serialVersionUID = 1L;
    private final CommonGoalCard commonGoalCard;

    public CommonGoalCardMessage(String nickName, CommonGoalCard commonGoalCard) {
        super(nickName, MessageType.COMMON_GOAL_CARD);
        this.commonGoalCard = commonGoalCard;
    }

    public CommonGoalCard getCommonGoalCard() {
        return commonGoalCard;
    }

    @Override
    public String toString() {
        return getCommonGoalCard().toString();
    }
}
