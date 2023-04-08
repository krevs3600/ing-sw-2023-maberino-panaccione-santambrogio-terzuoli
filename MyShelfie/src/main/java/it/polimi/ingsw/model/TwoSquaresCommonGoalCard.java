package it.polimi.ingsw.model;

public class TwoSquaresCommonGoalCard extends CommonGoalCard{
    public TwoSquaresCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=8;
    }

    public boolean CheckPattern (Bookshelf b) {

     int squares=0;
        int[][] auxiliary = new int[b.getMaxHeight()][b.getMaxWidth()];
        for (int i = 0; i < b.getMaxWidth(); i++) {
            for (int j = 0; j < b.getMaxHeight(); j++) {
                auxiliary[i][j] = 0;
            }
        }

        if (toBeChecked(b)) {
            for(int i=0; i< b.getMaxWidth() && squares<2 ;i++){
                for(int j=0;j<b.getMaxHeight() && squares<2 ;j++){
                    if(b.getGrid()[i][j].getType().equals(b.getGrid()[i+1][j].getType()) && auxiliary[i][j]==0 &&
                            b.getGrid()[i][j].getType().equals(b.getGrid()[i][j+1].getType()) && auxiliary[i][j+1]==0 &&
                             b.getGrid()[i][j].getType().equals(b.getGrid()[i+1][j+1].getType()) && auxiliary[i+1][j+1]==0){
                        auxiliary[i][j]=1;
                        auxiliary[i+1][j]=1;
                        auxiliary[i][j+1]=1;
                        auxiliary[i+1][j+1]=1;
                        squares++;

                }
                }
            }
           return (squares==2);

            }



            else {
            return false;
        }
    }
}
