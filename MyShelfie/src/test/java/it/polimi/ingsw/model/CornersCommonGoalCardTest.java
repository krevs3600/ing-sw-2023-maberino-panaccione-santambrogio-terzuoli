package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CornersCommonGoalCardTest {
    private Bookshelf b1,b2,b3;
    private TilePack tp1, tp2, tp3, tp4, tp5;
    private ItemTile i1, i2, i3, i4, i5, i6, i7, i8, i9;
    private CommonGoalCard c;

    @Before
    public void setUp() {
        b1 = new Bookshelf();
        b2=new Bookshelf();
        b3=new Bookshelf();
        tp1 = new TilePack();
        tp2=new TilePack();
        tp3=new TilePack();
        tp4=new TilePack();
        tp5=new TilePack();
        c=new CornersCommonGoalCard(NumberOfPlayers.THREEPLAYERS,RomanNumber.ONE);
        i1 = new ItemTile(TileType.CAT);
        i2 = new ItemTile(TileType.BOOK);
        i3 = new ItemTile(TileType.BOOK);
        i4 = new ItemTile(TileType.FRAME);
        i5 = new ItemTile(TileType.TROPHY);
        i6 = new ItemTile(TileType.PLANT);
        i7 = new ItemTile(TileType.GAME);
        i8 = new ItemTile(TileType.BOOK);
        i9 = new ItemTile(TileType.BOOK);
        tp1.insertTile(i1);//tp1--> CAT BOOK BOOK
        tp1.insertTile(i2);
        tp1.insertTile(i3);
        tp2.insertTile(i4);//tp2--> FRAME TROPHY PLANT
        tp2.insertTile(i5);
        tp2.insertTile(i6);
        tp3.insertTile(i7);//tp3--> GAME BOOK BOOK
        tp3.insertTile(i8);
        tp3.insertTile(i9);
        tp4.insertTile(i1);//tp4--> CAT BOOK
        tp4.insertTile(i2);
        tp5.insertTile(i8);//tp5--> BOOK
        b1.insertTile(tp5, 0);
        b1.insertTile(tp4, 0);
        b1.insertTile(tp1, 0);

        b1.insertTile(tp5, 4);
        b1.insertTile(tp4, 4);
        b1.insertTile(tp1, 4);


        b2.insertTile(tp5, 0);
        b2.insertTile(tp4, 0);
        b2.insertTile(tp2, 0); // only one corner different from the others

        b2.insertTile(tp5, 4);
        b2.insertTile(tp4, 4);
        b2.insertTile(tp1, 4);

        b3.insertTile(tp5, 4); // in b3 there is an  empty first column
        b3.insertTile(tp4, 4);
        b3.insertTile(tp1, 4);
    }

    @Test
    public  void correctCheckPattern(){
        assertTrue(c.CheckPattern(b1));
        assertTrue(c.CheckPattern(b2));
        assertTrue(c.CheckPattern(b3)); //return false not the exception
    }
}