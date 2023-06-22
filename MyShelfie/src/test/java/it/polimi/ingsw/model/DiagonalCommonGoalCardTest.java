package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.DiagonalCommonGoalCard;
import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiagonalCommonGoalCardTest {

    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private Bookshelf testBookshelf3;
    private CommonGoalCard testCommonGoalCard;
    private TilePack testTilePack;
    @Before
    public void setUp()  {
        testBookshelf1 = new Bookshelf();
        testBookshelf2 = new Bookshelf();
        testBookshelf3 = new Bookshelf();
        testCommonGoalCard = new DiagonalCommonGoalCard();
        testTilePack = new TilePack();
    }

    @Test
    public void checkPatternTest(){

        // empty bookshelf: toBeChecked false
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
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            for (int j = 0; j < 3; j++) {
                testBookshelf1.insertTile(testTilePack, 2, 0);
            }
        }
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // five tiles forming a diagonal but not all of the same type
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            testTilePack.insertTile(new ItemTile(TileType.TROPHY));
            for (int j = 0; j < 3; j++) {
                testBookshelf1.insertTile(testTilePack, 3+i, 0);
            }
        }

        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testBookshelf1.insertTile(testTilePack, 3, 0);

        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 4, 0);
        }

        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // common goal achieved: toBeChecked true and checkPattern true
        // growing diagonal from left to right
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
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
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            for (int j = 0; j < 3; j++) {
                testBookshelf2.insertTile(testTilePack, 2, 0);
            }
        }

        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.FRAME));
            testTilePack.insertTile(new ItemTile(TileType.TROPHY));
            for (int j = 0; j < 3; j++) {
                testBookshelf2.insertTile(testTilePack, 3+i, 0);
            }
        }

        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testBookshelf2.insertTile(testTilePack, 3, 0);

        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 2; j++) {
            testBookshelf2.insertTile(testTilePack, 4, 0);
        }

        BookshelfView bookshelfView2 = new BookshelfView(testBookshelf2, "test");
        System.out.println(bookshelfView2);

        assertTrue(testCommonGoalCard.checkPattern(testBookshelf2));

        // common goal achieved: toBeChecked true and checkPattern true
        // growing diagonal from right to left
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testBookshelf3.insertTile(testTilePack, 4, 0);

        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int i=0; i<2; i++) {
            testBookshelf3.insertTile(testTilePack, 3, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int i=0; i<3; i++) {
            testBookshelf3.insertTile(testTilePack, 2, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        for (int i=0; i<3; i++) {
            testBookshelf3.insertTile(testTilePack, 1, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testBookshelf3.insertTile(testTilePack, 1, 0);

        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        for (int i=0; i<3; i++) {
            testBookshelf3.insertTile(testTilePack, 0, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int i=0; i<2; i++) {
            testBookshelf3.insertTile(testTilePack, 0, 0);
        }

        BookshelfView bookshelfView3 = new BookshelfView(testBookshelf3, "test");
        System.out.println(bookshelfView3);
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf3));
    }
}