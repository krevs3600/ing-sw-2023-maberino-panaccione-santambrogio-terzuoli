package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.utils.TileType;

/**
 * <h1>Class FourLinesCommonGoalCard</h1>
 * The class FourLinesCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/9/2023
 */
public class FourLinesCommonGoalCard extends CommonGoalCard  {

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to determine whether the goal card is to be checked or not,
     * namely if the bookshelf has the minimum requirements to be checked
     * In this case, the number of tiles within the bookshelf must be at least twenty
     * and the maximum insertable tiles in a column must be at least two to possibly achieve the goal
     * @param b the bookshelf to check if it meets the minimum requirements of the common goal
     * @return boolean It returns true if the bookshelf has enough number of item tiles, and it is worth checking, false otherwise
     */
    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=20 && b.getNumberInsertableTiles()<=2;
    }

    /**
     * This private method is an auxiliary method used by the CheckPattern method and checks if there are at most three different types in a row
     * @param b the bookshelf to check if it has at most three different types in a given row
     * @param i the row to check
     * @return boolean It returns true if the row has at most three different types, false otherwise
     */
    private boolean AtMostThreeDifferentTypesInOneRow(Bookshelf b, int i) {
        boolean found = false;
        boolean empty=false;
        int counter = 0;
        for(int j=0;j<b.getMaxWidth() && !empty;j++){
            if(b.getGrid()[i][j]==null){
                empty=true;
            }

        }
        if(!empty) {
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.CAT)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.BOOK)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.GAME)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.FRAME)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.TROPHY)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.PLANT)) {
                    counter++;
                    found = true;

                }
            }
            return counter <= 3;
        }
        return false;
    }

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to check whether the given bookshelf has the disposition of item tiles described by the common goal
     * In this case, the bookshelf must have four lines each formed by 5 tiles of maximum three different types.
     * One line can show the same or a different combination of another line
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean checkPattern(Bookshelf b) {
        int lines=0;
        if (toBeChecked(b)) {
            for (int i = 0; i < b.getMaxHeight(); i++) {
                if(AtMostThreeDifferentTypesInOneRow(b,i)){
                    lines++;
                }
            }
            return lines>=4;
        }
        return false;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "Four lines each formed by 5 tiles of maximum three different types.\n" +
                "One line can show the same or a different combination of another line";
    }
    @Override
    public String getType () {
        return this.getClass().getSimpleName();
    }
}




