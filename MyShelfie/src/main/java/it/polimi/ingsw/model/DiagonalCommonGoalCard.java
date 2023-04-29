package it.polimi.ingsw.model;

public class DiagonalCommonGoalCard extends CommonGoalCard{

    public DiagonalCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=15;
    }

    public boolean CheckPattern (Bookshelf b) {
       int diagonal1=0;
       int diagonal2=0;
        for (int i=0, j=0; i<4 && j<5 ; i++, j++) {
            if (b.getGrid()[i][j] != null && b.getGrid()[i + 1][j + 1] != null) {
                if ((b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j + 1].getType()))) {
                    diagonal1++;
                }
            }
            if (b.getGrid()[i + 1][j] != null && b.getGrid()[i + 2][j + 1] != null) {
                if ((b.getGrid()[i + 1][j].getType().equals(b.getGrid()[i + 2][j + 1].getType()))) {
                    diagonal2 ++;
                }
            }
            if (diagonal1==4 || diagonal2==4) return true;
        }


        diagonal1 = 0;
        diagonal2 = 0;
        for (int i=0, j=4; i<4 && j>0; i++, j--) {
            if (b.getGrid()[i][j] != null && b.getGrid()[i + 1][j - 1] != null) {
                if ((b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j - 1].getType()))) {
                    diagonal1++;
                }
            }
            if (b.getGrid()[i + 1][j] != null && b.getGrid()[i + 2][j - 1] != null) {
                if ((b.getGrid()[i + 1][j].getType().equals(b.getGrid()[i + 2][j - 1].getType()))) {
                    diagonal2++;
                }
            }
        }

        return diagonal1 == 4 || diagonal2 == 4;
    }

}
