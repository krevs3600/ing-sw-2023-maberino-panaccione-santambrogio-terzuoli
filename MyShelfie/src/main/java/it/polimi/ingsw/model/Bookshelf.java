package it.polimi.ingsw.model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Class Bookshelf</h1>
 * The class Bookshelf contains a matrix of item tiles owned by a Player
 *
 * @author Francsca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */

public class Bookshelf {

    private int NumberOfTiles;
    private static final int MAX_WIDTH=5;

    private static final int MAX_HEIGHT=6;

    private ItemTile[][] grid=new ItemTile[MAX_HEIGHT][MAX_WIDTH];

    /**
     * Class contructor
     */
    public Bookshelf() {
        this.NumberOfTiles = 0;
    }


    /**
     * This method is used to know the number of insertable tiles in a certain column,
     * that is, the number of item tiles that can be inserted before completely filling up the column
     * @param column the specified column from which the number of insertable tiles are checked
     * @return int It returns the number of insertable tiles in the given column
     * @exception IndexOutOfBoundsException The exception is thrown if the integer representing the column is not within the available range
     */

    public int getNumberInsertableTilesColumn(int column) throws IndexOutOfBoundsException {
        if (column >= 0 && column < MAX_WIDTH) {
            int number = 0;
            for (int i = 0; i < MAX_HEIGHT; i++) {
                if ((grid[i][column] == null)) {
                    number++;
                }
            }
            return number;
        } else {
            throw new IndexOutOfBoundsException("Invalid column, please select a column ranging from 0 to " + (MAX_WIDTH-1));
        }
    }

    /**
     * This method is used to know the number of insertable tiles in the bookshelf,
     * that is, the maximum among the number of insertable tiles of all the columns
     * @return int It returns the maximum number of insertable tiles in a bookshelf column
     */

    public int getNumberInsertableTiles(){
        int count,max=0;
        for(int j=0;j<MAX_WIDTH;j++){
            count=getNumberInsertableTilesColumn(j);
            if(count>max) max=count;
        }
        return max;
    }

    /**
     * This method is used to know if the bookshelf is full,
     * that is, the item tiles can no longer be inserted
     * @return boolean It returns true if the bookshelf is full, false otherwise
     */

    public boolean isFull(){
        if (this.getNumberInsertableTiles()==0) return true;
        return false;

    }

    /**
     * This method is used to insert all the tiles of the tile pack in the specified column of the bookshelf
     * @param tp the tile pack that is emptied and from which the item tiles are inserted in the bookshelf
     * @param column the selected column where the item tiles are inserted
     * @exception IndexOutOfBoundsException The exception is thrown if the selected column is not within the available range,
     * or if the selected column has not enough space to receive all the item tiles to insert
     */

    public void insertTile(TilePack tp,int column) throws IndexOutOfBoundsException{
        if (column >= 0 && column < MAX_WIDTH) {
            int insertableTiles = getNumberInsertableTilesColumn(column);
            if (insertableTiles >= tp.getTiles().size()) {
                int size = tp.getTiles().size();
                for (int j = 0; j < size; j++) { //
                    grid[insertableTiles - j - 1][column] = tp.getTiles().get(0);
                    tp.getTiles().remove(0);
                    this.NumberOfTiles++;
                }

            } else {
                throw new IndexOutOfBoundsException("Not enough space in this column, please select another column or remove some tiles from the tile pack");
            }
        }
        else {
            throw new IndexOutOfBoundsException("Invalid column, please select a column ranging from 0 to " + (MAX_WIDTH-1));
        }
    }

    /**
     * This method is used within the getNumberAdjacentTiles method to check if the neighbour tiles of a given position has the same tile type
     * @param i the row of the specified position
     * @param j the column of the specified position
     * @param auxiliary the auxiliary matrix of integers recording if the item tile of the same position has been already passed through (1), type checked (2) or otherwise (0)
     * @param counter the counter of the number of adjacent tiles of the group being checked
     * @param type the type of item tiles whose group of adjacent ones is being checked
     * @exception IndexOutOfBoundsException The exception is thrown if the selected column is not within the available range,
     * or if the selected column has not enough space to receive all the item tiles to insert
     */
    private void checkNeighbourTiles (int i, int j, int[][]auxiliary, int counter, TileType type) {
        auxiliary[i][j] = 1;
        counter++;
        if (i < 5 && grid[i + 1][j] != null && grid[i + 1][j].getType().equals(type) && auxiliary[i + 1][j] == 0) {
            auxiliary[i + 1][j] = 2;

        }
        if (i > 0 && grid[i - 1][j] != null && grid[i - 1][j].getType().equals(type) && auxiliary[i - 1][j] == 0) {
            auxiliary[i - 1][j] = 2;
        }

        if (j < 4 && grid[i][j + 1] != null && grid[i][j + 1].getType().equals(type) && auxiliary[i][j + 1] == 0) {
            auxiliary[i][j + 1] = 2;

        }

        if (j > 0 && grid[i][j - 1] != null && grid[i][j - 1].getType().equals(type) && auxiliary[i][j - 1] == 0) {
            auxiliary[i][j - 1] = 2;

        }
    }

    /**
     * This method is used to know if and how many groups of adjacent item tiles of the same given type are there in the bookshelf
     * @param type the type of adjacent item tiles
     * @return Map<Integer, Integer> It returns a map where the keys are the number of adjacent tiles of a group
     * and the values are the number of groups of the key type present in the bookshelf
     */

    public Map<Integer, Integer> getNumberAdjacentTiles(TileType type) {
        Map<Integer, Integer> m = new HashMap<>();

        boolean due = false;
        int counter = 0;
        boolean onefound = true;
        int[][] auxiliary = new int[MAX_HEIGHT][MAX_WIDTH];

        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                auxiliary[i][j] = 0;
            }
        }

        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                if (grid[i][j]!=null && grid[i][j].getType().equals(type) && auxiliary[i][j] == 0) {
                    checkNeighbourTiles (i,j,auxiliary,counter,type);

                    while (i < MAX_HEIGHT && onefound) {
                        while (j < MAX_WIDTH) {
                            if (auxiliary[i][j] == 2) {
                                checkNeighbourTiles (i,j,auxiliary,counter,type);
                            }
                            j++;
                        }

                        onefound=false;
                        for (int k = 0; k < MAX_WIDTH && !onefound; k++) {
                            if (auxiliary[i][k] == 1)
                                onefound = true;
                        }

                        if (onefound) {
                            for (int k = 0; k <MAX_WIDTH && !due; k++) {
                                if (auxiliary[i][k] == 2) {
                                    due = true;
                                    i--;
                                }
                            }
                            i++;
                            j=0;
                        }
                    }

                    if (m.containsKey(counter)) m.replace(counter, m.get(counter) + 1);
                    else {m.put(counter, 1);}
                    counter = 0;
                    i=0;
                    j=0;
                    onefound=true;
                }
            }
        }
        return m;
    }

    /**
     * This getter method gets the number of tiles present in the bookshelf
     * @return int It returns the number of tiles present in the bookshelf
     */
    public int getNumberOfTiles(){
        return this.NumberOfTiles;
    }

    /**
     * This getter method gets the grid of the bookshelf
     * @return ItemTile[][] It returns the matrix of item tiles representing the bookshelf
     */
    public ItemTile[][] getGrid() {
        return this.grid;
    }

    /**
     * This getter method gets the maximum width of the bookshelf
     * @return int It returns teh number of columns
     */
    public int getMaxWidth () {
        return MAX_WIDTH;
    }

    /**
     * This getter method gets the maximum height of the bookshelf
     * @return int It returns teh number of rows
     */
    public int getMaxHeight () {
        return MAX_HEIGHT;
    }

}
