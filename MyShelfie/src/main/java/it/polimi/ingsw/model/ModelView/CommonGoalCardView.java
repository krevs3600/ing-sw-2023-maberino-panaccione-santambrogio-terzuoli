package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;

public class CommonGoalCardView {
    private final CommonGoalCard card;

    public CommonGoalCardView(CommonGoalCard commonGoalCard){
        this.card = commonGoalCard;
    }

    @Override
    public String toString(){
        return card.toString();
    }
}
