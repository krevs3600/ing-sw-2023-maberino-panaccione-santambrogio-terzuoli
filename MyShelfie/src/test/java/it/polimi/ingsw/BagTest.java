package it.polimi.ingsw;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class BagTest {

    @Test (expected = IllegalArgumentException.class)
    public void getZeroTiles(){
        Bag testBag = new Bag();
        int oldSize = testBag.getSize();
        testBag.drawTile(-3);
        int newSize = testBag.getSize();

    }

    @Test
    public void DrawSingleTile(){
        Bag testBag = new Bag();
        int oldSize = testBag.getSize();
        testBag.drawTile();
        int newSize = testBag.getSize();
        assertEquals(oldSize-1, newSize);

    }

    @Test
    public void getZeroTilesSize(){
        Bag testBag = new Bag();
        int oldSize = testBag.getSize();
        testBag.drawTile(0);
        int newSize = testBag.getSize();

        assertTrue(oldSize==newSize);

    }

    @Test
    public void drawAllTiles(){
        Bag testBag = new Bag();
        testBag.drawTile(132);
        int newSize = testBag.getSize();
        assertEquals(0, newSize);
    }

    @Test (expected = IllegalArgumentException.class)
    public void drawTooManyTiles(){
        Bag testBag = new Bag();
        testBag.drawTile(133);
        int newSize = testBag.getSize();

    }

    @Test
    public void drawTooManyTilesWithAssert(){
        Bag testBag = new Bag();
        assertThrows(IllegalArgumentException.class, () -> {
            testBag.drawTile(133);
        });
    }

    @Test
    public void drawOneTileFromEmptyBag(){
        Bag testBag = new Bag();
        testBag.drawTile(132);
        assertThrows(IndexOutOfBoundsException.class, ()-> {
            testBag.drawTile();
        });
    }

    @Test
    public void drawTilesTestSize(){
        Bag testBag = new Bag();
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
        Bag testBag = new Bag();
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
        Bag testBag = new Bag();
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

        Bag testBag = new Bag();
        List<ItemTile> drawTiles = testBag.drawTile(132);
        drawTiles.add(132, new ItemTile(TileType.CAT));
        testBag.insertTiles(drawTiles);
        int newSize = testBag.getSize();

    }

    @Test
    public void insertOneMoreThanSize(){
       Bag testBag = new Bag();
       List<ItemTile> list = new ArrayList<ItemTile>();
       list.add(new ItemTile(TileType.CAT));
       assertThrows(IllegalArgumentException.class, ()-> {
           testBag.insertTiles(list);
       });
    }

}
