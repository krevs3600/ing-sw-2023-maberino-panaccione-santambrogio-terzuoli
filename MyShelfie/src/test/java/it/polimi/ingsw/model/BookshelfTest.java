package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BookshelfTest {

    private Bookshelf b1, b2, b3, b4, b5;
    private TilePack tp1, tp2, tp3, tp4, tp5;

    private ItemTile i1, i2, i3, i4, i5, i6, i7, i8, i9;

    @Before
    public void setUp() {
        b1 = new Bookshelf();
        b2 = new Bookshelf();
        b3 = new Bookshelf();
        b4 = new Bookshelf();
        b5 = new Bookshelf();
        tp1 = new TilePack();
        tp2 = new TilePack();
        tp3 = new TilePack();
        tp4 = new TilePack();
        tp5 = new TilePack();
        i1 = new ItemTile(TileType.CAT);
        i2 = new ItemTile(TileType.BOOK);
        i3 = new ItemTile(TileType.BOOK);
        i4 = new ItemTile(TileType.FRAME);
        i5 = new ItemTile(TileType.TROPHY);
        i6 = new ItemTile(TileType.PLANT);
        i7 = new ItemTile(TileType.GAME);
        i8 = new ItemTile(TileType.BOOK);
        i9 = new ItemTile(TileType.BOOK);
        tp1.insertTile(i1);
        tp1.insertTile(i2);
        tp1.insertTile(i3);
        tp2.insertTile(i4);
        tp2.insertTile(i5);
        tp2.insertTile(i6);
        tp3.insertTile(i7);
        tp3.insertTile(i8);
        tp3.insertTile(i9);
        tp4.insertTile(i1);
        tp4.insertTile(i2);
        tp5.insertTile(i8);
        b2.insertTile(tp1, 1);
        b2.insertTile(tp1, 1);
        b2.insertTile(tp2, 2);
        b2.insertTile(tp2, 2);
        b2.insertTile(tp3, 3);
        b2.insertTile(tp3, 3);
        b2.insertTile(tp2, 4);
        b2.insertTile(tp2, 4);
        b2.insertTile(tp1, 0);
        b2.insertTile(tp1, 0);
        b3.insertTile(tp1, 2);
        b3.insertTile(tp2, 2);
        b4.insertTile(tp1, 0);
        b4.insertTile(tp2, 1);
        b4.insertTile(tp3, 2);
        b4.insertTile(tp1, 3);
        b4.insertTile(tp2, 4);
        b5.insertTile(tp1, 0); // column 0 has 3 tiles
        b5.insertTile(tp4, 1);
        b5.insertTile(tp1, 1); //column 1 has 5 tiles
        b5.insertTile(tp5, 2); //column 2 has 1 tile

    }

    @Test
    public void correctIsFull() {
        assertFalse(b1.isFull());
        assertTrue(b2.isFull());
        assertFalse(b3.isFull());
        assertFalse(b4.isFull());
        assertFalse(b5.isFull());
    }

    @Test
    public void correctGetNumberInsertableTiles() {

        for (int i = 0; i < 5; i++) {
            assertEquals(6, b1.getNumberInsertableTilesColumn(i));
            assertEquals(0, b2.getNumberInsertableTilesColumn(i));
            assertEquals(3, b4.getNumberInsertableTilesColumn(i));
        }
        assertEquals(0, b3.getNumberInsertableTilesColumn(2));
        assertEquals(1, b5.getNumberInsertableTilesColumn(1));
        assertEquals(5, b5.getNumberInsertableTilesColumn(2));

        assertEquals(6, b1.getNumberInsertableTiles());
        assertEquals(0, b2.getNumberInsertableTiles());
        assertEquals(6, b3.getNumberInsertableTiles());
        assertEquals(3, b4.getNumberInsertableTiles());
        assertEquals(6, b5.getNumberInsertableTiles());
    }

    @Test
    public void correctInsertTiles() {
        for (int m = 0; m < 5; m++) {
            int finalM = m;

            assertThrows(IndexOutOfBoundsException.class,
                    () -> {
                        b2.insertTile(tp1, finalM); // insert tiles in full column
                    });
        }
        assertThrows(IndexOutOfBoundsException.class,
                () -> {
                    b5.insertTile(tp1, 1); // insert tiles exceeding the number of insertable tiles for the specific column
                });
        for (int i = 0; i < tp1.getTiles().size(); i++) {
            assertEquals(tp1.getTiles().get(i).getType(), b2.getGrid()[5 - i][0].getType());
            assertEquals(tp1.getTiles().get(i).getType(), b2.getGrid()[tp1.getTiles().size()-1-i][0].getType());
        }

        for (int i = 0; i < tp1.getTiles().size(); i++) {
            assertEquals(tp1.getTiles().get(i).getType(), b2.getGrid()[5 - i][1].getType());
            assertEquals(tp1.getTiles().get(i).getType(), b2.getGrid()[tp1.getTiles().size()-1-i][1].getType());
        }
        for (int i = 0; i < tp2.getTiles().size(); i++) {
            assertEquals(tp2.getTiles().get(i).getType(), b2.getGrid()[5 - i][2].getType());
            assertEquals(tp2.getTiles().get(i).getType(), b2.getGrid()[tp2.getTiles().size()-1-i][2].getType());
        }
        for (int i = 0; i < tp3.getTiles().size(); i++) {
            assertEquals(tp3.getTiles().get(i).getType(), b2.getGrid()[5 - i][3].getType());
            assertEquals(tp3.getTiles().get(i).getType(), b2.getGrid()[tp3.getTiles().size()-1-i][3].getType());
        }
        for (int i = 0; i < tp2.getTiles().size(); i++) {
            assertEquals(tp2.getTiles().get(i).getType(), b2.getGrid()[5 - i][4].getType());
            assertEquals(tp2.getTiles().get(i).getType(), b2.getGrid()[tp2.getTiles().size()-1-i][4].getType());
        }
        assertNull(b1.getGrid()[5][0]);
    }
    @Test
    public void correctGetNumberAdjacentTiles() {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(2, 2);
        m.put(4, 2);
        assertEquals(m, b2.getNumberAdjacentTiles(TileType.BOOK));
        Map<Integer, Integer> m1 = new HashMap<>();
        m1.put(2, 2);
        assertEquals(m1, b2.getNumberAdjacentTiles(TileType.CAT));
        Bookshelf b8 = new Bookshelf();
        for (int i = 0; i < 5; i++) {
            b8.insertTile(tp1, i);
            b8.insertTile(tp1, i);
        }
        Map<Integer, Integer> m2 = new HashMap<>();
        m2.put(5, 2);
        assertEquals(m2, b8.getNumberAdjacentTiles(TileType.CAT));
        Bookshelf b9 = new Bookshelf();
        i1 = new ItemTile(TileType.CAT);
        i2 = new ItemTile(TileType.BOOK);
        i3 = new ItemTile(TileType.BOOK);
        i4 = new ItemTile(TileType.FRAME);
        i5 = new ItemTile(TileType.TROPHY);
        i6 = new ItemTile(TileType.PLANT);
        i7 = new ItemTile(TileType.GAME);
        i8 = new ItemTile(TileType.BOOK);
        i9 = new ItemTile(TileType.BOOK);
        TilePack tp10=new TilePack();
        tp10.insertTile(i1);
        tp10.insertTile(i3);
        tp10.insertTile(i4); // t10-->CAT BOOK FRAME

            b9.insertTile(tp10,0);
            b9.insertTile(tp1,0);

        b9.insertTile(tp1,1);
        b9.insertTile(tp10,1);

        b9.insertTile(tp10,2);
        b9.insertTile(tp1,2);

        b9.insertTile(tp1,3);
        b9.insertTile(tp10,3);

        b9.insertTile(tp10,4);
        b9.insertTile(tp1,4);

        Map<Integer, Integer> m3 = new HashMap<>();
        m3.put(1,5);
        assertEquals(m3, b9.getNumberAdjacentTiles(TileType.FRAME));


    }
}