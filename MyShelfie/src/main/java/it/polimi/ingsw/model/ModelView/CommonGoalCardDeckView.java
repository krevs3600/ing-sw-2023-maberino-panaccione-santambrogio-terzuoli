package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCardDeck;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonGoalCardDeckView implements Serializable {

    private final List<CommonGoalCardView> commonGoalCardDeck;
    @Serial
    private static final long serialVersionUID = 1L;

    public CommonGoalCardDeckView (CommonGoalCardDeck deck) {
        commonGoalCardDeck = new ArrayList<>();
        for(CommonGoalCard card : deck.getDeck()){
            this.commonGoalCardDeck.add(new CommonGoalCardView(card));
        }
    }

    public int getSize() {
        return commonGoalCardDeck.size();
    }

    public List<CommonGoalCardView> getDeck () {
        return new ArrayList<>(this.commonGoalCardDeck);
    }
}
