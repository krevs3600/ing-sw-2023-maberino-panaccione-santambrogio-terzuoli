package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;

/**
 * <h1>Class IncreasingColumnsCommonGoalCard</h1>
 * The class IncreasingColumnsCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public class IncreasingColumnsCommonGoalCard extends CommonGoalCard{

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
     * In this case, the bookshelf must have five columns of increasing or decreasing height
     * Starting from the first column on the left or on the right, each next column must be made of exactly one more tile
     * Tiles can be of any type
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean checkPattern(Bookshelf b) {
        boolean increasingOrder = true;
        for (int i=0; i<b.getMaxWidth()-1 && increasingOrder; i++) {
            increasingOrder = (b.getNumberInsertableTilesColumn(i) == b.getNumberInsertableTilesColumn(i+1) + 1);
        }
        if (increasingOrder) return true;
        increasingOrder = true;
        for (int i=b.getMaxWidth()-1; i>0 && increasingOrder; i--) {
            increasingOrder = (b.getNumberInsertableTilesColumn(i) == b.getNumberInsertableTilesColumn(i-1) + 1);
        }
        return increasingOrder;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return """
                Five columns of increasing or decreasing height.
                Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.\s
                Tiles can be of any type""";
    }
    @Override
    public String getType () {
        return this.getClass().getSimpleName();
    }
}