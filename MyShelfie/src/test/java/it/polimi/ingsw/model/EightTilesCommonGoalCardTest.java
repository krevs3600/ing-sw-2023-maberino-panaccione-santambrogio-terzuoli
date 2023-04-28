package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EightTilesCommonGoalCardTest {
    private Bookshelf b1, b2, b3;
    private TilePack tp1, tp2, tp3, tp4, tp5, tp6, tp7, tp8, tp9, tp10;
    private ItemTile i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20;
    private CommonGoalCard c;

    @Before
    public void setUp() {
        b1 = new Bookshelf();
        b2 = new Bookshelf();
        b3 = new Bookshelf();
        tp1 = new TilePack();
        tp2 = new TilePack();
        tp3 = new TilePack();
        tp4 = new TilePack();
        tp5 = new TilePack();
        tp6 = new TilePack();
        tp7 = new TilePack();
        tp8 = new TilePack();
        tp9 = new TilePack();
        tp10 = new TilePack();

        c = new EightTilesCommonGoalCard(NumberOfPlayers.THREEPLAYERS, RomanNumber.ONE);
        i1 = new ItemTile(TileType.CAT);
        i2 = new ItemTile(TileType.BOOK);
        i3 = new ItemTile(TileType.BOOK);
        i4 = new ItemTile(TileType.FRAME);
        i5 = new ItemTile(TileType.TROPHY);
        i6 = new ItemTile(TileType.BOOK);
        i7 = new ItemTile(TileType.GAME);
        i8 = new ItemTile(TileType.BOOK);
        i9 = new ItemTile(TileType.BOOK);
        i10 = new ItemTile(TileType.BOOK);
        i11 = new ItemTile(TileType.BOOK);
        i12 = new ItemTile(TileType.BOOK);
        i13 = new ItemTile(TileType.PLANT);
        i14 = new ItemTile(TileType.TROPHY);
        i15 = new ItemTile(TileType.FRAME);
        i16 = new ItemTile(TileType.GAME);
        i17 = new ItemTile(TileType.FRAME);
        i18 = new ItemTile(TileType.GAME);
        i19 = new ItemTile(TileType.TROPHY);
        i20 = new ItemTile(TileType.BOOK);


        tp1.insertTile(i1);//tp1--> CAT BOOK BOOK
        tp1.insertTile(i2);
        tp1.insertTile(i3);

        tp2.insertTile(i4); //Frame trophy BOOK
        tp2.insertTile(i5);
        tp2.insertTile(i6);

        tp3.insertTile(i8); //BOOK GAME BOOK
        tp3.insertTile(i7);
        tp3.insertTile(i9);

        tp6.insertTile(i10); //book

        tp7.insertTile(i11); //book book
        tp7.insertTile(i12);

        tp8.insertTile(i13); // plant trophy  frame
        tp8.insertTile(i14);
        tp8.insertTile(i15);

        tp9.insertTile(i16); // game frame game
        tp9.insertTile(i17);
        tp9.insertTile(i18);

        tp10.insertTile(i19); // trophy book
        tp10.insertTile(i20);


        // random bookshelf

        b1.insertTile(tp1, 0);
        b1.insertTile(tp6, 0);

        b1.insertTile(tp7, 2);

        b1.insertTile(tp2, 3);

        b1.insertTile(tp3, 4);

        // bookshelf with 8 tiles but not 8 tiles of the same type

        b3.insertTile(tp8, 0);
        b3.insertTile(tp9, 4);
        b3.insertTile(tp10, 2);

    }

    @Test
    public void correctCheckPattern() {
        assertTrue(c.CheckPattern(b1)); // check on a random configuration
        assertFalse(c.CheckPattern(b2)); // check on a totally empty book ( the ifchecked is  false)
        assertFalse(c.CheckPattern(b3));// check bookshelf with 8 tiles but not of the same type

    }

}