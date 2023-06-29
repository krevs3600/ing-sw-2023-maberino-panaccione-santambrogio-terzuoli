package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.TwoLinesCommonGoalCard;
import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoLinesCommonGoalCardTest {
    private Bookshelf testBookshelf1;
    private CommonGoalCard testCommonGoalCard;
    private TilePack testTilePack;
    @Before
    public void setUp()  {
        testBookshelf1 = new Bookshelf();
        testCommonGoalCard = new TwoLinesCommonGoalCard();
        testTilePack = new TilePack();
    }

    @Test
    public void toStringTest() {
        assertEquals(     "TWO LINES EACH FORMED BY FIVE DIFFERENT TYPES OF TILES\n" +
                "ONE LINE CAN SHOW THE SAME OR A DIFFERENT COMBINATION OF THE OTHER LINE.", testCommonGoalCard.toString());
        assertEquals("TwoLinesCommonGoalCard", testCommonGoalCard.getType());
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
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));

        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 1, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 1, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // Two lines each formed by four different types of tiles
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 2, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 3, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 4, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // Only one line formed by five different types of tiles

        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 2, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 3, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 4, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // common goal achieved: toBeChecked true and checkPattern true
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        testTilePack.insertTile(new ItemTile(TileType.GAME));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 2, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.GAME));
        testTilePack.insertTile(new ItemTile(TileType.PLANT));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 3, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        for (int j = 0; j < 2; j++) {
            testBookshelf1.insertTile(testTilePack, 4, 0);
        }

        System.out.println(new BookshelfView(testBookshelf1, "test"));
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf1));
    }

}