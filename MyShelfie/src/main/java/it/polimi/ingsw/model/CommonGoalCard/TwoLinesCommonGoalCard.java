package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;

/**
 * <h1>Class TwoLinesCommonGoalCard</h1>
 * The class TwoLinesCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/9/2023
 */
public class TwoLinesCommonGoalCard extends CommonGoalCard{

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to determine whether the goal card is to be checked or not,
     * namely if the bookshelf has the minimum requirements to be checked
     * In this case, the number of tiles within the bookshelf must be at least ten to possibly achieve the goal
     * @param b the bookshelf to check if it meets the minimum requirements of the common goal
     * @return boolean It returns true if the bookshelf has enough number of item tiles, and it is worth checking, false otherwise
     */
    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=10;
    }

    /**
     * This private method is an auxiliary method used by the CheckPattern method and checks if there are all different types in a row
     * @param b the bookshelf to check if it has five different types in one of its row
     * @param i the row to check
     * @return boolean It returns true if the row has five different types
     */
    public boolean differentTypesInOneRow(Bookshelf b,int i) {
        boolean found = false;
        for (int j = 0; j < b.getMaxWidth(); j++){
            if(b.getGrid()[i][j]==null)
                return found;
        }
        found=true;
            for (int j = 0; j < b.getMaxWidth() && found; j++) {
                for (int k = j + 1; k < b.getMaxWidth() && found; k++) {
                    if (b.getGrid()[i][j].getType().equals(b.getGrid()[i][k].getType())) found = false;
                }
            }
            return found;
    }


    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to check whether the given bookshelf has the disposition of item tiles described by the common goal
     * In this case, the bookshelf must have two lines each formed by five different types of tiles
     * One line can show the same or a different combination of the other line.
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean CheckPattern (Bookshelf b) {

        int lines=0;
        if (toBeChecked(b)) {
            for (int i = 0; i < b.getMaxHeight(); i++) {
                   if(differentTypesInOneRow(b,i)){
                       lines++;
                   }
            }
            return lines>=2;
        }
        return false;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "Two lines each formed by five different types of tiles\n" +
                "One line can show the same or a different combination of the other line.";
    }
    @Override
    public String getType () {
        return this.getClass().getSimpleName();
    }
}


