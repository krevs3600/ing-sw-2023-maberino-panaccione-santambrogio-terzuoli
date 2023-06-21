package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CommonGoalCardDeckTest {

    private CommonGoalCardDeck testCommonGoalCardDeck;

    @Before
    public void setUp () {
        testCommonGoalCardDeck = new CommonGoalCardDeck();
    }

    @Test
    public void drawTest () {

        // before drawing a card
        int numberOfPersonalGoalCards = 12;
        assertEquals(numberOfPersonalGoalCards, testCommonGoalCardDeck.getSize());

        // after drawing a card
        GoalCard goalCard = testCommonGoalCardDeck.draw();
        assertEquals(numberOfPersonalGoalCards-1, testCommonGoalCardDeck.getSize());
        assertFalse(testCommonGoalCardDeck.getDeck().contains(goalCard));
    }
}
