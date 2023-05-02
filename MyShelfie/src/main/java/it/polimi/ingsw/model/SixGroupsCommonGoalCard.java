package it.polimi.ingsw.model;

import java.util.Map;

import java.util.Map;

/**
 * <h1>Class SixGroupsCommonGoalCard</h1>
 * The class SixGroupsCommonGoalCard extends the CommonGoalCard abstract class
 * and represents one of the two possible common goals achievable by all the players during a game
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public class SixGroupsCommonGoalCard extends CommonGoalCard {

    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to determine whether the goal card is to be checked or not,
     * namely if the bookshelf has the minimum requirements to be checked
     * In this case, the number of tiles within the bookshelf must be at least twelve to possibly achieve the goal
     *
     * @param b the bookshelf to check if it meets the minimum requirements of the common goal
     * @return boolean It returns true if the bookshelf has enough number of item tiles, and it is worth checking, false otherwise
     */
    public boolean toBeChecked(Bookshelf b) {
        return b.getNumberOfTiles() >= 12;
    }
    /**
     * This method is the implementation of the CommonGoalCard's one
     * It is used to check whether the given bookshelf has the disposition of item tiles described by the common goal
     * In this case, the bookshelf must have six groups each containing at least two tiles of the same type
     * The tiles of one group can be different from those of another group.
     *
     * @param b the bookshelf to check if it meets the requirements of the common goal
     * @return boolean It returns true if the bookshelf has the disposition of item tiles described by the common goal, false otherwise
     */
    public boolean CheckPattern(Bookshelf b) {
        TileType[] tts = {TileType.CAT, TileType.BOOK, TileType.GAME, TileType.FRAME, TileType.TROPHY, TileType.PLANT};
        int counter = 0;
        if (this.toBeChecked(b)) {
            for (TileType tt : tts) {
                Map<Integer, Integer> m = b.getNumberAdjacentTiles(tt);
                for (int i = 2; i < b.getNumberOfTiles(); i++) {
                    if (m.containsKey(i)) {
                        counter += (int) m.get(i); // casting!!!
                        counter += m.get(i);
                    }
                }
            }
        }
        return counter >= 6;
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        return "Six groups each containing at least two tiles of the same type.\n" +
                "The tiles of one group can be different from those of another group";
    }
}


