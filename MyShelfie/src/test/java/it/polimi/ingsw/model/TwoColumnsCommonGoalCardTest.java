package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.TwoColumnsCommonGoalCard;
import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoColumnsCommonGoalCardTest {
    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private CommonGoalCard testCommonGoalCard;
    private TilePack testTilePack;
    @Before
    public void setUp()  {
        testBookshelf1 = new Bookshelf();
        testBookshelf2 = new Bookshelf();
        testCommonGoalCard = new TwoColumnsCommonGoalCard();
        testTilePack = new TilePack();
    }

    @Test
    public void toStringTest() {
        assertEquals( "TWO COLUMNS EACH FORMED BY SIX DIFFERENT TYPES OF TILES", testCommonGoalCard.toString());
        assertEquals("TwoColumnsCommonGoalCard", testCommonGoalCard.getType());
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
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            testTilePack.insertTile(new ItemTile(TileType.BOOK));

            for (int j = 0; j < 3; j++) {
                testBookshelf1.insertTile(testTilePack, 1, 0);
            }
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // Two columns each formed by five different types of tiles.
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 2, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 2, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 3, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 3, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // Only one column formed by six different types of tiles
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 4, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 4, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // common goal achieved: toBeChecked true and checkPattern true
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 4, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 4, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 3, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 3, 0);
        }

        System.out.println(new BookshelfView(testBookshelf2, "test"));
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf2));
    }
}