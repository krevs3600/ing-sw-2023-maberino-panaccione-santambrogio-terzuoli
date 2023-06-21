package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TilePackTest {

    private TilePack testTilePack;

    @Before
    public void setUp(){
        testTilePack = new TilePack();
    }

    @Test
    public void insertTileTest() {

        // before insertion: empty tile pack
        assertEquals(0, testTilePack.getSize());

        // after insertion
        ItemTile itemTile = new ItemTile(TileType.CAT);
        testTilePack.insertTile(itemTile);
        assertEquals(itemTile, testTilePack.getTiles().get(0));
        assertEquals(1, testTilePack.getSize());

        // exception about inserting too many tiles exceeding the limit
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        testTilePack.insertTile(new ItemTile(TileType.CAT));
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> testTilePack.insertTile(new ItemTile(TileType.CAT)));

        String expectedMessage = "Inserting too many tiles in the tile pack";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}