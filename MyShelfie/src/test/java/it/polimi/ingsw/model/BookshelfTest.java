package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.TwoSquaresCommonGoalCard;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BookshelfTest {

    private Bookshelf b;
    private TilePack tp;
    private ItemTile it1, it2, it3, it4, it5, it6;
    @Before
    public void setUp() {
        b = new Bookshelf();
        tp = new TilePack();
        it1 = new ItemTile(TileType.CAT);
        it2 = new ItemTile(TileType.BOOK);
        it3 = new ItemTile(TileType.GAME);
        it4 = new ItemTile(TileType.TROPHY);
        it5 = new ItemTile(TileType.FRAME);
        it6 = new ItemTile(TileType.PLANT);
    }

    @Test
    public void isFullTest() {

        // empty bookshelf
        assertFalse(b.isFull());

        // not empty nor full bookshelf
        tp.insertTile(it1);
        tp.insertTile(it2);
        tp.insertTile(it3);
        for (int i=0; i<3; i++) {
            b.insertTile(tp, 0, 0);
        }
        assertFalse(b.isFull());

        // full bookshelf
        for (int k=0; k<b.getMaxWidth(); k++) {
            while (b.getNumberInsertableTilesColumn(k)>0) {
                tp.insertTile(it1);
                tp.insertTile(it2);
                tp.insertTile(it3);
                for (int i = 0; i < 3; i++) {
                    b.insertTile(tp, k, 0);
                }
            }
        }

        assertTrue(b.isFull());

    }

    @Test
    public void getNumberInsertableTilesColumnTest() {

        // empty column
        assertEquals(6, b.getNumberInsertableTilesColumn(0));

        // not empty nor full column
        tp.insertTile(it1);
        tp.insertTile(it2);
        tp.insertTile(it3);
        for (int i=0; i<3; i++) {
            b.insertTile(tp, 0, 0);
        }
        assertEquals(3, b.getNumberInsertableTilesColumn(0));

        // full column
        tp.insertTile(it1);
        tp.insertTile(it2);
        tp.insertTile(it3);
        for (int i = 0; i < 3; i++) {
            b.insertTile(tp, 0, 0);
        }
        assertEquals(0, b.getNumberInsertableTilesColumn(0));
    }

    @Test
    public void getNumberInsertableTilesTest() {
        // empty bookshelf
        assertEquals(6, b.getNumberInsertableTiles());

        // not empty nor full bookshelf
        for (int k=0; k<b.getMaxWidth(); k++) {
            tp.insertTile(it1);
            tp.insertTile(it2);
            tp.insertTile(it3);
            for (int i = 0; i < 3; i++) {
                b.insertTile(tp, k, 0);
            }
        }
        assertEquals(3, b.getNumberInsertableTiles());

        // full bookshelf
        for (int k=0; k<b.getMaxWidth(); k++) {
            while (b.getNumberInsertableTilesColumn(k)>0) {
                tp.insertTile(it1);
                tp.insertTile(it2);
                tp.insertTile(it3);
                for (int i = 0; i < 3; i++) {
                    b.insertTile(tp, k, 0);
                }
            }
        }

        assertEquals(0, b.getNumberInsertableTiles());

    }

    @Test
    public void correctInsertTiles() {
          it6=new ItemTile(TileType.CAT);
          tp.insertTile(it6);
            /*assertThrows(IndexOutOfBoundsException.class,
                    () -> {
                        b.insertTile(tp, 0); // insert tiles in full column
            });

             */

            //todo altri casi
        }

    @Test
    public void correctGetNumberAdjacentTiles() {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(4, 1);
        m.put(1, 2);
        m.put(5, 1);

        assertEquals(m, b.getNumberAdjacentTiles(TileType.GAME));
    }
}