package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCardDeck;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

public class CommonGoalCardDeckView extends Observable implements Observer {

    private final CommonGoalCardDeck commonGoalCardDeck;

    public CommonGoalCardDeckView (CommonGoalCardDeck commonGoalCardDeck) {
        this.commonGoalCardDeck = commonGoalCardDeck;
    }

    public int getSize() {
        return commonGoalCardDeck.getSize();
    }

    public List<CommonGoalCard> getDeck () {
        return commonGoalCardDeck.getDeck();
    }
}
