package it.polimi.ingsw.model;




class LivingRoomBoard(){
    private final int MAX_WIDTH = 9;
    private final int MAX_HEIGHT = 9;
    private Space[][] spaces = new Space[][];
    private int numberOfPlayers;

    private final int[][] twoPlayerSetup = {{9,0,0},{3,2,4},{3,3,4},{2,6,1},   {7,1,1},   {1,6,2},{3,3,3},{4,2,3},{9,0,0,}}; // PROVA

    public LivingRoomBoard(int numberOfPlayers){


    }

    public ItemTile getTile(){
        // return ItemTile
    }

    public Space[] getPickableTiles(){
        // return Space[]
    }

    public CommongGoalCard[] getCommonGoalCards(){
        // CommonGoalCard[]
    }

    public boolean allSpaceHaveAdjacent(){
        //return boolean
    }

    public void refill(){

    }



}