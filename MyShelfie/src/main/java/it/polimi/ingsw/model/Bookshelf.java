package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.TileType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <h1>Class Bookshelf</h1>
 * This class contains a matrix of item tiles owned by a Player
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */

public class Bookshelf {

    private int numberOfTiles;
    private static final int MAX_WIDTH=5;

    private static final int MAX_HEIGHT=6;

    private final ItemTile[][] grid;

    /**
     * Class constructor:
     * this method initializes an empty {@link Bookshelf}  setting the {@link Bookshelf#numberOfTiles} and the {@link Bookshelf#grid}
     */
    public Bookshelf() {
        this.numberOfTiles = 0;
        this.grid = new ItemTile[getMaxHeight()][getMaxWidth()];
    }


    /**
     * This method is used to know the number of insertable tiles in a certain column,
     * that is, the number of item tiles that can be inserted before completely filling up the column
     * @param column the specified column from which the number of insertable tiles are checked
     * @return an {@code int} representing the number of insertable tiles in the given column
     * @exception IndexOutOfBoundsException The exception is thrown if the integer representing the column is not within the available range
     */

    public int getNumberInsertableTilesColumn(int column) throws IndexOutOfBoundsException {
        if (column >= 0 && column < MAX_WIDTH) {
            int number = 0;
            for (int i = 0; i < getMaxHeight(); i++) {
                if ((grid[i][column] == null)) {
                    number++;
                }
            }
            return number;
        } else {
            throw new IndexOutOfBoundsException("Invalid column, please select a column ranging from 0 to " + (getMaxWidth()-1));
        }
    }

    /**
     * This method is used to know the number of insertable tiles in the bookshelf,
     * that is, the maximum among the number of insertable tiles of all the columns, known by calling the {@link #getNumberInsertableTilesColumn(int)} method foor all the columns
     * @return an {@code int} representing the maximum number of insertable tiles in a bookshelf column
     */

    public int getNumberInsertableTiles(){
        int count,max=0;
        for(int j=0;j<getMaxWidth();j++){
            count=getNumberInsertableTilesColumn(j);
            if(count>max) max=count;
        }
        return max;
    }

    /**
     * This method is used to know if the bookshelf is full,
     * that is, when the item tiles can no longer be inserted.
     * @return {@code true} if the bookshelf is full, namely when the {@link #getNumberInsertableTiles()} is 0, false otherwise
     */

    public boolean isFull(){
        return this.getNumberInsertableTiles() == 0;
    }

    /**
     * This method is used to insert all the tiles of the tile pack in the specified column of the bookshelf
     * @param tp the tile pack that is emptied and from which the item tiles are inserted in the bookshelf
     * @param column the selected column where the item tiles are inserted
     * @exception IndexOutOfBoundsException The exception is thrown if the selected column is not within the available range,
     * or if the selected column has not enough space to receive all the item tiles to insert
     */

    //TODO: da togliere
    @Deprecated
    public void insertTile(TilePack tp, int column) throws IndexOutOfBoundsException{
        if (column >= 0 && column < getMaxWidth()) {
            int insertableTiles = getNumberInsertableTilesColumn(column);
            if (insertableTiles >= tp.getTiles().size()) {
                int size = tp.getTiles().size();
                for (int j = 0; j < size; j++) { //
                    grid[insertableTiles - j - 1][column] = tp.getTiles().get(0);
                    tp.getTiles().remove(0);
                    this.numberOfTiles++;
                }

            } else {
                throw new IndexOutOfBoundsException("Not enough space in this column, please select another column or remove some tiles from the tile pack");
            }
        }
        else {
            throw new IndexOutOfBoundsException("Invalid column, please select a column ranging from 0 to " + (getMaxWidth()-1));
        }
    }

    /**
     * This method is used to insert a {@link ItemTile} taken from a specific position of the {@link TilePack}
     * @param tilePack the {@link TilePack} from which the item tile is inserted in the {@link Bookshelf}
     * @param column the selected column where the item tile is inserted
     * @param index the specified position of the {@link ItemTile} within the {@link TilePack} to insert
     * @exception IndexOutOfBoundsException The exception is thrown if the selected column is not within the available range,
     * or if the selected column has not enough space to receive the {@link ItemTile} to insert
     */

    public void insertTile(TilePack tilePack, int column, int index) throws IndexOutOfBoundsException {
        if (column >= 0 && column < getMaxWidth()) {
            int insertableTiles = getNumberInsertableTilesColumn(column);
            if (insertableTiles > 0) {
                grid[insertableTiles - 1][column] = tilePack.getTiles().get(index);
                tilePack.getTiles().remove(index);
                this.numberOfTiles++;
            } else {
                throw new IndexOutOfBoundsException("Not enough space in this column, please select another column or remove some tiles from the tile pack");
            }
        }
        else {
            throw new IndexOutOfBoundsException("Invalid column, please select a column ranging from 0 to " + (getMaxWidth()-1));
        }
    }

    /**
     * This method is used to know how many groups of adjacent {@link ItemTile}s of the same given type are there in the {@link Bookshelf}
     * It is needed for the calculation of the score based on the adjacent tiles
     * @param type the type of adjacent {@link ItemTile}s
     * @return {@code Map<Integer, Integer>} where the keys are the number of adjacent tiles of a group
     * and the values are the number of groups of the key type present in the {@link Bookshelf}
     */

    public Map<Integer, Integer> getNumberAdjacentTiles(TileType type) {
        Map<Integer, Integer> m = new HashMap<>();

        boolean due = false;
        int counter = 0;
        boolean onefound = true;
        int[][] auxiliary = new int[getMaxHeight()][getMaxWidth()];

        //creation of the auxiliary matrix
        //all the value are set as 0
        for (int i = 0; i < getMaxHeight(); i++) {
            for (int j = 0; j < getMaxWidth(); j++) {
                auxiliary[i][j] = 0;
            }
        }

        // along the entire grid of the bookshelf, if a cell of the grid contains an item tile (it is not null),
        // its type is equal to the one of an adjacent cell
        // and the cell of the same position of the auxiliary is 0, meaning the position has not been visited yet
        for (int i = 0; i < getMaxHeight(); i++) {
            for (int j = 0; j < getMaxWidth(); j++) {
                if (grid[i][j]!=null && grid[i][j].getType().equals(type) && auxiliary[i][j] == 0) {
                    // if the position of the auxiliary matrix is 1, it means that the position has been already visited
                    // and has been counted in the counter
                    auxiliary[i][j] = 1;
                    counter++;
                    if (i < 5 && grid[i + 1][j] != null && grid[i + 1][j].getType().equals(type) && auxiliary[i + 1][j] == 0) {
                        // if the adjacent position of the current one has an item tile of the same type,
                        // it is marked with the value 2 in the auxiliary matrix,
                        // meaning that it has the same type of the one currently in analysis
                        // but has not been counted in the counter yet
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

                    while (i < getMaxHeight() && onefound) {
                        while (j < getMaxWidth()) {
                            if (auxiliary[i][j] == 2) {
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
                            j++;
                        }

                        onefound=false;
                        for (int k = 0; k < getMaxWidth(); k++) {
                            if (auxiliary[i][k] == 1) {
                                onefound = true;
                                break;
                            }
                        }

                        if (onefound) {
                            for (int k = 0; k <getMaxWidth() && !due; k++) {
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
     * Getter method
     * @return the {@link Bookshelf#numberOfTiles} present in the {@link Bookshelf}
     */
    public int getNumberOfTiles(){
        return this.numberOfTiles;
    }

    /**
     * Getter method
     * @return the {@link Bookshelf#grid} representing the {@link Bookshelf} as a matrix of {@link ItemTile}s
     */
    public ItemTile[][] getGrid() {
        return this.grid;
    }

    /**
     * Getter method
     * @return {@link Bookshelf#MAX_WIDTH}, namely the number of columns
     */
    public int getMaxWidth () {
        return MAX_WIDTH;
    }

    /**
     * Getter method
     * @return {@link Bookshelf#MAX_HEIGHT}, namely the number of rows
     */
    public int getMaxHeight () {
        return MAX_HEIGHT;
    }

    //TODO: da togliere alla fine
    public void insertTileTest() {
        for (int r=0; r<6; r++){
            for(int c=0; c<5;c++){
                grid[r][c] = new ItemTile(TileType.values()[new Random().nextInt(0,5)]);
            }
        }
        grid[0][0] = null;
        grid[1][0] = null;
    }

}
