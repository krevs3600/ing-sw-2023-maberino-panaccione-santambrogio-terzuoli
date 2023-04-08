package it.polimi.ingsw.model;

import java.util.Map;

public class FourGroupsCommonGoalCard extends CommonGoalCard {

    public FourGroupsCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles() >= 16;
    }

    public boolean CheckPattern (Bookshelf b) {
        TileType[] tts = {TileType.CAT, TileType.BOOK, TileType.GAME, TileType.FRAME, TileType.TROPHY, TileType.PLANT};
        if (this.toBeChecked(b)) {
            int counter = 0;

            for (TileType tt : tts) {
                Map m = b.getNumberAdjacentTiles(tt);
                for (int i = 4; i < b.getNumberOfTiles(); i++) {
                    counter += (int) m.get(i); // casting!!!
                }
            }
            return counter >= 4;
        }
        else return false;
    }
}
