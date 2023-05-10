package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.utils.Position;


import java.util.ArrayList;
import java.util.List;

public class LivingRoomBoardView{
    private final int MAX_WIDTH = 9;
    private final int MAX_HEIGHT = 9;

    private final LivingRoomBoard livingRoom;

    public LivingRoomBoardView (LivingRoomBoard livingRoom) {
        this.livingRoom = livingRoom;
    }

    public SpaceView[][] getSpaces() {
        SpaceView[][] livingRoomBoard = new SpaceView[MAX_WIDTH][MAX_HEIGHT];
        for (int i =0; i< MAX_WIDTH; i++){
            for (int j =0; j< MAX_HEIGHT; i++){
                livingRoomBoard[i][j] = new SpaceView(livingRoom.getSpace(new Position(i,j)));
            }
        }
        return livingRoomBoard;
    }

    public List<CommonGoalCard> getCommonGoalCards () {
        return livingRoom.getCommonGoalCards();
    }

    public Bag getBag () {
        return livingRoom.getBag();
    }

    public SpaceView getSpace(Position position){return new SpaceView(livingRoom.getSpace(position));}

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        String board = "";
        board = board.concat(cliPrintHorizontalNums()).concat("\n");
        board = board.concat(cliPrintLine()).concat("\n");
        for (int i=0; i < MAX_HEIGHT; i++){
            board = board.concat("\033[0;31m"+i + "\033[0m ");
            for (int j=0; j<MAX_WIDTH; j++){
                board = board.concat( "|" + getSpace(new Position(i,j)).toString());
            }
            board = board.concat("|\n");
            board = board.concat(cliPrintLine()).concat("\n");
        }
        return board;
    }

    /**
     * This private method is an auxiliary method for the toString method of the class
     * @return String It returns the string representing the horizontal numbers
     */
    private String cliPrintHorizontalNums(){
        String line = "   ".concat("\033[0;31m");
        for (int i=0; i<MAX_WIDTH; i++){
            line = line.concat(" " + i + "  ");
        }
        return line.concat("\033[0m");
    }

    /**
     * This private method is an auxiliary method for the toString method of the class
     * @return String It returns the string representing a line of the board
     */
    private String cliPrintLine(){
        String line = "  +";
        for (int i=0; i<MAX_WIDTH; i++){
            line = line.concat("---+");
        }
        return line;
    }
}
