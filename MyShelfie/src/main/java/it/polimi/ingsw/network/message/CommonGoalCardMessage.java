package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;

public class CommonGoalCardMessage extends Message {

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
