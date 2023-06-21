package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCard.FourLinesCommonGoalCard;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FourLinesCommonGoalCardTest {
    private Bookshelf b1, b2, b3;
    private TilePack tp1, tp2, tp3, tp4, tp5, tp6, tp7,tp8,tp9,tp10,tp11,tp12,tp13,tp14,tp15,tp16,tp17,tp18,tp19,tp20;
    private ItemTile i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12,i13,i14,i15,i16,i17,i18,i19,i20,i21,i22,i23,i24,i25,i26,i27,i28,i29,i30,i31,i32,i33,i34,i35,i36,i37,i38,i39,i40,i41;
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


        c = new FourLinesCommonGoalCard();
        i1 = new ItemTile(TileType.CAT);
        i2 = new ItemTile(TileType.BOOK);
        i3 = new ItemTile(TileType.BOOK);
        i4 = new ItemTile(TileType.PLANT);
        i5 = new ItemTile(TileType.TROPHY);
        i6 = new ItemTile(TileType.BOOK);
        i7 = new ItemTile(TileType.GAME);
        i8 = new ItemTile(TileType.BOOK);
        i9 = new ItemTile(TileType.BOOK);
        i10 = new ItemTile(TileType.BOOK);
        i11 = new ItemTile(TileType.BOOK);
        i12 = new ItemTile(TileType.BOOK);
        i13 = new ItemTile(TileType.PLANT);
        i14 = new ItemTile(TileType.BOOK);
        i15 = new ItemTile(TileType.FRAME);
        i16 = new ItemTile(TileType.GAME);
        i17 = new ItemTile(TileType.FRAME);
        i18 = new ItemTile(TileType.GAME);
        i19 = new ItemTile(TileType.TROPHY);
        i20 = new ItemTile(TileType.BOOK);
        i21 = new ItemTile(TileType.TROPHY);
        i22 = new ItemTile(TileType.TROPHY);
        i23 = new ItemTile(TileType.TROPHY);


        tp1.insertTile(i1);//tp1--> CAT BOOK BOOK
        tp1.insertTile(i2);
        tp1.insertTile(i3);

        tp2.insertTile(i4); //Plant trophy BOOK
        tp2.insertTile(i5);
        tp2.insertTile(i6);

        tp3.insertTile(i8); //BOOK GAME BOOK
        tp3.insertTile(i7);
        tp3.insertTile(i9);

        tp6.insertTile(i10); //book

        tp7.insertTile(i11); //book book
        tp7.insertTile(i12);

        tp8.insertTile(i13); // plant book  frame
        tp8.insertTile(i14);
        tp8.insertTile(i15);

        tp9.insertTile(i16); // game frame game
        tp9.insertTile(i17);
        tp9.insertTile(i18);

        tp10.insertTile(i19); // trophy book
        tp10.insertTile(i20);

        tp11.insertTile(i21); //trophy trophy
        tp11.insertTile(i22);

        tp12.insertTile(i23); //trophy
        // random bookshelf

        b1.insertTile(tp1, 0);
        b1.insertTile(tp6, 0);

        b1.insertTile(tp8, 1);
        b1.insertTile(tp12, 1);


        b1.insertTile(tp7, 2);
        b1.insertTile(tp9, 2);

        b1.insertTile(tp2, 3);
        b1.insertTile(tp11, 3);

        b1.insertTile(tp3, 4);
        b1.insertTile(tp10, 4);



        i24 = new ItemTile(TileType.CAT);
        i25 = new ItemTile(TileType.BOOK);
        i26 = new ItemTile(TileType.BOOK);
        i27 = new ItemTile(TileType.BOOK );


        i28 = new ItemTile(TileType.GAME);
        i29 = new ItemTile(TileType.BOOK);
        i30 = new ItemTile(TileType.FRAME);



        i31 = new ItemTile(TileType.FRAME);
        i32 = new ItemTile(TileType.BOOK);
        i33 = new ItemTile(TileType.GAME);


        i34 = new ItemTile(TileType.PLANT);
        i35 = new ItemTile(TileType.TROPHY);
        i36 = new ItemTile(TileType.BOOK);
        i37= new ItemTile(TileType.TROPHY);

        i38 = new ItemTile(TileType.BOOK);
        i39 = new ItemTile(TileType.GAME);
        i40 = new ItemTile(TileType.BOOK);
        i41= new ItemTile(TileType.TROPHY);

        tp13.insertTile(i24);
        tp13.insertTile(i25);
        tp13.insertTile(i26);

        tp14.insertTile(i27);

        tp15.insertTile(i28);
        tp15.insertTile(i29);
        tp15.insertTile(i30);

        tp16.insertTile(i31);
        tp16.insertTile(i32);
        tp16.insertTile(i33);

        tp17.insertTile(i34);
        tp17.insertTile(i35);
        tp17.insertTile(i36);

        tp18.insertTile(i37);

        tp19.insertTile(i38);
        tp19.insertTile(i39);
        tp19.insertTile(i40);

        tp20.insertTile(i41);


        b3.insertTile(tp13,0);
        b3.insertTile(tp14,0);

        b3.insertTile(tp15,1);

        b3.insertTile(tp16,2);

        b3.insertTile(tp17,3);
        b3.insertTile(tp18,3);

        b3.insertTile(tp19,4);
        b3.insertTile(tp20,4);
    }
    // bookshelf with 8 tiles but not 8 tiles of the same type

    @Test
    public  void correctCheckPattern(){
        assertTrue(c.checkPattern(b1));// check on a random configuration
        assertFalse(c.checkPattern(b2));
        assertFalse(c.checkPattern(b3));// check on an empty bookshelf
    }
}