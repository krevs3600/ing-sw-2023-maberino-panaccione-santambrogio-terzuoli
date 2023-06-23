package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.IncreasingColumnsCommonGoalCard;
import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IncreasingColumnsCommonGoalCardTest {

    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private Bookshelf testBookshelf3;
    private Bookshelf testBookshelf4;
    private CommonGoalCard testCommonGoalCard;
    private TilePack testTilePack;
    @Before
    public void setUp()  {
        testBookshelf1 = new Bookshelf();
        testBookshelf2 = new Bookshelf();
        testBookshelf3 = new Bookshelf();
        testBookshelf4 = new Bookshelf();
        testCommonGoalCard = new IncreasingColumnsCommonGoalCard();
        testTilePack = new TilePack();
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
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.GAME));
            testTilePack.insertTile(new ItemTile(TileType.GAME));
            testTilePack.insertTile(new ItemTile(TileType.GAME));
            for (int j = 0; j < 3; j++) {
                testBookshelf1.insertTile(testTilePack, 2, 0);
            }
        }
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.GAME));
            testTilePack.insertTile(new ItemTile(TileType.GAME));
            testTilePack.insertTile(new ItemTile(TileType.GAME));
            for (int j = 0; j < 3; j++) {
                testBookshelf1.insertTile(testTilePack, 3, 0);
            }
        }
        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // just one column not following the pattern
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testBookshelf2.insertTile(testTilePack, 0, 0);

        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 2; j++) {
            testBookshelf2.insertTile(testTilePack, 1, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 2, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 3, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 4, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testBookshelf2.insertTile(testTilePack, 4, 0);

        System.out.println(new BookshelfView(testBookshelf2, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf2));

        // common goal achieved: toBeChecked true and checkPattern true
        // increasing height from left to right
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testBookshelf3.insertTile(testTilePack, 0, 0);

        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 2; j++) {
            testBookshelf3.insertTile(testTilePack, 1, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf3.insertTile(testTilePack, 2, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf3.insertTile(testTilePack, 3, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testBookshelf3.insertTile(testTilePack, 3, 0);

        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 3; j++) {
            testBookshelf3.insertTile(testTilePack, 4, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 2; j++) {
            testBookshelf3.insertTile(testTilePack, 4, 0);
        }

        System.out.println(new BookshelfView(testBookshelf3, "test"));
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf3));

        // common goal achieved: toBeChecked true and checkPattern true
        // increasing height from right to left
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testBookshelf4.insertTile(testTilePack, 4, 0);

        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 2; j++) {
            testBookshelf4.insertTile(testTilePack, 3, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf4.insertTile(testTilePack, 2, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf4.insertTile(testTilePack, 1, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testBookshelf4.insertTile(testTilePack, 1, 0);

        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 3; j++) {
            testBookshelf4.insertTile(testTilePack, 0, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 2; j++) {
            testBookshelf4.insertTile(testTilePack, 0, 0);
        }

        System.out.println(new BookshelfView(testBookshelf4, "test"));
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf4));
    }
}

