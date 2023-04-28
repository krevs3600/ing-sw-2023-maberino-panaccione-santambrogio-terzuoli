package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Game's board <br>
 * The board is represented as a MAX_WIDTH x MAX_HEIGHT matrix of Spaces
 */
public class LivingRoomBoard {
    private static final int MAX_WIDTH = 9;
    private static final int MAX_HEIGHT = 9;
    private Space[][] spaces = new Space[MAX_WIDTH][MAX_HEIGHT];

    private Bag bag = new Bag();
    private int numberOfPlayers;

    // The followings are the configurations for each possible number of players.
    // Each sublist has [0] and [2] indexes for FORBIDDEN and [1] for ACTIVE
    // This could change if we decide to use the THREE_PLAYER and FOUR_PLAYER SpaceType's values
    private final int[][] twoPlayerSetup = {{9, 0, 0}, {3, 2, 4}, {3, 3, 3}, {2, 6, 1}, {1, 7, 1}, {1, 6, 2}, {3, 3, 3}, {4, 2, 3}, {9, 0, 0,}};
    private final int[][] threePlayerSetup = {{3, 1, 5}, {3, 2, 4}, {2, 5, 2}, {2, 7, 0}, {1, 7, 1}, {0, 7, 2}, {2, 5, 2}, {4, 2, 3}, {5, 1, 3,}};
    private final int[][] fourPlayerSetup = {{3, 2, 4}, {3, 3, 3}, {2, 5, 2}, {1, 8, 0}, {0, 9, 0}, {0, 8, 1}, {2, 5, 2}, {3, 3, 3}, {4, 2, 3,}};


    /**
     * Initialization of game's board <br>
     * Takes as input the number of players, and it initializes the board accordingly.
     * Afterwards, tiles are drawn from the bag and placed on the free spaces
     * according to the chosen configuration.
     *
     * @throws IllegalArgumentException if numberOfPlayers not in interval [2,4]
     * @param numberOfPlayers
     */
    public LivingRoomBoard(int numberOfPlayers) throws IllegalArgumentException {
        int[][] activeList = null;

        switch (numberOfPlayers) {
            case 2: {
                activeList = twoPlayerSetup;
                break;
            }
            case 3: {
                activeList = threePlayerSetup;
                break;
            }
            case 4: {
                activeList = fourPlayerSetup;
                break;
            }
            default: {
                throw new IllegalArgumentException("Illegal number of players. There can be between 2 and 4 players");
            }
        }

        int c = 0, r = 0;
        for (int[] list : activeList) {
            // forbidden tiles
            for (int i = 0; i < list[0]; i++) {
                Position position = new Position(r, c);
                spaces[r][c] = new Space(SpaceType.FORBIDDEN, position);
                c++;
            }
            // active tiles
            for (int i = 0; i < list[1]; i++) {
                Position position = new Position(r, c);
                spaces[r][c] = new Space(SpaceType.DEFAULT, position);
                c++;
            }
            // forbidden tiles
            for (int i = 0; i < list[2]; i++) {
                Position position = new Position(r, c);
                spaces[r][c] = new Space(SpaceType.FORBIDDEN, position);
                c++;
            }
            c=0;
            r++;
        }
        placeTilesRandomly();
        /**
         * TO BE DONE:
         * Adding CommonGoalCards
         */

    }

    /**
     * Given the space's position this method will return the right Space from the LivingRoomBoard
     * @param position position of the space of interest
     * @return the space of interest
     */
    public Space getSpace(Position position) {
        return spaces[position.getRow()][position.getColumn()];
    }

    /**
     * Finds all the spaces from the LivingRoomBoard with all free spaces on their side
     * @return all those spaces
     */
    public List<Space> getAllFree() {
        List<Space> freeSidesTiles = new ArrayList<Space>();

        /**
         * Check's order
         *        4
         *     1 [] 3
         *        2
         */
        for (int i = 1; i < MAX_WIDTH - 1; i++) {
            for (int j = 1; j < MAX_HEIGHT - 1; j++) {
                if (!getSpace(new Position(i, j)).isFree() && getSpace(new Position(i, j)).getType() != SpaceType.FORBIDDEN) {
                    if (
                            (getSpace(new Position(i, j - 1)).isFree() || getSpace(new Position(i, j - 1)).getType() == SpaceType.FORBIDDEN) &&
                                    (getSpace(new Position(i + 1, j)).isFree() || getSpace(new Position(i + 1, j)).getType() == SpaceType.FORBIDDEN) &&
                                    (getSpace(new Position(i, j + 1)).isFree() || getSpace(new Position(i, j + 1)).getType() == SpaceType.FORBIDDEN) &&
                                    (getSpace(new Position(i - 1, j)).isFree() || getSpace(new Position(i - 1, j)).getType() == SpaceType.FORBIDDEN)
                    ) {
                        freeSidesTiles.add(getSpace(new Position(i, j)));
                    }
                }
            }
        }
        return freeSidesTiles;
    }

    /**
     * Finds all the spaces from the LivingRoomBoard that have at least one free side.
     * @return List<Space>
     */
    public List<Space> getDrawableTiles() {
        List<Space> drawablesTiles = new ArrayList<Space>();

        for (int i = 0; i < MAX_WIDTH; i++) {
            for (int j = 0; j < MAX_HEIGHT; j++) {
                if (!getSpace(new Position(i, j)).isFree() && getSpace(new Position(i,j)).getType() == SpaceType.DEFAULT) {
                    // inner spaces
                    if (i==0 || i==MAX_HEIGHT-1 || j==0 || j==MAX_WIDTH-1){
                        drawablesTiles.add(getSpace(new Position(i, j)));
                    }
                    else if (
                            (getSpace(new Position(i, j - 1)).isFree() || getSpace(new Position(i, j - 1)).getType() == SpaceType.FORBIDDEN) ||
                                    (getSpace(new Position(i + 1, j)).isFree() || getSpace(new Position(i + 1, j)).getType() == SpaceType.FORBIDDEN) ||
                                    (getSpace(new Position(i, j + 1)).isFree() || getSpace(new Position(i, j + 1)).getType() == SpaceType.FORBIDDEN) ||
                                    (getSpace(new Position(i - 1, j)).isFree() || getSpace(new Position(i - 1, j)).getType() == SpaceType.FORBIDDEN)
                    ) {
                        drawablesTiles.add(getSpace(new Position(i, j)));
                    }

                }

            }
        }
        return drawablesTiles;


    }

    /**
     * Returns the two CommonGoalCard associated to the LivingRoomBoard during the match.
     * @return List<CommonGoalCard>
     */
    public List<CommonGoalCard> getCommonGoalCards() {
        // list or array?
        // CommonGoalCard[]
        return null;
    }

    /**
     * LivingRoomBoard is refilled with new ItemTiles. First the not drawable ones are put back in the bag and then
     * new ones are drawn and placed.
     */
    public void refill() {
        if (getDrawableTiles().size() == getAllFree().size()) { // this condition will probably go in the controller
            bag.insertTiles(getAllFree().stream().map(x -> x.getTile()).collect(Collectors.toList()));
            for (Space space : getAllFree()) {
                space.drawTile();
            }
            placeTilesRandomly();
        }
    }

    /**
     * Draws the ItemTiles from the bag and places them in the LivingRoomBoard on the playable spaces.
     */
    private void placeTilesRandomly() {
        for (int i = 0; i < MAX_WIDTH; i++) {
            for (int j = 0; j < MAX_HEIGHT; j++) {
                if (getSpace(new Position(i, j)).getType() == SpaceType.DEFAULT) {
                    getSpace(new Position(i, j)).setTile(bag.drawTile());
                }
            }
        }
    }

    @Override
    public String toString(){
        String board = "";
        board = board.concat(cliPrintHorizontalNums()).concat("\n");
        board = board.concat(cliPrintLine()).concat("\n");
        for (int i=0; i < MAX_HEIGHT; i++){
            board = board.concat("\033[0;31m"+i + "\033[0m ");
            for (int j=0; j<MAX_WIDTH; j++){
                board = board.concat( "|" + getSpace(new Position(i,j)).toString());
            }
            board = board.concat("|\n");
            board = board.concat(cliPrintLine()).concat("\n");
        }
        return board;
    }

    private String cliPrintHorizontalNums(){
        String line = "   ".concat("\033[0;31m");
        for (int i=0; i<MAX_WIDTH; i++){
            line = line.concat(" " + i + "  ");
        }
        return line.concat("\033[0m");
    }
    private String cliPrintLine(){
        String line = "  +";
        for (int i=0; i<MAX_WIDTH; i++){
            line = line.concat("---+");
        }
        return line;
    }
}