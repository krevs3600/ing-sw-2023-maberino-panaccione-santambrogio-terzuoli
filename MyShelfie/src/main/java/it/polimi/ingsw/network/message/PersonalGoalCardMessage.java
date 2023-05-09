package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.ModelView.PersonalGoalCardDeckView;
import it.polimi.ingsw.model.PersonalGoalCard;

public class PersonalGoalCardMessage extends Message{
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
