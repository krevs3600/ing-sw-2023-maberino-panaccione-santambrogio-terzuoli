package it.polimi.ingsw;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.TileType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class BagTest {

    @Test
    public void getZeroTiles(){
        Bag testBag = new Bag();
        int oldSize = testBag.getSize();
        testBag.drawTile(-3);
        int newSize = testBag.getSize();

        assertThrows(new IllegalArgumentException());
    }

    public void getZeroTilesSize(){
        Bag testBag = new Bag();
        int oldSize = testBag.getSize();
        testBag.drawTile(0);
        int newSize = testBag.getSize();

        assertTrue(oldSize==newSize);

    }

    @Test
    public void drawTooManyTiles(){
        Bag testBag = new Bag();
        testBag.drawTile(133);
        int newSize = testBag.getSize();

        assertThrows(IllegalArgumentException());
    }

    @Test
    public void drawTilesTestSize(){
        Bag testBag = new Bag();
        int oldSize = testBag.getSize();
        testBag.drawTile(30);
        int newSize = testBag.getSize();

        assertTrue(newSize == oldSize-30);
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

        assertTrue(newSize == oldSize-25);
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

        assertTrue(newSize == oldSize-25);
    }

    @Test
    public void insertTilesTestSizeOverflow(){
        Bag testBag = new Bag();
        List<ItemTile> drawTiles = testBag.drawTile(132);
        drawTiles.add(132, new ItemTile(TileType.CAT));
        testBag.insertTiles(drawTiles);
        int newSize = testBag.getSize();

        assertThrows(IllegalArgumentException());
    }



}
