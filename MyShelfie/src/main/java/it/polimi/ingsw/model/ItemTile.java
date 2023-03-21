package it.polimi.ingsw.model;

public class ItemTile {
    private TileType type;

    public ItemTile(TileType type){
        this.type = type;
    }

    public TileType getType() {
        return type;
    }
}
