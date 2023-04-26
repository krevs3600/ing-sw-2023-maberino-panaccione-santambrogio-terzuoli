package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.TileType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import static org.junit.Assert.*;

public class SpaceTest{

    @Test
    public void setTileTestFree(){
        Position pos = new Position(5,5);
        Space cell = new Space(SpaceType.DEFAULT, pos);
        cell.setTile(new ItemTile(TileType.CAT));
        assertTrue(!cell.isFree());
    }


    @Test
    public void setTileTestTile(){
        Position pos = new Position(5,5);
        Space cell = new Space(SpaceType.DEFAULT, pos);
        ItemTile tile = new ItemTile(TileType.CAT);
        cell.setTile(new ItemTile(TileType.CAT));
        assertEquals(cell.getTile().getType(), tile.getType());
    }

    @Test
    public void drawTileTest(){
        Position pos = new Position(5,5);
        Space cell = new Space(SpaceType.DEFAULT, pos);
        cell.setTile(new ItemTile(TileType.CAT));
        cell.drawTile();
        assertTrue(cell.isFree());
    }


    @Test
    public void drawTileTestTile(){
        Position pos = new Position(5,5);
        Space cell = new Space(SpaceType.DEFAULT, pos);
        cell.setTile(new ItemTile(TileType.CAT));
        ItemTile tile = new ItemTile(TileType.CAT);
        assertEquals(cell.drawTile().getType(), tile.getType());
    }

    @Test
    public void getTileFromFreeCell(){
        Position pos = new Position(5,5);
        Space cell = new Space(SpaceType.DEFAULT, pos);
        cell.setTile(new ItemTile(TileType.CAT));
        cell.drawTile();
        assertThrows(IllegalAccessError.class, () -> {
            cell.getTile();
        });
    }

    @Test
    public void getTileFromForbiddenCell() {
        Position pos = new Position(5, 5);
        Space cell = new Space(SpaceType.FORBIDDEN, pos);
        assertThrows(IllegalAccessError.class, () -> {
            cell.getTile();
        });
    }

    @Test
    public void drawTileFromFreeCell(){
        Position pos = new Position(5,5);
        Space cell = new Space(SpaceType.DEFAULT, pos);
        cell.setTile(new ItemTile(TileType.CAT));
        cell.drawTile();
        assertThrows(IllegalAccessError.class, () -> {
            cell.drawTile();
        });
    }

    @Test
    public void drawTileFromForbiddenCell() {
        Position pos = new Position(5, 5);
        Space cell = new Space(SpaceType.FORBIDDEN, pos);
        assertThrows(IllegalAccessError.class, () -> {
            cell.drawTile();
        });
    }


    //!! non va!!
    @Test
    public void SetTileOnOccupiedCell(){
        Position pos = new Position(5,5);
        Space cell = new Space(SpaceType.DEFAULT, pos);
        cell.setTile(new ItemTile(TileType.CAT));
        assertThrows(IllegalAccessError.class, () -> {
            cell.setTile(new ItemTile(TileType.BOOK));
        });
    }

    @Test
    public void setTileOnForbiddenCell(){
        Position pos = new Position(5, 5);
        Space cell = new Space(SpaceType.FORBIDDEN, pos);
        assertThrows(IllegalAccessError.class, () -> {
            cell.setTile(new ItemTile(TileType.CAT));
        });
    }



}