package it.polimi.ingsw.model.utils;

import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.Space;

import java.io.Serializable;

/**
 * <h1>Class Position</h1>
 * The class Position represents the coordinates of a {@link Space} in the {@link LivingRoomBoard}
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/21/2023
 */

public class Position implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MAX_WIDTH = 9;
    private static final int MAX_HEIGHT = 9;
    private final int row;
    private final int column;

    /**
     * Constructor that encapsulates the space coordinates in the {@link LivingRoomBoard}
     * @param row the row of the coordinates
     * @param column the column of the coordinates
     * @throws IllegalArgumentException The exception is thrown if the coordinates are invalid
     */
    public Position(int row, int column) throws IllegalArgumentException{
        if (row < 0 || column < 0 || column > MAX_WIDTH || row > MAX_HEIGHT){
            throw new IllegalArgumentException("invalid coordinates, please insert values within the available ranges");
        } else {
            this.row = row;
            this.column = column;
        }
    }

    /**
     * Getter method
     * @return {@code int} representing the row of the coordinates
     */
    public int getRow(){
        return row;
    }

    /**
     * Getter method
     * @return {@code int} representing the column of the coordinates
     */
    public int getColumn(){
        return column;
    }

    /**
     * This method tells whether the {@link Position} is adjacent to the given one
     * @param position the {@link Position} we want to establish if adjacent
     * @return {@code boolean} telling if the {@link Position} is adjacent
     */
    public boolean isAdjacent(Position position){
        if(this.getColumn() == position.getColumn()){
            if((this.getRow() == position.getRow() +1) || (this.getRow() == position.getRow() -1)){
                return true;

            }
        }

        if(this.getRow() == position.getRow()) {
            if ((this.getColumn() == position.getColumn() + 1) || (this.getColumn() == position.getColumn() - 1)) {
                return true;
            }
        }
        return false;
}

    /**
     * To String method
     * @return {@code String} version representing the {@link Position}
     */
    @Override
    public String toString(){
        return "(" + this.row + "," + this.column + ")";
    }

}
