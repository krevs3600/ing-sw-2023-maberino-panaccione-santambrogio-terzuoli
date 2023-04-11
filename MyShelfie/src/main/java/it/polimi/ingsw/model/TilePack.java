package it.polimi.ingsw.model;

import java.util.*;

public class TilePack {
    private List<ItemTile> tiles;

    private static final int MAX_LENGTH = 3;
    public TilePack () {
        this.tiles = new ArrayList<ItemTile>();
    }

    public List<ItemTile> getTiles() {
        return this.tiles;
    }

    public void insertTile(ItemTile tile) {
        this.tiles.add(tile);
    }

    public void removeAllTiles() {
        for (int i = 0; i < MAX_LENGTH; i++) {
            this.removeLast();
        }
    }

    public void removeLast() {
        this.tiles.remove(this.getTiles().size()-1);
    }

    public void switchTiles (int position1, int position2) {
        ItemTile it = new ItemTile(this.getTiles().get(position1).getType());
        this.tiles.set(position1, this.tiles.get(position2));
        this.tiles.set(position2, it);
    }

}
