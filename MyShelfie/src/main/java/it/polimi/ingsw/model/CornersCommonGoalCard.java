package it.polimi.ingsw.model;

public class CornersCommonGoalCard extends CommonGoalCard{

    public CornersCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=12;
    }

    public boolean CheckPattern (Bookshelf b) {
        return b.getGrid()[0][0].getType().equals(b.getGrid()[b.getMaxHeight() - 1][0].getType()) &&
                b.getGrid()[0][0].getType().equals(b.getGrid()[0][b.getMaxWidth() - 1].getType()) &&
                b.getGrid()[0][0].getType().equals(b.getGrid()[b.getMaxHeight() - 1][b.getMaxWidth() - 1].getType());
    }
}
