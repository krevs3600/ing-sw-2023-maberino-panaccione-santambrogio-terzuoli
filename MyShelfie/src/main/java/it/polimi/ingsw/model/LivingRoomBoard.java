package it.polimi.ingsw.model;


import java.util.List;

class LivingRoomBoard(){
    private final int MAX_WIDTH = 9;
    private final int MAX_HEIGHT = 9;
    private Space[][] spaces = new Space[][];
    private int numberOfPlayers;

    private final int[][] twoPlayerSetup = {{9,0,0},{3,2,4},{3,3,4},{2,6,1},   {7,1,1},   {1,6,2},{3,3,3},{4,2,3},{9,0,0,}}; // PROVA
    private final int[][] threePlayerSetup = {{9,0,0},{3,2,4},{3,3,4},{2,6,1},   {7,1,1},   {1,6,2},{3,3,3},{4,2,3},{9,0,0,}}; // PROVA
    private final int[][] fourPlayerSetup = {{9,0,0},{3,2,4},{3,3,4},{2,6,1},   {7,1,1},   {1,6,2},{3,3,3},{4,2,3},{9,0,0,}}; // PROVA


    /**
     * Initialization of game board
     * @author Carlo, FraMabe
     * @param numberOfPlayers int
     */
    public LivingRoomBoard(int numberOfPlayers){
        int[][] activeList = null;

        switch (numberOfPlayers){
            case 2: activeList = twoPlayerSetup;
            case 3: activeList = threePlayerSetup;
            case 4: activeList = fourPlayerSetup;
            default: activeList = twoPlayerSetup;
        }

        int c,r = 0;
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

    public ItemTile getTile(){
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

    public boolean allSpaceHaveAdjacent(){
        //return boolean
        return false;
    }

    public void refill(){

    }



}