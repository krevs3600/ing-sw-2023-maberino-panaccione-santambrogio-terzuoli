package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;

/**
 * <h1>Class CornersCommonGoalCard</h1>
 * The class CornersCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public class CornersCommonGoalCard extends CommonGoalCard {

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to determine whether the goal card is to be checked or not,
     * namely if the bookshelf has the minimum requirements to be checked
     * In this case, the number of tiles must be at least 12 and the spaces in the four corners of the bookshelf must not be empty
     * @param b the bookshelf to check if it meets the minimum requirements of the common goal
     * @return boolean It returns true if the bookshelf has enough number of item tiles and has the four corners occupied, and it is worth checking, false otherwise
     */
    @Override
    public boolean toBeChecked(Bookshelf b) {
        return b.getNumberOfTiles() >= 12 && b.getGrid()[0][0] != null &&
                b.getGrid()[b.getMaxHeight() - 1][0] != null &&
                b.getGrid()[0][b.getMaxWidth() - 1] != null &&
                b.getGrid()[b.getMaxHeight() - 1][b.getMaxWidth() - 1] != null;
    }

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to check whether the given bookshelf has the disposition of item tiles described by the common goal
     * In this case, the bookshelf must have four tiles of the same type in the four corners
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    @Override
    public boolean CheckPattern(Bookshelf b) {
        if (toBeChecked(b)) {
            return b.getGrid()[0][0].getType().equals(b.getGrid()[b.getMaxHeight() - 1][0].getType()) &&
                    b.getGrid()[0][0].getType().equals(b.getGrid()[0][b.getMaxWidth() - 1].getType()) &&
                    b.getGrid()[0][0].getType().equals(b.getGrid()[b.getMaxHeight() - 1][b.getMaxWidth() - 1].getType());
        }
        return false;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "Four tiles of the same type in the four corners";
    }
}
