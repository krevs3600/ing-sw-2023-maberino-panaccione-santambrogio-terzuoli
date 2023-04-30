package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>Class LivingRoomBoard</h1>
 * The class LivingRoomBoard represents the central board of the game where the item tiles are placed
 * and from which they can be picked by the players
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/21/2023
 */

public class LivingRoomBoard {
    private static final int MAX_WIDTH = 9;
    private static final int MAX_HEIGHT = 9;
    private final Space[][] spaces = new Space[MAX_WIDTH][MAX_HEIGHT];

    private CommonGoalCard firstCommonGoalCard;
    private CommonGoalCard secondCommonGoalCard;

    private final Bag bag = new Bag();

    // The followings are the configurations for each possible number of players.
    // Each sublist has [0] and [2] indexes for FORBIDDEN and [1] for ACTIVE
    // This could change if we decide to use the THREE_PLAYER and FOUR_PLAYER SpaceType's values
    private final int[][] twoPlayerSetup = {{9, 0, 0}, {3, 2, 4}, {3, 3, 3}, {2, 6, 1}, {1, 7, 1}, {1, 6, 2}, {3, 3, 3}, {4, 2, 3}, {9, 0, 0,}};
    private final int[][] threePlayerSetup = {{3, 1, 5}, {3, 2, 4}, {2, 5, 2}, {2, 7, 0}, {1, 7, 1}, {0, 7, 2}, {2, 5, 2}, {4, 2, 3}, {5, 1, 3,}};
    private final int[][] fourPlayerSetup = {{3, 2, 4}, {3, 3, 3}, {2, 5, 2}, {1, 8, 0}, {0, 9, 0}, {0, 8, 1}, {2, 5, 2}, {3, 3, 3}, {4, 2, 3,}};


    /**
     * Class constructor
     * @param numberOfPlayers the number of players on which the configuration of playable spaces depends
     * @exception  IllegalArgumentException The exception is thrown if numberOfPlayers is not within the proper interval
     */

    //TODO: modify the input numberOfPlayers as an enum and not an int
    public LivingRoomBoard(int numberOfPlayers) throws IllegalArgumentException {
        int[][] activeList;
        CommonGoalCardDeck commonGoalCardDeck = new CommonGoalCardDeck();

        switch (numberOfPlayers) {
            case 2 -> {
                activeList = twoPlayerSetup;
            }
            case 3 -> {
                activeList = threePlayerSetup;
            }
            case 4 -> {
                activeList = fourPlayerSetup;
            }
            default -> {
                throw new IllegalArgumentException("Invalid number of players, please insert 2, 3 or 4 players");
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
        // TODO: modify draw method introducing a correct input (nop, roman)
        this.firstCommonGoalCard = commonGoalCardDeck.draw();
        this.secondCommonGoalCard = commonGoalCardDeck.draw();
    }

    /**
     * This method returns the space of the given position
     * @param position the position of the space of interest
     * @return Space It returns the space of interest
     */
    public Space getSpace(Position position) {
        return spaces[position.getRow()][position.getColumn()];
    }

    /**
     * This method finds all the spaces from the LivingRoomBoard with all free spaces on their side
     * @return List<Space> It returns a list with all the free spaces
     */
    public List<Space> getAllFree() {
        List<Space> freeSidesTiles = new ArrayList<Space>();

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
     * This method finds all the spaces from the LivingRoomBoard that have at least one free side.
     * @return List<Space> It returns a list with all the spaces with one free side
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

    //TODO: getCommonGoalCards
    public List<CommonGoalCard> getCommonGoalCards() {
        // list or array?
        // CommonGoalCard[]
        return null;
    }

    /**
     * This method refills the living room board with new ItemTiles.
     * First, the item tiles left on the board without any other adjacent tile are put back in the bag
     * Then, new ones are drawn and placed.
     */
    public void refill() {
        if (getDrawableTiles().size() == getAllFree().size()) { // this condition will probably go in the controller
            bag.insertTiles(getAllFree().stream().map(Space::getTile).collect(Collectors.toList()));
            for (Space space : getAllFree()) {
                space.drawTile();
            }
            placeTilesRandomly();
        }
    }

    /**
     * This method draws the ItemTiles from the bag and places them randomly in the LivingRoomBoard on the playable spaces.
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