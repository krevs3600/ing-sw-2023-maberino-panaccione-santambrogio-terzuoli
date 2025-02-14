package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.utils.TileType;

/**
 * <h1>Class TwoSquaresCommonGoalCard</h1>
 * The class TwoSquaresCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/9/2023
 */
public class TwoSquaresCommonGoalCard extends CommonGoalCard{

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to determine whether the goal card is to be checked or not,
     * namely if the bookshelf has the minimum requirements to be checked
     * In this case, the number of tiles within the bookshelf must be at least eight to possibly achieve the goal
     * @param b the bookshelf to check if it meets the minimum requirements of the common goal
     * @return boolean It returns true if the bookshelf has enough number of item tiles, and it is worth checking, false otherwise
     */
    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=8;
    }

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to check whether the given bookshelf has the disposition of item tiles described by the common goal
     * In this case, the bookshelf must have two groups each containing four tiles of the same type in a 2x2 square
     * The tiles of one square can be different from those of the other square.
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean checkPattern(Bookshelf b) {
        int squares = 0;
        int[][] auxiliary = new int[b.getMaxHeight()][b.getMaxWidth()];
        for (int i = 0; i < b.getMaxHeight(); i++) {
            for (int j = 0; j < b.getMaxWidth(); j++) {
                auxiliary[i][j] = 0;
            }
        }

        if (toBeChecked(b)) {
            for (int i = 0; i < b.getMaxHeight() - 1; i++) {
                for (int j = 0; j < b.getMaxWidth() - 1; j++) {
                    if (b.getGrid()[i][j] != null && b.getGrid()[i + 1][j] != null
                            && b.getGrid()[i][j + 1] != null && b.getGrid()[i + 1][j + 1] != null) {
                        if (b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j].getType()) &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i][j + 1].getType())  &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j + 1].getType())  &&
                                auxiliary[i][j] == 0 &&
                                auxiliary[i][j + 1] == 0 &&
                                auxiliary[i + 1][j] == 0 &&
                                auxiliary[i+1][j+1] == 0) {
                            auxiliary[i][j] = 1;
                            auxiliary[i + 1][j] = 1;
                            auxiliary[i][j + 1] = 1;
                            auxiliary[i + 1][j + 1] = 1;
                            squares++;
                        }
                    }
                }
            }
        }
        return (squares>=2);
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "TWO GROUPS EACH CONTAINING FOUR TILES OF THE SAME TYPE IN A 2X2 SQUARE\n" +
                "THE TILES OF ONE SQUARE CAN BE DIFFERENT FROM THOSE OF THE OTHER SQUARE.";
    }
    @Override
    public String getType () {
        return this.getClass().getSimpleName();
    }
}


