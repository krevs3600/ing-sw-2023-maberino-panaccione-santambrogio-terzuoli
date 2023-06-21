package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class SpaceTest{
    private Space testSpace1;
    private Space testSpace2;

    @Before
    public void setUp(){
        testSpace1 = new Space(SpaceType.PLAYABLE, new Position(5,5));
        testSpace2 = new Space(SpaceType.FORBIDDEN, new Position(0, 0));
    }

    @Test
    public void getTileTest () {

        // before setting the tile in a space
        assertNull(testSpace1.getTile());

        // after setting the tile
        ItemTile itemTile = new ItemTile(TileType.CAT);
        testSpace1.setTile(itemTile);
        assertEquals(itemTile, testSpace1.getTile());
    }

    @Test
    public void setTileTest() {

        // set on a playable space
        // before setting the tile
        assertNull(testSpace1.getTile());

        // after setting the tile
        ItemTile itemTile1 = new ItemTile(TileType.CAT);
        testSpace1.setTile(itemTile1);
        assertEquals(itemTile1, testSpace1.getTile());

        // set on a forbidden space
        // exception about setting an item tile on a forbidden space
        IllegalAccessError exception2 = assertThrows(IllegalAccessError.class, () -> testSpace2.setTile(new ItemTile(TileType.CAT)));

        String expectedMessage2 = "The space is not available, please choose another space to put the tile on";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));

    }

    @Test
    public void drawTileTest() {

        // before drawing the tile from the space
        ItemTile itemTile = new ItemTile(TileType.CAT);
        testSpace1.setTile(itemTile);
        assertNotNull(testSpace1.getTile());

        // after drawing the tile from the space
        testSpace1.drawTile();
        assertNull(testSpace1.getTile());

        // exception about drawing a tile from an empty space
        IllegalAccessError exception2 = assertThrows(IllegalAccessError.class, () -> testSpace1.drawTile());

        String expectedMessage2 = "The selected space is free";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));

        // exception about drawing a tile from a forbidden space
        IllegalAccessError exception3 = assertThrows(IllegalAccessError.class, () -> testSpace2.drawTile());

        String expectedMessage3 = "The selected space is not playable";
        String actualMessage3 = exception3.getMessage();

        assertTrue(actualMessage3.contains(expectedMessage3));

    }

}