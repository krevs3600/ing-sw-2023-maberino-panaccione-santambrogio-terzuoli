package it.polimi.ingsw.model;

public class EightTilesCommonGoalCard extends CommonGoalCard {

    public EightTilesCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=8;
    }

    public boolean CheckPattern (Bookshelf b) {
        boolean found=false;
        TileType[] tts = {TileType.CAT, TileType.BOOK, TileType.GAME, TileType.FRAME, TileType.TROPHY, TileType.PLANT};
        int counter;
        if (toBeChecked(b)) {
            for (TileType tt : tts) {
                counter=0;
                for (int i = 0; i < b.getMaxHeight() && !found; i++) {
                    for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                        if (b.getGrid()[i][j]!=null && b.getGrid()[i][j].getType().equals(tt)) {
                            counter++;
                        }
                    }
                }
                if (counter == 8) {
                    found = true;
                }
            }
        }


        return found;


    }
}









