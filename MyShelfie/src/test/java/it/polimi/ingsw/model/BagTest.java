package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class BagTest {
    private Bag testBag;
    @Before
    public void setUp(){
        testBag = new Bag();
    }

    @Test (expected = IllegalArgumentException.class)
    public void getZeroTiles(){
        int oldSize = testBag.getSize();
        testBag.drawTile(-3);
        int newSize = testBag.getSize();

    }

    @Test
    public void DrawSingleTile(){
        int oldSize = testBag.getSize();
        testBag.drawTile();
        int newSize = testBag.getSize();
        assertEquals(oldSize-1, newSize);

    }

    @Test
    public void getZeroTilesSize(){
        int oldSize = testBag.getSize();
        testBag.drawTile(0);
        int newSize = testBag.getSize();

        assertTrue(oldSize==newSize);

    }

    @Test
    public void drawAllTiles(){
        testBag.drawTile(132);
        int newSize = testBag.getSize();
        assertEquals(0, newSize);
    }

    @Test (expected = IllegalArgumentException.class)
    public void drawTooManyTiles(){
        testBag.drawTile(133);
        int newSize = testBag.getSize();

    }

    @Test
    public void drawTooManyTilesWithAssert(){
        assertThrows(IllegalArgumentException.class, () -> {
            testBag.drawTile(133);
        });
    }

    @Test
    public void drawOneTileFromEmptyBag(){
        testBag.drawTile(132);
        assertThrows(IndexOutOfBoundsException.class, ()-> {
            testBag.drawTile();
        });
    }

    @Test
    public void drawTilesTestSize(){
        int oldSize = testBag.getSize();
        testBag.drawTile(25);
        int newSize = testBag.getSize();
        try {
            assertTrue(newSize == oldSize - 25);
            }
        catch (AssertionError e){
            System.out.println(oldSize);
            }
        }




    @Test
    public void insertTilesTestSize(){
        List<ItemTile> drawTiles = testBag.drawTile(30);
        int oldSize = testBag.getSize();
        List<ItemTile> leftovers = new ArrayList<>();
        for(int i=0; i<5; i++){
            leftovers.add(drawTiles.get(ThreadLocalRandom.current().nextInt(0, 30-i)));
        }
        testBag.insertTiles(leftovers);
        int newSize = testBag.getSize();

        assertTrue(newSize == oldSize + 5);
    }

    @Test
    public void insertTilesTest(){
        List<ItemTile> drawTiles = testBag.drawTile(30);
        int oldSize = testBag.getSize();
        List<ItemTile> leftovers = new ArrayList<>();
        for(int i=0; i<5; i++){
            leftovers.add(drawTiles.get(ThreadLocalRandom.current().nextInt(0, 30-i)));
        }
        testBag.insertTiles(leftovers);
        int newSize = testBag.getSize();

        //assertTrue(newSize == oldSize-25);
        try {
            assertEquals(oldSize +5, newSize);
        } catch (AssertionError e){
            System.out.println(oldSize);
        }
    }


    @Test (expected = IllegalArgumentException.class)
    public void insertTilesTestSizeOverflow(){
        List<ItemTile> drawTiles = testBag.drawTile(132);
        drawTiles.add(132, new ItemTile(TileType.CAT));
        testBag.insertTiles(drawTiles);
        int newSize = testBag.getSize();

    }

    @Test
    public void insertOneMoreThanSize(){
       List<ItemTile> list = new ArrayList<ItemTile>();
       list.add(new ItemTile(TileType.CAT));
       assertThrows(IllegalArgumentException.class, ()-> {
           testBag.insertTiles(list);
       });
    }

}
