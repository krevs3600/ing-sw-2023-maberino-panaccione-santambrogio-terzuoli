package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.utils.TileType;

/**
 * <h1>Class TwoColumnsCommonGoalCard</h1>
 * The class TwoColumnsCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/9/2023
 */
public class TwoColumnsCommonGoalCard extends CommonGoalCard{

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to determine whether the goal card is to be checked or not,
     * namely if the bookshelf has the minimum requirements to be checked
     * In this case, the number of tiles within the bookshelf must be at least twelve to possibly achieve the goal
     * @param b the bookshelf to check if it meets the minimum requirements of the common goal
     * @return boolean It returns true if the bookshelf has enough number of item tiles, and it is worth checking, false otherwise
     */
    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=12;
    }

    /**
     * This private method is an auxiliary method used by the CheckPattern method and checks if there are all different types in a column
     * @param b the bookshelf to check if it has six different types in one of its column
     * @param j the column to check
     * @return boolean It returns true if the column has all six different types
     */
    private boolean differentTypesInOneColumn(Bookshelf b,int j) {
        boolean found;
        int counter = 0;
        for (TileType tileType: TileType.values()) {
            found = false;
            for (int i = 0; i < b.getMaxHeight() && !found; i++) {
                if (b.getGrid()[i][j] != null && b.getGrid()[i][j].getType().equals(tileType)) {
                    counter++;
                    found = true;
                }
            }
        }

        return counter==6;
    }

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to check whether the given bookshelf has the disposition of item tiles described by the common goal
     * In this case, the bookshelf must have two columns each formed by six different types of tiles
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean checkPattern(Bookshelf b) {
        int columns=0;
        if (toBeChecked(b)) {
            for (int j = 0; j < b.getMaxWidth(); j++) {
                if (differentTypesInOneColumn(b, j)) {
                    columns++;
                }
            }
        }
        return columns>=2;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "TWO COLUMNS EACH FORMED BY SIX DIFFERENT TYPES OF TILES";
    }
    @Override
    public String getType () {
        return this.getClass().getSimpleName();
    }
}



