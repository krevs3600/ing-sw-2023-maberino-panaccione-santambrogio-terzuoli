package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.utils.TileType;

/**
 * <h1>Class EightTilesCommonGoalCard</h1>
 * The class EightTilesCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/9/2023
 */
public class EightTilesCommonGoalCard extends CommonGoalCard {

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
     * In this case, the bookshelf must have eight tiles of the same type and there is no restriction about the position of these tiles.
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean CheckPattern (Bookshelf b) {
        boolean found=false;
        TileType[] tts = {TileType.CAT, TileType.BOOK, TileType.GAME, TileType.FRAME, TileType.TROPHY, TileType.PLANT};
        int counter;
        if (toBeChecked(b)) {
            for (TileType tt : tts) {
                counter=0;
                for (int i = 0; i < b.getMaxHeight() && !found; i++) {
                    for (int j = 0; j < b.getMaxWidth(); j++) {
                        if (b.getGrid()[i][j]!=null && b.getGrid()[i][j].getType().equals(tt)) {
                            counter++;
                        }
                    }
                }
                if (counter == 8) {
                    found = true;
                }
            }
        }
        return found;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "Eight tiles of the same type. There is no restriction about the position of these tiles";
    }
}









