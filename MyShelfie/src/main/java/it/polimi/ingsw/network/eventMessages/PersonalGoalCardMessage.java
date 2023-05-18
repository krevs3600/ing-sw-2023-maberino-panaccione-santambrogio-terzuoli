package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.network.MessageType;

public class PersonalGoalCardMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private PersonalGoalCard personalGoalCard;
    public PersonalGoalCardMessage(String nickName, PersonalGoalCard personalGoalCard) {
        super(nickName, MessageType.PERSONAL_GOAL_CARD);
        this.personalGoalCard = personalGoalCard;
    }

    public PersonalGoalCard getPersonalGoalCard(){
        return this.personalGoalCard;
    }

    public String toString(){
        return "" + getNickName() + "'s personal goal card is : \n" + getPersonalGoalCard().toString();
    }
}
