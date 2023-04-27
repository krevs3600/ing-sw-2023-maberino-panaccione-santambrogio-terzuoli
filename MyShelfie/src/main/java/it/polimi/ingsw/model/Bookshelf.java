package it.polimi.ingsw.model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Bookshelf {

    private int NumberOfTiles;
    private static final int MAX_WIDTH=5;

    private static final int MAX_HEIGHT=6;

    private ItemTile[][] grid=new ItemTile[MAX_HEIGHT][MAX_WIDTH];

    public Bookshelf() {
        this.NumberOfTiles = 0;
    }

    public int getNumberOfTiles(){
        return this.NumberOfTiles;
    }

    public int getNumberInsertableTiles(){
        int count,max=0;
        for(int j=0;j<MAX_WIDTH;j++){
            count=getNumberInsertableTilesColumn(j);
            if(count>max) max=count;
        }
        return max;
    }

    public int getNumberInsertableTilesColumn(int column){
        int number=0;
        for(int i=0;i<MAX_HEIGHT;i++){
            if((grid[i][column]==null)){
                number++;
            }
        }
        return number;
    }


    public ItemTile[][] getGrid() {
        return this.grid;
    }

    public int getMaxWidth () {
        return MAX_WIDTH;
    }

    public int getMaxHeight () {
        return MAX_HEIGHT;
    }

    public void insertTile(TilePack tp,int column){
        int insertableTiles=getNumberInsertableTilesColumn(column);
        if(insertableTiles>=tp.getTiles().size()){
            for(int j=0;j<tp.getTiles().size();j++){
                grid[MAX_HEIGHT-insertableTiles+j][column] = tp.getTiles().get(j);
            }
            this.NumberOfTiles += tp.getTiles().size();
        }

    }


    public boolean isFull(){
        if (this.getNumberInsertableTiles()==0) return true;
        return false;

    }


    public Map<Integer, Integer> getNumberAdjacentTiles(TileType type) {
        Map<Integer, Integer> m = new HashMap<>();

        boolean due = false;
        int counter = 0;
        boolean onefound = true;
        boolean stop = false;
        int[][] auxiliary = new int[MAX_HEIGHT][MAX_WIDTH];

        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                auxiliary[i][j] = 0;
            }
        }


        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                if (grid[i][j]!=null && grid[i][j].getType().equals(type) && auxiliary[i][j] == 0) {
                    auxiliary[i][j] = 1;
                    counter++;
                    if (grid[i+1][j]!=null && grid[i + 1][j].getType().equals(type) &&  auxiliary[i + 1][j] ==0 ) {
                        auxiliary[i + 1][j] = 2;

                    }
                    if (grid[i-1][j]!=null && grid[i - 1][j].getType().equals(type) && auxiliary[i - 1][j]== 0 ) {
                        auxiliary[i - 1][j] = 2;

                    }

                    if (grid[i][j+1]!=null && grid[i][j + 1].getType().equals(type) && auxiliary[j][j + 1]== 0) {
                        auxiliary[j][j + 1] = 2;

                    }

                    if (grid[i][j-1]!=null && grid[i][j - 1].getType().equals(type) && auxiliary[i][j - 1] ==0) {
                        auxiliary[i][j - 1] = 2;

                    }

                    while (i < MAX_HEIGHT && onefound) {
                        while (j < MAX_WIDTH) {
                            if (auxiliary[i][j] == 2) {
                                auxiliary[i][j] = 1;
                                counter++;
                                if (grid[i+1][j]!=null && grid[i + 1][j].getType().equals(type) && auxiliary[i+1][j]==0) {
                                    auxiliary[i + 1][j] = 2;
                                }
                                if (grid[i-1][j]!=null && grid[i - 1][j].getType().equals(type) && auxiliary[i-1][j]==0) {
                                    auxiliary[i - 1][j] = 2;
                                }

                                if (grid[i][j+1]!=null && grid[i][j + 1].getType().equals(type) && auxiliary[i][j+1]==0) {
                                    auxiliary[j][j + 1] = 2;
                                }

                                if (grid[i][j-1]!=null && grid[i][j - 1].getType().equals(type) && auxiliary[i][j-1]==0) {
                                    auxiliary[i][j - 1] = 2;
                                }


                            }
                            j++;
                        }

                        onefound=false;
                        for (int k = 0; k < MAX_WIDTH && !onefound; k++) {
                            if (auxiliary[i][k] == 1)
                                onefound = true;
                        }

                        if (onefound) { //non posso avere due senza un uno
                            for (int k = 0; k <MAX_WIDTH && !due; k++) {
                                if (auxiliary[i][k] == 2) {
                                    due = true;
                                    i--;

                                }

                            }
                            i++;

                        }


                    }

                    if (m.containsKey(counter)) {

                        m.replace(counter, m.get(counter) + 1);


                    } else {

                        m.put(counter, 1);

                    }
                    counter = 0;


                }
            }
        }


        return m;


    }

}
