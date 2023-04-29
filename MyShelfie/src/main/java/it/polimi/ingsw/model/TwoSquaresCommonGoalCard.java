package it.polimi.ingsw.model;

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
     * Class constructor
     * @param nop the number of players necessary to determine the scoring tokens stacking on the card
     * @param roman the identifying roman number necessary to determine whether it is the first or the second goal of the game
     */
    public TwoSquaresCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

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
    public boolean CheckPattern (Bookshelf b) {
        TileType tipeFound = TileType.CAT;
        int squares = 0;
        int[][] auxiliary = new int[b.getMaxHeight()][b.getMaxWidth()];
        for (int i = 0; i < b.getMaxHeight(); i++) {
            for (int j = 0; j < b.getMaxWidth(); j++) {
                auxiliary[i][j] = 0;
            }
        }

        if (toBeChecked(b)) {
            for (int i = 0; i < b.getMaxHeight() - 1 && squares < 2; i++) {
                for (int j = 0; j < b.getMaxWidth() - 1 && squares < 2; j++) {
                    if (b.getGrid()[i][j] != null && b.getGrid()[i + 1][j] != null
                            && b.getGrid()[i][j + 1] != null && b.getGrid()[i + 1][j + 1] != null) {
                        if (squares == 0 && b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j].getType()) && auxiliary[i][j] == 0 &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i][j + 1].getType()) && auxiliary[i][j + 1] == 0 &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j + 1].getType()) && auxiliary[i + 1][j] == 0) {
                            auxiliary[i][j] = 1;
                            auxiliary[i + 1][j] = 1;
                            auxiliary[i][j + 1] = 1;
                            auxiliary[i + 1][j + 1] = 1;
                            tipeFound = b.getGrid()[i][j].getType();
                            squares++;
                        } else if
                        (squares > 0 && b.getGrid()[i][j].getType().equals(tipeFound) && b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j].getType()) && auxiliary[i][j] == 0 &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i][j + 1].getType()) && auxiliary[i][j + 1] == 0 &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j + 1].getType()) && auxiliary[i + 1][j] == 0) {
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
        return (squares== 2);
    }
}


