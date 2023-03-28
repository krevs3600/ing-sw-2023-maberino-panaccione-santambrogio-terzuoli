package it.polimi.ingsw.model;


import java.util.List;

/**
 * Game's board
 * @author Carlo
 */
class LivingRoomBoard(){
    // The board is represented as a MAX_WIDTH x MAX_HEIGHT matrix of Spaces
    private final int MAX_WIDTH = 9;
    private final int MAX_HEIGHT = 9;
    private Space[][] spaces = new Space[][];
    private int numberOfPlayers;

    // The followings are the setups for each possible number of players.
    // Each sublist has [0] and [2] indexes for FORBIDDEN and [1] for ACTIVE
    // This could change if we decide to use the THREE_PLAYER and FOUR_PLAYER SpaceType's values
    private final int[][] twoPlayerSetup = {{9,0,0},{3,2,4},{3,3,3},{2,6,1},   {1,7,1},   {1,6,2},{3,3,3},{4,2,3},{9,0,0,}};
    private final int[][] threePlayerSetup = {{3,1,5},{3,2,4},{2,5,2},{2,7,0},   {1,7,1},   {0,7,2},{2,4,3},{4,2,3},{9,0,0,}};
    private final int[][] fourPlayerSetup = {{3,2,4},{3,3,3},{3,3,4},{2,6,1},   {1,7,1},   {1,6,2},{3,3,3},{4,2,3},{9,0,0,}};


    /**
     * Initialization of game's board
     * Takes as input the number of players, and it initializes the board accordingly
     * @param numberOfPlayers int
     * @author Carlo
     */
    public LivingRoomBoard(int numberOfPlayers) throws IllegalArgumentException {
        int[][] activeList = null;

        switch (numberOfPlayers){
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
        for(int[] list: activeList){
            // forbidden tiles
            for(int i=0; i<list[0]; i++){
                Position position = new Position(r,c);
                spaces[r][c] = new Space(SpaceType.FORBIDDEN, position);
                c++;
            }
            //active tiles
            for(int i=0; i<list[1]; i++){
                Position position = new Position(r,c);
                spaces[r][c] = new Space(SpaceType.DEFAULT, position);
                c++;
            }
            //forbidden tiles
            for(int i=0; i<list[2]; i++){
                Position position = new Position(r,c);
                spaces[r][c] = new Space(SpaceType.FORBIDDEN, position);
                c++;
            }
            r++;
        }


    }



    public ItemTile getTile(Position position){
        // return ItemTile
        return null;
    }

    public List<Space> getDrawableTiles(){
        // return Space[]
        return null;
    }

    public List<CommongGoalCard> getCommonGoalCards(){
        // list or array?
        // CommonGoalCard[]
        return null;
    }

    // This method does not make much sense
    public boolean allSpaceHaveAdjacent(){
        //return boolean
        return false;
    }

    public void refill(){

    }



}