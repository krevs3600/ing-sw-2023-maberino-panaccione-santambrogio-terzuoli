package it.polimi.ingsw.model;

/**
 * Coordinates of class Space in the LivingRoomBoard
 */
public class Position {
    private static final int MAX_WIDTH = 9;
    private static final int MAX_HEIGHT = 9;
    private final int row;
    private final int column;

    /**
     * Constructor that encapsulates Space coordinates in the LivingRoomBoard
     * @param row
     * @param column
     * @throws IllegalArgumentException
     */
    public Position(int row, int column) throws IllegalArgumentException{
        if (row < 0 || column < 0 || row > MAX_WIDTH || row > MAX_HEIGHT){
            throw new IllegalArgumentException();
        } else {
            this.row = row;
            this.column = column;
        }
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

    @Override
    public String toString(){
        return "(" + this.row + "," + this.column + ")";
    }

}
