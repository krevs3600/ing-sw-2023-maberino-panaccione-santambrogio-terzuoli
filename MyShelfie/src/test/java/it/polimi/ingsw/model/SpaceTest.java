package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import it.polimi.ingsw.model.utils.TileType;
import javafx.geometry.Pos;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class SpaceTest{

    private Position pos1;
    private Position pos2;
    private Space cell1; //PLAYABLE
    private Space cell2; //FORBIDDEN
    private ItemTile tile;

    @Before
    public void setUp(){
        pos1 = new Position(5,5);
        cell1 = new Space(SpaceType.PLAYABLE, pos1);
        pos2 = new Position(4, 4);
        cell2 = new Space(SpaceType.FORBIDDEN, pos2);
        tile = new ItemTile(TileType.CAT);
    }

    @Test
    public void setTileTestFree(){
        cell1.setTile(new ItemTile(TileType.CAT));
        assertFalse(cell1.isFree());
    }


    @Test
    public void setTileTestTile(){
        cell1.setTile(new ItemTile(TileType.CAT));
        assertEquals(cell1.getTile().getType(), tile.getType());
    }

    @Test
    public void drawTileTest(){
        cell1.setTile(new ItemTile(TileType.CAT));
        cell1.drawTile();
        assertTrue(cell1.isFree());
    }


    @Test
    public void drawTileTestTile(){
        cell1.setTile(new ItemTile(TileType.CAT));
        assertEquals(cell1.drawTile().getType(), tile.getType());
    }

    /**@Test
    public void getTileFromFreeCell(){
        Position pos = new Position(5,5);
        Space cell = new Space(SpaceType.PLAYABLE, pos);
        cell.setTile(new ItemTile(TileType.CAT));
        cell.drawTile();
        assertThrows(IllegalAccessError.class, cell::getTile);
    }

    @Test
    public void getTileFromForbiddenCell() {
        Position pos = new Position(5, 5);
        Space cell = new Space(SpaceType.FORBIDDEN, pos);
        assertThrows(IllegalAccessError.class, cell::getTile);
    }
     */

    @Test
    public void drawTileFromFreeCell(){
        cell1.setTile(new ItemTile(TileType.CAT));
        cell1.drawTile();
        assertThrows(IllegalAccessError.class, cell1::drawTile);
    }

    @Test
    public void drawTileFromForbiddenCell() {
        assertThrows(IllegalAccessError.class, cell2::drawTile);
    }


    //!! non va!!
    @Test
    public void SetTileOnOccupiedCell(){
        cell1.setTile(new ItemTile(TileType.CAT));
        assertThrows(IllegalAccessError.class, () -> {
            cell1.setTile(new ItemTile(TileType.BOOK));
        });
    }

    @Test
    public void setTileOnForbiddenCell(){
        assertThrows(IllegalAccessError.class, () -> {
            cell2.setTile(new ItemTile(TileType.CAT));
        });
    }

    @Test
    public void getTileFromForbiddenSpace(){
        assertEquals(null, cell2.getTile());
    }
    @Test
    public void getTileFromFreeSpace(){
        cell1.setTile(new ItemTile(TileType.CAT));
        cell1.drawTile();
        assertEquals(null, cell1.getTile());
    }


}