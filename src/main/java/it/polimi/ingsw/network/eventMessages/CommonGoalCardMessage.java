package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class CommonGoalCardMessage</h1>
 * The class CommonGoalCardMessage extends the abstract class EventMessage. This type of message is used to send to the View
 * the {@link CommonGoalCard}
 *
 * @author Francesco Maberino, Carlo Terzuoli
 * @version 1.0
 * @since 5/9/2023
 */
public class CommonGoalCardMessage extends EventMessage {

    private final long serialVersionUID = 1L;
    private final int commonGoalCardIndex;
    private final CommonGoalCard commonGoalCard;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     * @param commonGoalCard to be sent
     * @param commonGoalCardIndex to be sent
     */
    public CommonGoalCardMessage(String nickName, CommonGoalCard commonGoalCard, int commonGoalCardIndex) {
        super(nickName, EventMessageType.COMMON_GOAL_CARD);
        this.commonGoalCardIndex = commonGoalCardIndex;
        this.commonGoalCard = commonGoalCard;
    }

    /**
     * Getter Method
     * @return the index of the {@link CommonGoalCard} of the message
     */
    public int getCommonGoalCardIndex() {
        return commonGoalCardIndex;
    }

    /**
     * Getter Method
     * @return the {@link CommonGoalCard} of the message
     */
    public CommonGoalCard getCommonGoalCard() {return commonGoalCard;}

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString() {
        return getCommonGoalCard().toString();
    }
}
