package it.polimi.ingsw.model;

public class DiagonalCommonGoalCard extends CommonGoalCard{

    public DiagonalCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=15;
    }

    public boolean CheckPattern (Bookshelf b) {
        boolean diagonal1 = true;
        boolean diagonal2 = true;
        for (int i=0, j=0; i<4 && j<4 && (diagonal1 || diagonal2); i++, j++) {
            if (!(b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j + 1].getType()))) {
                diagonal1 = false;
            }
            if (!(b.getGrid()[i + 1][j].getType().equals(b.getGrid()[i + 2][j + 1].getType()))) {
                diagonal2 = false;
            }
        }
        if (diagonal1 || diagonal2) return true;
        diagonal1 = true;
        diagonal2 = true;
        for (int i=0, j=4; i<4 && j>0 && (diagonal1 || diagonal2); i++, j--) {
            if (!(b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j - 1].getType()))) {
                diagonal1 = false;
            }
            if (!(b.getGrid()[i + 1][j].getType().equals(b.getGrid()[i + 2][j - 1].getType()))) {
                diagonal2 = false;
            }
        }
        return diagonal1 || diagonal2;
    }

}
