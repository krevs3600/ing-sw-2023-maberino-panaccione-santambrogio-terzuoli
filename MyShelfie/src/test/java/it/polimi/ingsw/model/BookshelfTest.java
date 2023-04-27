package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BookshelfTest {
 @Test
    public void correctNumberofInsertableTilesForOneColumn(){
     Bookshelf b=new Bookshelf();
     Map<Integer, Integer> m = new HashMap<>();
     TilePack tp=new TilePack();
     TilePack tp1=new TilePack();
     TilePack tp2=new TilePack();
     ItemTile i1=new ItemTile(TileType.CAT);
     ItemTile i2=new ItemTile(TileType.BOOK);
     ItemTile i3=new ItemTile(TileType.BOOK);
     tp.insertTile(i1);
     tp.insertTile(i2);
     tp.insertTile(i3);
     tp1.insertTile(i1);
     tp1.insertTile(i2);
     tp1.insertTile(i3);
     tp2.insertTile(i1);
     tp2.insertTile(i2);
     tp2.insertTile(i3);
     b.insertTile(tp,1);
     b.insertTile(tp1,2);
     b.insertTile(tp2,3);
     assertEquals(6 ,b.getNumberInsertableTilesColumn(1));
     assertEquals(m,b.getNumberAdjacentTiles(TileType.BOOK));
 }
    @Test
    public void AllEmptyColumn(){
     Bookshelf b=new Bookshelf();
        assertEquals(6,b.getNumberInsertableTiles());

    }

 @Test
 public void AdjacentTest(){
  Bookshelf b=new Bookshelf();
  Map<Integer, Integer> m = new HashMap<>();
  TilePack tp=new TilePack();
  TilePack tp1=new TilePack();
  TilePack tp2=new TilePack();
  ItemTile i1=new ItemTile(TileType.CAT);
  ItemTile i2=new ItemTile(TileType.BOOK);
  ItemTile i3=new ItemTile(TileType.BOOK);
  tp.insertTile(i1);
  tp.insertTile(i2);
  tp.insertTile(i3);
  tp1.insertTile(i1);
  tp1.insertTile(i2);
  tp1.insertTile(i3);
  tp2.insertTile(i1);
  tp2.insertTile(i2);
  tp2.insertTile(i3);
  b.insertTile(tp,1);
  b.insertTile(tp1,2);
  b.insertTile(tp2,3);
  m.put(6,1);
  assertEquals(m,b.getNumberAdjacentTiles(TileType.BOOK));



 }
}