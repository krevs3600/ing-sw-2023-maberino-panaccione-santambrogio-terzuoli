package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;

/**
 * <h1>Class CrossCommonGoalCard</h1>
 * The class CrossCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/9/2023
 */
//dubbio se deve essere esclusivamente x o possono esserci delle carte in mezzo  (quindi in i+1)
public class CrossCommonGoalCard extends CommonGoalCard{

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to determine whether the goal card is to be checked or not,
     * namely if the bookshelf has the minimum requirements to be checked
     * In this case, the number of tiles within the bookshelf must be at least five to possibly achieve the goal
     * @param b the bookshelf to check if it meets the minimum requirements of the common goal
     * @return boolean It returns true if the bookshelf has enough number of item tiles, and it is worth checking, false otherwise
     */
    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=5;
    }

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to check whether the given bookshelf has the disposition of item tiles described by the common goal
     * In this case, the bookshelf must have five tiles of the same type forming an X
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean CheckPattern (Bookshelf b) {
        if(toBeChecked(b)){
        for(int i=0;i<b.getMaxHeight()-2;i++){
            for(int j=0;j<b.getMaxWidth()-2 ;j++){
                if(b.getGrid()[i][j]!=null && b.getGrid()[i+2][j]!=null &&  b.getGrid()[i+1][j+1]!=null &&
                        b.getGrid()[i][j+2]!=null && b.getGrid()[i+2][j+2]!=null) {
                    if (b.getGrid()[i][j].getType().equals(b.getGrid()[i + 2][j].getType()) &&
                            b.getGrid()[i][j].getType().equals(b.getGrid()[i][j + 2].getType()) &&
                            b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j + 1].getType()) &&
                            b.getGrid()[i][j].getType().equals(b.getGrid()[i + 2][j + 2].getType())) {
                        return true;
                    }
                }
            }
        }
        }
       return false;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "Five tiles of the same type forming an X";
    }
    @Override
    public String getType () {
        return this.getClass().getSimpleName();
    }
}
