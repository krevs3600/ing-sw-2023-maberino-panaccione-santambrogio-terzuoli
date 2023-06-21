package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.EightTilesCommonGoalCard;
import it.polimi.ingsw.model.utils.TileType;
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

        c = new EightTilesCommonGoalCard();
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


        tp1.insertTile(i1);
        tp1.insertTile(i2);
        tp1.insertTile(i3);

        tp2.insertTile(i4);
        tp2.insertTile(i5);
        tp2.insertTile(i6);

        tp3.insertTile(i8);
        tp3.insertTile(i7);
        tp3.insertTile(i9);

        tp6.insertTile(i10);

        tp7.insertTile(i11);
        tp7.insertTile(i12);

        tp8.insertTile(i13);
        tp8.insertTile(i14);
        tp8.insertTile(i15);

        tp9.insertTile(i16);
        tp9.insertTile(i17);
        tp9.insertTile(i18);

        tp10.insertTile(i19);
        tp10.insertTile(i20);


        // random bookshelf

        b1.insertTile(tp1, 0);
        b1.insertTile(tp6, 0);

        b1.insertTile(tp7, 2);

        b1.insertTile(tp2, 3);

        b1.insertTile(tp3, 4);

        b3.insertTile(tp8, 0);
        b3.insertTile(tp9, 4);
        b3.insertTile(tp10, 2);

    }

    @Test
    public void correctCheckPattern() {
        assertTrue(c.checkPattern(b1)); // all conditions are true
        assertFalse(c.checkPattern(b2));  // toBeChecked condition false (empty bookshelf)
        assertFalse(c.checkPattern(b3));// toBeChecked condition true but checkPattern conditions false (bookshelf with wight tiles but not of the same type)

    }

}