package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.*;
import it.polimi.ingsw.model.utils.Drawable;

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
    private final List<CommonGoalCard> deck;

    /**
     * Class constructor
     */
    public CommonGoalCardDeck () {
        CommonGoalCard corners = new CornersCommonGoalCard();
        CommonGoalCard cross = new CrossCommonGoalCard();
        CommonGoalCard diagonal = new DiagonalCommonGoalCard();
        CommonGoalCard eightTiles =  new EightTilesCommonGoalCard();
        CommonGoalCard fourGroups =  new FourGroupsCommonGoalCard();
        CommonGoalCard fourLines =  new FourLinesCommonGoalCard();
        CommonGoalCard increasingColumns =  new IncreasingColumnsCommonGoalCard();
        CommonGoalCard sixGroups = new SixGroupsCommonGoalCard();
        CommonGoalCard threeColumns =  new ThreeColumnsCommonGoalCard();
        CommonGoalCard twoColumns =   new TwoColumnsCommonGoalCard();
        CommonGoalCard twoLines =  new TwoLinesCommonGoalCard();
        CommonGoalCard twoSquares =  new TwoSquaresCommonGoalCard();
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
     * Getter method
     * @return the {@link CommonGoalCardDeck#deck} of {@link CommonGoalCard}s}
     */
    public List<CommonGoalCard> getDeck() {
        return deck;
    }

    /**
     * Getter method
     * @return the {@link CommonGoalCardDeck#size} of the deck
     */
    public int getSize () { return size;}
}