package it.polimi.ingsw.model;

public class TwoColumnCommonGoalCard extends CommonGoalCard{
    public TwoColumnCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=12;
    }

    public boolean differentTypesinOneColumn(Bookshelf b,int j) {
        boolean found = true;
        for (int i = 0; i < b.getMaxHeight() && found; i++) {
            for (int k = i + 1; k < b.getMaxHeight() && found; k++) {
                if (b.getGrid()[i][j].equals(b.getGrid()[k][j])) {
                    found = false;
                }
            }
        }
        return found;
    }


    public boolean CheckPattern (Bookshelf b) {
        boolean found=true;
        int columns=0;
        if (toBeChecked(b)) {
            for (int j = 0; j < b.getMaxWidth(); j++) {
                if(differentTypesinOneColumn(b,j)){
                    columns++;

                }
            }
            return columns>=2;

        }

        else return false;

    }



}
