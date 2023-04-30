package it.polimi.ingsw.model;

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
        CommonGoalCard corners = new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard cross = new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard diagonal = new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard eightTiles =  new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard fourGroups =  new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard fourLines =  new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard increasingColumns =  new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard sixGroups = new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard threeColumns =  new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard twoColumns =   new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard twoLines =  new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard twoSquares =  new CornersCommonGoalCard(NumberOfPlayers.TWO_PLAYERS, RomanNumber.TWO);
        CommonGoalCard[] allCommonGoalCards = {corners, cross, diagonal, eightTiles, fourGroups, fourLines, increasingColumns, sixGroups, threeColumns, twoColumns, twoLines, twoSquares};
        this.deck = new ArrayList<CommonGoalCard>();
            for(CommonGoalCard cgc: allCommonGoalCards) {
                deck.add(cgc);
                this.size++;
            }
    }

    /**
     * This method is used to draw a random common goal card from the deck
     * @return GoalCard It returns the randomly drawn goal card
     */
    //TODO: draw method
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
}