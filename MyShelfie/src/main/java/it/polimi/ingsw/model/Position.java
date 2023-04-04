package it.polimi.ingsw.model;

/**
 * Coordinates of class Space in the LivingRoomBoard
 */
public class Position {
    private int row;
    private int column;

    /**
     * Constructor that encapsulates Space coordinates in the LivingRoomBoard
     * @param row
     * @param column
     */
    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * @return row's coordinate
     */
    public int getRow(){
        return row;
    }

    /**
     * @return column's coordinate
     */
    public int getColumn(){
        return column;
    }


}
