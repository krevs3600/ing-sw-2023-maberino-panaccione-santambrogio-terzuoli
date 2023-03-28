package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Game's board
 * The board is represented as a MAX_WIDTH x MAX_HEIGHT matrix of Spaces
 */
class LivingRoomBoard() {
    private final int MAX_WIDTH = 9;
    private final int MAX_HEIGHT = 9;
    private Space[][] spaces = new Space[MAX_WIDTH][MAX_HEIGHT];

    private Bag bag = new Bag();
    private int numberOfPlayers;

    // The followings are the configurations for each possible number of players.
    // Each sublist has [0] and [2] indexes for FORBIDDEN and [1] for ACTIVE
    // This could change if we decide to use the THREE_PLAYER and FOUR_PLAYER SpaceType's values
    private final int[][] twoPlayerSetup = {{9, 0, 0}, {3, 2, 4}, {3, 3, 3}, {2, 6, 1}, {1, 7, 1}, {1, 6, 2}, {3, 3, 3}, {4, 2, 3}, {9, 0, 0,}};
    private final int[][] threePlayerSetup = {{3, 1, 5}, {3, 2, 4}, {2, 5, 2}, {2, 7, 0}, {1, 7, 1}, {0, 7, 2}, {2, 4, 3}, {4, 2, 3}, {9, 0, 0,}};
    private final int[][] fourPlayerSetup = {{3, 2, 4}, {3, 3, 3}, {3, 3, 4}, {2, 6, 1}, {1, 7, 1}, {1, 6, 2}, {3, 3, 3}, {4, 2, 3}, {9, 0, 0,}};


    /**
     * Initialization of game's board
     * Takes as input the number of players, and it initializes the board accordingly.
     * Afterwards, tiles are drawn from the bag and placed on the free spaces
     * according to the chosen configuration
     *
     * @param numberOfPlayers int
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
            }
            case 4: {
                activeList = fourPlayerSetup;
            }
            default: {
                throw new IllegalArgumentException();
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
            r++;
        }
        placeTilesRandomly();
        /**
         * TO BE DONE:
         * Adding CommonGoalCards
         */
    }


    public Space getSpace(Position position) {
        return spaces[position.getRow()][position.getColumn()];
    }

    public List<Space> getAllFree() {
        List<Space> freeSidesTiles = new ArrayList<Space>();

        for (int i = 1; i < MAX_WIDTH - 1; i++) {
            for (int j = 1; j < MAX_HEIGHT - 1; j++) {
                if (getSpace(new Position(i, j)).isFree()) {
                    if ((getSpace(new Position(i, j - 1)).isFree() || getSpace(new Position(i, j - 1)).getType() == SpaceType.FORBIDDEN) &&
                            (getSpace(new Position(i + 1, j)).isFree() || getSpace(new Position(i + 1, j)).getType() == SpaceType.FORBIDDEN) &&
                            (getSpace(new Position(i, j + 1)).isFree() || getSpace(new Position(i, j + 1)).getType() == SpaceType.FORBIDDEN) &&
                            (getSpace(new Position(i - 1, j)).isFree() || getSpace(new Position(i - 1, j)).getType() == SpaceType.FORBIDDEN)) {
                        freeSidesTiles.add(getSpace(new Position(i, j)));
                    }
                }

            }
        }
        return freeSidesTiles;
    }

    public List<Space> getDrawableTiles() {
        List<Space> drawablesTiles = new ArrayList<Space>();

        for (int i = 1; i < MAX_WIDTH - 1; i++) {
            for (int j = 1; j < MAX_HEIGHT - 1; j++) {
                if (getSpace(new Position(i, j)).isFree()) {
                    if (
                            (getSpace(new Position(i, j - 1)).isFree() || getSpace(new Position(i, j - 1)).getType() == SpaceType.FORBIDDEN) ||
                                    (getSpace(new Position(i + 1, j)).isFree() || getSpace(new Position(i + 1, j)).getType() == SpaceType.FORBIDDEN) ||
                                    (getSpace(new Position(i, j + 1)).isFree() || getSpace(new Position(i, j + 1)).getType() == SpaceType.FORBIDDEN) ||
                                    (getSpace(new Position(i - 1, j)).isFree() || getSpace(new Position(i - 1, j)).getType() == SpaceType.FORBIDDEN)) {

                        drawablesTiles.add(getSpace(new Position(i, j)));
                    }
                }

            }
        }
        return drawablesTiles;


    }

    public List<CommongGoalCard> getCommonGoalCards() {
        // list or array?
        // CommonGoalCard[]
        return null;
    }


    public void refill() {
        if (getDrawableTiles().size() == getAllFree().size()) {
            bag.insertTiles(getAllFree().stream().map(x -> x.getTile()).collect(Collectors.toList()));
            for (Space space : getAllFree()) {
                space.drawTile();
            }
            placeTilesRandomly();
        }
    }

    private void placeTilesRandomly() {
        for (int i = 1; i < MAX_WIDTH; i++) {
            for (int j = 1; j < MAX_HEIGHT; j++) {
                if (getSpace(new Position(i, j)).getType() == SpaceType.DEFAULT) {
                    getSpace(new Position(i, j)).setTile(bag.drawTile());
                }
            }
        }


    }
}