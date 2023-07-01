package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.EightTilesCommonGoalCard;
import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EightTilesCommonGoalCardTest {
    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private CommonGoalCard testCommonGoalCard;
    private TilePack testTilePack;
    @Before
    public void setUp()  {
        testBookshelf1 = new Bookshelf();
        testBookshelf2 = new Bookshelf();
        testCommonGoalCard = new EightTilesCommonGoalCard();
        testTilePack = new TilePack();
    }

    @Test
    public void toStringTest() {
        assertEquals("EIGHT TILES OF THE SAME TYPE.THERE IS NO RESTRICTION ABOUT THE POSITION OF THESE TILES", testCommonGoalCard.toString());
        assertEquals("EightTilesCommonGoalCard", testCommonGoalCard.getType());
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
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            for (int j = 0; j < 3; j++) {
                testBookshelf1.insertTile(testTilePack, 1, 0);
            }
        }
        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // seven tiles of the same type, but not eight
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 3, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // common goal achieved: toBeChecked true and checkPattern true
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.TROPHY));
            testTilePack.insertTile(new ItemTile(TileType.PLANT));
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            for (int j = 0; j < 3; j++) {
                testBookshelf2.insertTile(testTilePack, 0, 0);
            }
        }
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            for (int j = 0; j < 3; j++) {
                testBookshelf2.insertTile(testTilePack, 1, 0);
            }
        }
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.TROPHY));
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            for (int j = 0; j < 3; j++) {
                testBookshelf2.insertTile(testTilePack, 2, 0);
            }
        }

        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.PLANT));
            testTilePack.insertTile(new ItemTile(TileType.GAME));
            for (int j = 0; j < 3; j++) {
                testBookshelf2.insertTile(testTilePack, 3+i, 0);
            }
        }
        System.out.println(new BookshelfView(testBookshelf2, "test"));
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf2));
    }

}