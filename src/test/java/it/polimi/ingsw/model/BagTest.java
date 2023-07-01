package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class BagTest {
    private Bag testBag;
    @Before
    public void setUp(){

        testBag = new Bag();
    }

    @Test
    public void drawTileTest() {

        // before drawing: full bag
        assertEquals(testBag.getMaxSize(), testBag.getSize());

        // after drawing
        testBag.drawTile();
        assertEquals(testBag.getMaxSize()-1, testBag.getSize());

        // exception about drawing from an empty bag
        for (int i=0; i<testBag.getMaxSize()-1; i++) {
            testBag.drawTile();
        }
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> testBag.drawTile());

        String expectedMessage = "The bag is empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void drawTilesTest() {

        // before drawing: full bag
        assertEquals(testBag.getMaxSize(), testBag.getSize());

        // after drawing
        int amount = 17;
        testBag.drawTile(amount);
        assertEquals(testBag.getMaxSize()-amount, testBag.getSize());

        // exception about drawing too many tiles, exceeding the size of the bag
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> testBag.drawTile(testBag.getMaxSize()-amount+1));

        String expectedMessage1 = "You're taking too main items. Bag size is: " + testBag.getSize();
        String actualMessage1 = exception1.getMessage();

        assertTrue(actualMessage1.contains(expectedMessage1));

        // exception about drawing a negative number of tiles
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> testBag.drawTile(-1));

        String expectedMessage2 = "The selected amount of tiles to draw is non-positive!";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));
    }

    @Test
    public void insertTilesTest(){
        int amount = 30;
        List<ItemTile> drawTiles = testBag.drawTile(amount);

        // before insertion
        assertEquals(testBag.getMaxSize()-amount, testBag.getSize());

        // after insertion
        testBag.insertTiles(drawTiles);
        assertEquals(testBag.getMaxSize(), testBag.getSize());

        // exception about exceeding the bag capacity by inserting too many tiles
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> testBag.insertTiles(drawTiles));

        String expectedMessage2 = "Exceeding bag capacity";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));
    }

}
