package it.polimi.ingsw.model;


//dubbio se deve essere esclusivamente x o possono esserci delle carte in mezzo  (quindi in i+1)
public class CrossCommonGoalCard extends CommonGoalCard{
    public CrossCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=5;
    }

    public boolean CheckPattern (Bookshelf b) {
        if(toBeChecked(b)){
        for(int i=0;i<b.getMaxWidth()-2;i++){
            for(int j=0;j<b.getMaxHeight()-2 ;j++){
                if(b.getGrid()[i][j].getType().equals(b.getGrid()[i+2][j].getType()) &&
                        b.getGrid()[i][j].getType().equals(b.getGrid()[i][j+2].getType())  &&
                        b.getGrid()[i][j].getType().equals(b.getGrid()[i+1][j+1].getType()) &&
                        b.getGrid()[i][j].getType().equals(b.getGrid()[i+2][j+2].getType()))
                    { return true;}

                }
            }

        }
       return false;
    }


}
