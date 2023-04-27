package it.polimi.ingsw.model;

import org.junit.Before;

import static org.junit.Assert.*;

public class CrossCommonGoalCardTest {
    private Bookshelf b1, b2, b3;
    private TilePack tp1, tp2, tp3, tp4, tp5;
    private ItemTile i1, i2, i3, i4, i5, i6, i7, i8, i9;
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
        c = new CrossCommonGoalCard(NumberOfPlayers.THREEPLAYERS, RomanNumber.ONE);
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
        tp1.insertTile(i5); //tp1-> cat book trophy
        tp2.insertTile(i6);
        tp2.insertTile(i9); //tp2-->plant book
        tp3.insertTile(i4);
        tp3.insertTile(i7);
        tp3.insertTile(i8);
        tp3.insertTile(i3); //tp3--> game book book

        b1.insertTile(tp1, 0);
        b1.insertTile(tp1, 0);
        b1.insertTile(tp3, 1);
        b1.insertTile(tp2, 1);


    }
}