package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.ThreeColumnsCommonGoalCard;
import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThreeColumnsCommonGoalCardTest {
    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private CommonGoalCard testCommonGoalCard;
    private TilePack testTilePack;
    @Before
    public void setUp()  {
        testBookshelf1 = new Bookshelf();
        testBookshelf2 = new Bookshelf();
        testCommonGoalCard = new ThreeColumnsCommonGoalCard();
        testTilePack = new TilePack();
    }

    @Test
    public void toStringTest() {
        assertEquals( "THREE COLUMNS EACH FORMED BY SIX TILES OF MAXIMUM THREE DIFFERENT TYPES.\n" +
                "ONE COLUMN CAN SHOW THE SAME OR A DIFFERENT COMBINATION OF ANOTHER COLUMN", testCommonGoalCard.toString());
        assertEquals("ThreeColumnsCommonGoalCard", testCommonGoalCard.getType());
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
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 1, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 2, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 3, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 4, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // Two columns each formed by six tiles of maximum three different types
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 1, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // Three columns each formed by six tiles of more than three different types
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 2, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 3, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 4, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // common goal achieved: toBeChecked true and checkPattern true
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.PLANT));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            testTilePack.insertTile(new ItemTile(TileType.TROPHY));

            for (int j = 0; j < 3; j++) {
                testBookshelf2.insertTile(testTilePack, 0, 0);
            }
        }
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.PLANT));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            testTilePack.insertTile(new ItemTile(TileType.TROPHY));

            for (int j = 0; j < 3; j++) {
                testBookshelf2.insertTile(testTilePack, 1, 0);
            }
        }
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.PLANT));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            testTilePack.insertTile(new ItemTile(TileType.TROPHY));

            for (int j = 0; j < 3; j++) {
                testBookshelf2.insertTile(testTilePack, 2, 0);
            }
        }

        System.out.println(new BookshelfView(testBookshelf2, "test"));
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf2));
    }

}