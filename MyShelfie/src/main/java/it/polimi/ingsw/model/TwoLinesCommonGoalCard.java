package it.polimi.ingsw.model;

public class TwoLinesCommonGoalCard extends CommonGoalCard{
    public TwoLinesCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=10;
    }
public boolean differentTypesInOneline(Bookshelf b,int i) {
    boolean found = true;
    for (int j = 0; j < b.getMaxWidth() && found; j++) {
        for (int k = j + 1; k < b.getMaxWidth() && found; k++) {
            if (b.getGrid()[i][j].equals(b.getGrid()[i][k])) {
                found = false;
            }
        }
    }
    return found;
}




    public boolean CheckPattern (Bookshelf b) {
        boolean found=true;
        TileType[] tts = {TileType.CAT, TileType.BOOK, TileType.GAME, TileType.FRAME, TileType.TROPHY, TileType.PLANT};
        int lines=0;
        if (toBeChecked(b)) {
            for (int i = 0; i < b.getMaxHeight(); i++) {
                   if(differentTypesInOneline(b,i)){
                       lines++;

                        }
                    }
            return lines>=2;

                }

        else return false;

            }



        }


