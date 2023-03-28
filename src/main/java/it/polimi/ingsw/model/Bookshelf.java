package it.polimi.ingsw.model;

public class Bookshelf {
    private int NumberOfTiles;
    private final int MAX_WIDTH=5;

    private final int MAX_HEIGHT=6;

    private ItemTile[][] grid=new ItemTile[MAX_HEIGHT][MAX_WIDTH];

    public Bookshelf() {
        this.NumberOfTiles = 0;
    }

    public int getNumberOfTiles(){
        return this.NumberOfTiles;
    }

  public int getNumberInsertableTiles(){
        int count=0,max=0;
            for(int j=0;j<MAX_WIDTH;j++){
                count=getNumberInsertableTilesColumn(j);
                if(count>max) max=count;
            }
        return max;
  }

  public int getNumberInsertableTilesColumn(int column){
        int number=0;
        for(int i=0;i<MAX_HEIGHT;i++){
            if(!(grid[i][column].containsTile())){
                number++;
            }
        }
        return number;
  }



  public int getNumberAdjacentTiles(TileType tt){


  }

  public void insertTile(TilePack tp,int column){
        int insertableTiles=getNumberInsertableTiles(column);
            if(insertableTiles>=tp.size()){
                for(int j=0;j<tp.size();j++){
                    grid[MAX_HEIGHT-insertableTiles+j][column].equals(tp.get(j));
            }
        }

  }


  public boolean isFull(){
        if (this.getNumberInsertableTiles()==0) return true;
        return false;

  }



}
