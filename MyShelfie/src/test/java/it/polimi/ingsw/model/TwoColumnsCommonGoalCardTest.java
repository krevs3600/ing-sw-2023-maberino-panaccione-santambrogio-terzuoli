package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoColumnsCommonGoalCardTest {
    private Bookshelf b1, b2, b3;
    private TilePack  tp1, tp2, tp3, tp4, tp5, tp6, tp7,tp8,tp9,tp10,tp11,tp12,tp13,tp14,tp15,tp16,tp17,tp18,tp19,tp20;
    private ItemTile i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20,i21,i22,i23,i24,i25,i26,i27,i28,i29,i30,i31,i32,i33,i34,i35,i36,i37,i38,i39,i40,i41,i42,i43;
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
        tp11 = new TilePack();
        tp12 = new TilePack();
        tp13 = new TilePack();
        tp14 = new TilePack();
        tp15 = new TilePack();
        tp16 = new TilePack();
        tp17 = new TilePack();
        tp18 = new TilePack();
        tp19 = new TilePack();
        tp20 = new TilePack();


        c = new TwoColumnsCommonGoalCard();

        i1 = new ItemTile(TileType.PLANT);
        i2 = new ItemTile(TileType.FRAME);
        i3 = new ItemTile(TileType.GAME);
        i4 = new ItemTile(TileType.CAT);
        i5 = new ItemTile(TileType.TROPHY);
        i6 = new ItemTile(TileType.BOOK);


        i7 = new ItemTile(TileType.TROPHY);
        i8 = new ItemTile(TileType.BOOK);
        i9 = new ItemTile(TileType.CAT);
        i10 = new ItemTile(TileType.FRAME);
        i11 = new ItemTile(TileType.GAME);
        i12 = new ItemTile(TileType.PLANT);

        i13 = new ItemTile(TileType.PLANT);
        i14 = new ItemTile(TileType.GAME);





        tp1.insertTile(i1);
        tp1.insertTile(i2);
        tp1.insertTile(i3);



        tp2.insertTile(i4);
        tp2.insertTile(i5);
        tp2.insertTile(i6);


        tp3.insertTile(i7);
        tp3.insertTile(i8);
        tp3.insertTile(i9);

        tp4.insertTile(i10);
        tp4.insertTile(i11);
        tp4.insertTile(i12);

        tp5.insertTile(i13);
        tp5.insertTile(i14);






        b1.insertTile(tp1,0);
        b1.insertTile(tp2,0);
        b1.insertTile(tp5,2);
        b1.insertTile(tp3,3);
        b1.insertTile(tp4,3);

        i23 = new ItemTile(TileType.GAME);
        i34 = new ItemTile(TileType.CAT);


        i24 = new ItemTile(TileType.GAME);
        i26 = new ItemTile(TileType.GAME);
        i27 = new ItemTile(TileType.TROPHY);

        i28 = new ItemTile(TileType.GAME);
        i29 = new ItemTile(TileType.TROPHY);
        i40 = new ItemTile(TileType.GAME);
        i25= new ItemTile(TileType.FRAME);


        i30 = new ItemTile(TileType.CAT);
        i31 = new ItemTile(TileType.BOOK);
        i32 = new ItemTile(TileType.TROPHY);
        i33 = new ItemTile(TileType.GAME);
        i42 = new ItemTile(TileType.CAT);

        i35 = new ItemTile(TileType.TROPHY);
        i36 = new ItemTile(TileType.GAME);
        i37 = new ItemTile(TileType.FRAME);
        i38 = new ItemTile(TileType.CAT);
        i39 = new ItemTile(TileType.BOOK);
        i41 = new ItemTile(TileType.PLANT);

        tp9.insertTile(i23);
        tp9.insertTile(i34);


        tp10.insertTile(i24);
        tp10.insertTile(i26);
        tp10.insertTile(i27);


        tp11.insertTile(i28);
        tp11.insertTile(i29);
        tp11.insertTile(i40);
        tp16.insertTile(i25);


        tp12.insertTile(i30);
        tp12.insertTile(i31);
        tp12.insertTile(i32);


        tp13.insertTile(i33);
        tp13.insertTile(i42);

        tp14.insertTile(i35);
        tp14.insertTile(i36);
        tp14.insertTile(i37);


        tp15.insertTile(i38);
        tp15.insertTile(i39);
        tp15.insertTile(i41);



        b3.insertTile(tp9,0 );
        b3.insertTile(tp10,1 );
        b3.insertTile(tp11,2 );
        b3.insertTile(tp16,2 );

        b3.insertTile(tp12,3);
        b3.insertTile(tp13,3);

        b3.insertTile(tp14,4);
        b3.insertTile(tp15,4);
    }

    @Test
    public  void correctCheckPattern(){
        assertTrue(c.CheckPattern(b1));
        assertFalse(c.CheckPattern(b2));
        assertFalse(c.CheckPattern(b3));
    }

}