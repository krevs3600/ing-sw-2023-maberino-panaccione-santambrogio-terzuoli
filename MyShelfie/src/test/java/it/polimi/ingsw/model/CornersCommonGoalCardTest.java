package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.CornersCommonGoalCard;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CornersCommonGoalCardTest {
    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private CommonGoalCard testCommonGoalCard;
    private TilePack testTilePack;
    @Before
    public void setUp()  {
        testBookshelf1 = new Bookshelf();
        testBookshelf2 = new Bookshelf();
        testCommonGoalCard = new CornersCommonGoalCard();
        testTilePack = new TilePack();
    }

    @Test
    public void correctCheckPattern() {

        // empty bookshelf: toBeChecked false
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // not empty bookshelf: toBeChecked true but checkPattern false
        for (int i = 0; i< testBookshelf1.getMaxHeight(); i++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testBookshelf1.insertTile(testTilePack, 0, 0);
        }
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // all four corners occupied but not by items of the same type
        for (int i = 0; i< testBookshelf1.getMaxHeight(); i++) {
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testBookshelf1.insertTile(testTilePack, testBookshelf1.getMaxWidth()-1, 0);
        }
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // common goal achieved: toBeChecked true and checkPattern true
        // filling the first column
        for (int i = 0; i< testBookshelf2.getMaxHeight(); i++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testBookshelf2.insertTile(testTilePack, 0, 0);
        }
        // filling the last column
        for (int j = 0; j< testBookshelf1.getMaxHeight(); j++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testBookshelf2.insertTile(testTilePack, testBookshelf1.getMaxWidth()-1, 0);
        }
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf2));
    }
}