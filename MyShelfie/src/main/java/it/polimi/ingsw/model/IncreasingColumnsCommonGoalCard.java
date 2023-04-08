package it.polimi.ingsw.model;

public class IncreasingColumnsCommonGoalCard extends CommonGoalCard{

    public IncreasingColumnsCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=15;
    }

    public boolean CheckPattern (Bookshelf b) {
        boolean increasingOrder = true;
        for (int i=0; i<b.getMaxWidth()-1 && increasingOrder; i++) {
            increasingOrder = (b.getNumberInsertableTilesColumn(i) == b.getNumberInsertableTilesColumn(i+1) - 1);
        }
        if (increasingOrder) return increasingOrder;
        increasingOrder = true;
        for (int i=b.getMaxWidth()-1; i>0 && increasingOrder; i--) {
            increasingOrder = (b.getNumberInsertableTilesColumn(i) == b.getNumberInsertableTilesColumn(i-1) - 1);
        }
        return increasingOrder;
    }
}