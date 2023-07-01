package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BookshelfTest {

    private Bookshelf testBookshelf;
    private TilePack testTilePack;
    @Before
    public void setUp() {
        testBookshelf = new Bookshelf();
        testTilePack = new TilePack();
    }

    @Test
    public void insertTileTest() {

        // before insertion
        assertNull(testBookshelf.getGrid()[testBookshelf.getMaxHeight()-1][0]);

        // after insertion
        ItemTile itemTile = new ItemTile(TileType.CAT);
        testTilePack.insertTile(itemTile);
        testBookshelf.insertTile(testTilePack, 0, 0);
        assertEquals(itemTile, testBookshelf.getGrid()[testBookshelf.getMaxHeight()-1][0]);
        // upon the space of insertion there is no item tiles
        assertNull(testBookshelf.getGrid()[testBookshelf.getMaxHeight()-2][0]);

        // exception about exceeding the number of insertable tiles in a column
        for (int i = 0; i< testBookshelf.getMaxHeight()-1; i++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testBookshelf.insertTile(testTilePack, 0, 0);
        }
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        Exception exception1 = assertThrows(IndexOutOfBoundsException.class, () -> testBookshelf.insertTile(testTilePack, 0, 0));

        String expectedMessage1 = "Not enough space in this column, please select another column or remove some tiles from the tile pack";
        String actualMessage1 = exception1.getMessage();

        assertTrue(actualMessage1.contains(expectedMessage1));

        // exception about inserting in an invalid column
        Exception exception2 = assertThrows(IndexOutOfBoundsException.class, () -> testBookshelf.insertTile(testTilePack, testBookshelf.getMaxWidth(), 0));

        String expectedMessage2 = "Invalid column, please select a column ranging from 0 to " + (testBookshelf.getMaxWidth()-1);
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));

    }

    @Test
    public void getNumberInsertableTilesColumnTest() {

        // empty column
        assertEquals(6, testBookshelf.getNumberInsertableTilesColumn(0));

        // not empty nor full column
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        for (int i=0; i<3; i++) {
            testBookshelf.insertTile(testTilePack, 0, 0);
        }
        assertEquals(3, testBookshelf.getNumberInsertableTilesColumn(0));

        // full column
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        for (int i = 0; i < 3; i++) {
            testBookshelf.insertTile(testTilePack, 0, 0);
        }
        assertEquals(0, testBookshelf.getNumberInsertableTilesColumn(0));

        // exception about inserting in an invalid column
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> testBookshelf.getNumberInsertableTilesColumn(testBookshelf.getMaxWidth()));

        String expectedMessage = "Invalid column, please select a column ranging from 0 to " + (testBookshelf.getMaxWidth()-1);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void getNumberInsertableTilesTest() {

        // empty bookshelf
        assertEquals(6, testBookshelf.getNumberInsertableTiles());

        // not empty nor full bookshelf
        for (int k = 0; k< testBookshelf.getMaxWidth(); k++) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testTilePack.insertTile(new ItemTile(TileType.BOOK));
            testTilePack.insertTile(new ItemTile(TileType.TROPHY));
            for (int i = 0; i < 3; i++) {
                testBookshelf.insertTile(testTilePack, k, 0);
            }
        }
        assertEquals(3, testBookshelf.getNumberInsertableTiles());

        // full bookshelf
        for (int k = 0; k< testBookshelf.getMaxWidth(); k++) {
            while (testBookshelf.getNumberInsertableTilesColumn(k)>0) {
                testTilePack.insertTile(new ItemTile(TileType.CAT));
                testTilePack.insertTile(new ItemTile(TileType.BOOK));
                testTilePack.insertTile(new ItemTile(TileType.TROPHY));
                for (int i = 0; i < 3; i++) {
                    testBookshelf.insertTile(testTilePack, k, 0);
                }
            }
        }

        assertEquals(0, testBookshelf.getNumberInsertableTiles());

    }

    @Test
    public void isFullTest() {

        // empty bookshelf
        assertFalse(testBookshelf.isFull());

        // not empty nor full bookshelf
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        for (int i=0; i<3; i++) {
            testBookshelf.insertTile(testTilePack, 0, 0);
        }
        assertFalse(testBookshelf.isFull());

        // full bookshelf
        for (int k = 0; k< testBookshelf.getMaxWidth(); k++) {
            while (testBookshelf.getNumberInsertableTilesColumn(k)>0) {
                testTilePack.insertTile(new ItemTile(TileType.CAT));
                testTilePack.insertTile(new ItemTile(TileType.BOOK));
                testTilePack.insertTile(new ItemTile(TileType.TROPHY));
                for (int i = 0; i < 3; i++) {
                    testBookshelf.insertTile(testTilePack, k, 0);
                }
            }
        }

        assertTrue(testBookshelf.isFull());

    }

    @Test
    public void getNumberAdjacentTilesTest() {

        Map<Integer, Integer> m = new HashMap<>();
        // empty bookshelf
        assertEquals(m, testBookshelf.getNumberAdjacentTiles(TileType.CAT));

        // not empty bookshelf without item tiles of the given type
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        for (int i=0; i<3; i++) {
            testBookshelf.insertTile(testTilePack, 0, 0);
        }
        assertEquals(m, testBookshelf.getNumberAdjacentTiles(TileType.CAT));

        // bookshelf with some item tiles of the given type
        while (testBookshelf.getNumberInsertableTilesColumn(0)>0) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testBookshelf.insertTile(testTilePack, 0, 0);
        }

        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testBookshelf.insertTile(testTilePack, 1, 0);
        testTilePack.insertTile(new ItemTile(TileType.FRAME));
        testBookshelf.insertTile(testTilePack, 1, 0);
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testBookshelf.insertTile(testTilePack, 1, 0);
        testTilePack.insertTile(new ItemTile(TileType.BOOK));
        testBookshelf.insertTile(testTilePack, 1, 0);
        testTilePack.insertTile(new ItemTile(TileType.TROPHY));
        testBookshelf.insertTile(testTilePack, 1, 0);
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testBookshelf.insertTile(testTilePack, 1, 0);

        while (testBookshelf.getNumberInsertableTilesColumn(2)>0) {
            testTilePack.insertTile(new ItemTile(TileType.CAT));
            testBookshelf.insertTile(testTilePack, 2, 0);
        }

        for (int k = 3; k< testBookshelf.getMaxWidth(); k++) {
            while (testBookshelf.getNumberInsertableTilesColumn(k)>0) {
                testTilePack.insertTile(new ItemTile(TileType.CAT));
                testTilePack.insertTile(new ItemTile(TileType.BOOK));
                testTilePack.insertTile(new ItemTile(TileType.TROPHY));
                for (int i = 0; i < 3; i++) {
                    testBookshelf.insertTile(testTilePack, k, 0);
                }
            }
        }
        m.put(15, 1);

        assertEquals(m, testBookshelf.getNumberAdjacentTiles(TileType.CAT));

    }

    @Test
    public void insertTilesRandomlyTest() {
        testBookshelf.insertTilesRandomly();
        assertEquals(2, testBookshelf.getNumberInsertableTiles());
        for (int i=1; i<testBookshelf.getMaxWidth(); i++)
            assertEquals(0, testBookshelf.getNumberInsertableTilesColumn(i));
    }
}