package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.TwoLinesCommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.TwoSquaresCommonGoalCard;
import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoSquaresCommonGoalCardTest {
    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private CommonGoalCard testCommonGoalCard;
    private TilePack testTilePack;
    @Before
    public void setUp()  {
        testBookshelf1 = new Bookshelf();
        testBookshelf2 = new Bookshelf();
        testCommonGoalCard = new TwoSquaresCommonGoalCard();
        testTilePack = new TilePack();
    }

    @Test
    public void toStringTest() {
        assertEquals( "TWO GROUPS EACH CONTAINING FOUR TILES OF THE SAME TYPE IN A 2X2 SQUARE\n" +
                "THE TILES OF ONE SQUARE CAN BE DIFFERENT FROM THOSE OF THE OTHER SQUARE.", testCommonGoalCard.toString());
        assertEquals("TwoSquaresCommonGoalCard", testCommonGoalCard.getType());
    }

    @Test
    public void checkPatternTest(){

        // empty bookshelf: toBeChecked false
        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // not empty bookshelf: toBeChecked true but checkPattern false
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            testTilePack.insertTile(new ItemTile(TileType.BOOK));

            for (int j = 0; j < 3; j++) {
                testBookshelf1.insertTile(testTilePack, 0, 0);
            }
        }

        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.CAT));

        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 1, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // Two incomplete squares
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 1, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 1, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // Only one group containing 4 tiles of the same type in a 2x2 square

        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 2, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // common goal achieved: toBeChecked true and checkPattern true
        // two squares formed by two different types of tiles
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 2, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf1));

        // common goal achieved: toBeChecked true and checkPattern true
        // two adjacent squares formed by the same type of tiles
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 2; j++) {
            testBookshelf2.insertTile(testTilePack, 0, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 2; j++) {
            testBookshelf2.insertTile(testTilePack, 0, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 2; j++) {
            testBookshelf2.insertTile(testTilePack, 1, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 2; j++) {
            testBookshelf2.insertTile(testTilePack, 1, 0);
        }

        System.out.println(new BookshelfView(testBookshelf2, "test"));
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf2));
    }


}