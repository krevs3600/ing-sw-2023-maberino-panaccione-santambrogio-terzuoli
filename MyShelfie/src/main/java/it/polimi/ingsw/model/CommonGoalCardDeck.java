package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.CornersCommonGoalCard;
import it.polimi.ingsw.model.utils.Drawable;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * <h1>Class CommonGoalCardDeck</h1>
 * The class CommonGoalCardDeck contains a list of common goal cards
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 3/28/2023
 */

public class CommonGoalCardDeck implements Drawable {

    private int size = 0;
    private List<CommonGoalCard> deck;

    /**
     * Class constructor
     */
    public CommonGoalCardDeck () {
        CommonGoalCard corners = new CornersCommonGoalCard();
        CommonGoalCard cross = new CornersCommonGoalCard();
        CommonGoalCard diagonal = new CornersCommonGoalCard();
        CommonGoalCard eightTiles =  new CornersCommonGoalCard();
        CommonGoalCard fourGroups =  new CornersCommonGoalCard();
        CommonGoalCard fourLines =  new CornersCommonGoalCard();
        CommonGoalCard increasingColumns =  new CornersCommonGoalCard();
        CommonGoalCard sixGroups = new CornersCommonGoalCard();
        CommonGoalCard threeColumns =  new CornersCommonGoalCard();
        CommonGoalCard twoColumns =   new CornersCommonGoalCard();
        CommonGoalCard twoLines =  new CornersCommonGoalCard();
        CommonGoalCard twoSquares =  new CornersCommonGoalCard();
        CommonGoalCard[] allCommonGoalCards = {corners, cross, diagonal, eightTiles, fourGroups, fourLines, increasingColumns, sixGroups, threeColumns, twoColumns, twoLines, twoSquares};
        this.deck = new ArrayList<>();
            for(CommonGoalCard cgc: allCommonGoalCards) {
                deck.add(cgc);
                this.size++;
            }
    }

    /**
     * This method is used to draw a random common goal card from the deck
     * @return GoalCard It returns the randomly drawn goal card
     */
    @Override
    public CommonGoalCard draw() {
        int randNumber = ThreadLocalRandom.current().nextInt(0, getDeck().size());
        CommonGoalCard toBeDrawn = getDeck().get(randNumber);
        deck.remove(randNumber);
        size--;
        return toBeDrawn;
    }

    /**
     * This getter method gets the deck of common goal cards
     * @return List<CommonGoalCard> It returns the deck
     */
    public List<CommonGoalCard> getDeck() {
        return deck;
    }

    public int getSize () { return size;}
}