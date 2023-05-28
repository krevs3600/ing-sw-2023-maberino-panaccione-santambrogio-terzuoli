package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.PersonalGoalCard;

public class PersonalGoalCardMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final PersonalGoalCard personalGoalCard;
    public PersonalGoalCardMessage(String nickName, PersonalGoalCard personalGoalCard) {
        super(nickName, EventMessageType.PERSONAL_GOAL_CARD);
        this.personalGoalCard = personalGoalCard;
    }

    public PersonalGoalCard getPersonalGoalCard(){
        return this.personalGoalCard;
    }

    public String toString(){
        return "" + getNickname() + "'s personal goal card is : \n" + getPersonalGoalCard().toString();
    }
}
