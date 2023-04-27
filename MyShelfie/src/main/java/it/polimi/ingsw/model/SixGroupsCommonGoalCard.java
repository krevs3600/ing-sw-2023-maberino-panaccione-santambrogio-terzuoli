package it.polimi.ingsw.model;

import java.util.Map;

public class SixGroupsCommonGoalCard extends CommonGoalCard {

    public SixGroupsCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles() >= 12;
    }

    public boolean CheckPattern (Bookshelf b) {
        TileType[] tts = {TileType.CAT, TileType.BOOK, TileType.GAME, TileType.FRAME, TileType.TROPHY, TileType.PLANT};
        if (this.toBeChecked(b)) {
            int counter = 0;

            for (TileType tt : tts) {
                Map m = b.getNumberAdjacentTiles(tt);
                for (int i = 2; i < b.getNumberOfTiles(); i++) {
                    if (m.containsKey(i)) {
                        counter += (int) m.get(i); // casting!!!
                    }
                }
                return counter >= 6;
            }
        }
         return false;
    }
}
