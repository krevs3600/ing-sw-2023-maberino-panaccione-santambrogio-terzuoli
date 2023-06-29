package it.polimi.ingsw.model.CommonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.utils.TileType;

import java.util.Map;


/**
 * <h1>Class FourGroupsCommonGoalCard</h1>
 * The class FourGroupsCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public class FourGroupsCommonGoalCard extends CommonGoalCard {

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to determine whether the goal card is to be checked or not,
     * namely if the bookshelf has the minimum requirements to be checked
     * In this case, the number of tiles within the bookshelf must be at least sixteen to possibly achieve the goal
     * @param b the bookshelf to check if it meets the minimum requirements of the common goal
     * @return boolean It returns true if the bookshelf has enough number of item tiles, and it is worth checking, false otherwise
     */
    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles() >= 16;
    }

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to check whether the given bookshelf has the disposition of item tiles described by the common goal
     * In this case, the bookshelf must have four groups each containing at least 4 tiles of the same type.
     * The tiles of one group can be different from those of another group.
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean checkPattern(Bookshelf b) {
        TileType[] tts = {TileType.CAT, TileType.BOOK, TileType.GAME, TileType.FRAME, TileType.TROPHY, TileType.PLANT};
        if (this.toBeChecked(b)) {
            int counter = 0;

            for (TileType tt : tts) {
                Map<Integer, Integer> m = b.getNumberAdjacentTiles(tt);
                for (int i = 4; i < b.getNumberOfTiles(); i++) {
                    if (m.containsKey(i)) {
                        counter += m.get(i);
                    }
                }

            }
            return counter >= 4;
        }
        return false;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "FOUR OUR GROUPS EACH CONTAING AT LEAST 4 TILES OF THE SAME TYPE.\n" +
                "THE TILES OF ONE GROUP CAN BE DIFFERENT FROM THOSE OF ANOTHER GROUP";
    }
    @Override
    public String getType () {
        return this.getClass().getSimpleName();
    }
}
