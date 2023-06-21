package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonalGoalCardDeckTest {

    private PersonalGoalCardDeck testPersonalGoalCardDeck;

    @Before
    public void setUp () {
        testPersonalGoalCardDeck = new PersonalGoalCardDeck();
    }

    @Test
    public void drawTest () {

        // before drawing a card
        int numberOfPersonalGoalCards = 12;
        assertEquals(numberOfPersonalGoalCards, testPersonalGoalCardDeck.getSize());

        // after drawing a card
        GoalCard goalCard = testPersonalGoalCardDeck.draw();
        assertEquals(numberOfPersonalGoalCards-1, testPersonalGoalCardDeck.getSize());
        assertFalse(testPersonalGoalCardDeck.getDeck().contains(goalCard));
    }
}
