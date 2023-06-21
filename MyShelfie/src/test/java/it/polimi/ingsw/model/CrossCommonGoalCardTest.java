package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.CrossCommonGoalCard;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CrossCommonGoalCardTest {

    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private CommonGoalCard testCommonGoalCard;
    private TilePack testTilePack;
    @Before
    public void setUp()  {
        testBookshelf1 = new Bookshelf();
        testBookshelf2 = new Bookshelf();
        testCommonGoalCard = new CrossCommonGoalCard();
        testTilePack = new TilePack();
    }

    @Test
    public void checkPatternTest(){

        // empty bookshelf: toBeChecked false
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // not empty bookshelf: toBeChecked true but checkPattern false
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            for (int j = 0; j < 3; j++) {
                testBookshelf1.insertTile(testTilePack, 0, 0);
            }
        }
        for (int i = 0; i< 2; i++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            for (int j = 0; j < 3; j++) {
                testBookshelf1.insertTile(testTilePack, 2, 0);
            }
        }
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // five tiles forming an X but not all of the same tile type
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf1.insertTile(testTilePack, 1, 0);
        }
        assertFalse(testCommonGoalCard.checkPattern(testBookshelf1));

        // common goal achieved: toBeChecked true and checkPattern true

        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 0, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 1, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        for (int j = 0; j < 3; j++) {
            testBookshelf2.insertTile(testTilePack, 2, 0);
        }
        assertTrue(testCommonGoalCard.checkPattern(testBookshelf2));
    }
}