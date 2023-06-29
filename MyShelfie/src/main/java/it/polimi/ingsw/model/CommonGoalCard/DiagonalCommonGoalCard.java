package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;

/**
 * <h1>Class DiagonalCommonGoalCard</h1>
 * The class DiagonalCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public class DiagonalCommonGoalCard extends CommonGoalCard{

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to determine whether the goal card is to be checked or not,
     * namely if the bookshelf has the minimum requirements to be checked
     * In this case, the number of tiles within the bookshelf must be at least fifteen to possibly achieve the goal
     * @param b the bookshelf to check if it meets the minimum requirements of the common goal
     * @return boolean It returns true if the bookshelf has enough number of item tiles, and it is worth checking, false otherwise
     */
    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=15;
    }

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to check whether the given bookshelf has the disposition of item tiles described by the common goal
     * In this case, the bookshelf must have five tiles of the same type forming a diagonal.
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean checkPattern(Bookshelf b) {
       int diagonal1=0;
       int diagonal2=0;
       if (toBeChecked(b)) {
           for (int i = 0, j = 0; i < 4 && j < 5; i++, j++) {
               if (b.getGrid()[i][j] != null && b.getGrid()[i + 1][j + 1] != null) {
                   if ((b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j + 1].getType()))) {
                       diagonal1++;
                   }
               }
               if (b.getGrid()[i + 1][j] != null && b.getGrid()[i + 2][j + 1] != null) {
                   if ((b.getGrid()[i + 1][j].getType().equals(b.getGrid()[i + 2][j + 1].getType()))) {
                       diagonal2++;
                   }
               }
               if (diagonal1 == 4 || diagonal2 == 4) return true;
           }
           diagonal1 = 0;
           diagonal2 = 0;

           for (int i = 0, j = 4; i < 4 && j > 0; i++, j--) {
               if (b.getGrid()[i][j] != null && b.getGrid()[i + 1][j - 1] != null) {
                   if ((b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j - 1].getType()))) {
                       diagonal1++;}}
               if (b.getGrid()[i + 1][j] != null && b.getGrid()[i + 2][j - 1] != null) {
                   if ((b.getGrid()[i + 1][j].getType().equals(b.getGrid()[i + 2][j - 1].getType()))) {
                       diagonal2++;
                   }
               }
           }
           return diagonal1 == 4 || diagonal2 == 4;
       }
       return false;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "FIVE TILES OF THE SAME TYPE FORMING A DIAGONAL";
    }
    @Override
    public String getType () {
        return this.getClass().getSimpleName();
    }}
