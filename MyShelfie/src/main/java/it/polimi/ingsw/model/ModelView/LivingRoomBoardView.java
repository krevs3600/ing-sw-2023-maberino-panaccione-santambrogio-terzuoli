package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.utils.Position;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LivingRoomBoardView implements Serializable {
    private final int MAX_WIDTH;
    private final int MAX_HEIGHT;
    private final SpaceView[][] spaces;
    private final BagView bagView;
    private final List<CommonGoalCardView> commonGoalCards;

    @Serial
    private static final long serialVersionUID = 1L;

    public LivingRoomBoardView (LivingRoomBoard livingRoomBoard) {
        this.MAX_HEIGHT = livingRoomBoard.getMaxHeight();
        this.MAX_WIDTH = livingRoomBoard.getMaxWidth();
        spaces = new SpaceView[MAX_WIDTH][MAX_HEIGHT];
        //for(CommonGoalCard card : deck.getDeck()){
        //    this.commonGoalCardDeck.add(new CommonGoalCardView(card));
        for(int i=0; i<livingRoomBoard.getMaxHeight(); i++){
            for(int j = 0; j<livingRoomBoard.getMaxWidth(); j++){
                this.spaces[i][j] = new SpaceView(livingRoomBoard.getSpace(new Position(i,j)));
            }
        }
        this.bagView = new BagView(livingRoomBoard.getBag());
        //this.commonGoalCards = livingRoomBoard.getCommonGoalCards();
        commonGoalCards = new ArrayList<>();
        for(CommonGoalCard card : livingRoomBoard.getCommonGoalCards()) {
            this.commonGoalCards.add(new CommonGoalCardView(card));
        }
    }

    public SpaceView[][] getSpaces() {
        return this.spaces;
    }

    public List<CommonGoalCardView> getCommonGoalCards () {
        return this.commonGoalCards;
    }

    public BagView getBag () {
        return this.bagView;
    }

    public SpaceView getSpace(Position position){
        return this.spaces[position.getRow()][position.getColumn()];
    }

    /**
     * This method overrides the toString method of the Object class
     * @return String It returns the textual representation of an object of the class
     */
    @Override
    public String toString(){
        String board = "";
        HashMap<Integer, String> livingRoom = toDict();
        for(int i = 0; i< livingRoom.size(); i++){
            board = board.concat(livingRoom.get(i));
            if (i< livingRoom.size()-1){
                board = board.concat("\n");
            }
        }
        return board;
    }

    public HashMap<Integer, String> toDict(){
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, cliHorizontalNums() + "      ");
        map.put(1, cliLine());
        String board = "";
        for (int i=0; i < MAX_HEIGHT*2; i+=2){
            board = board.concat("\033[0;31m"+i/2 + "\033[0m ");
            for (int j=0; j<MAX_WIDTH; j++){
                board = board.concat( "|" + getSpace(new Position(i/2,j)).toString());
            }
            board = board.concat("|");
            map.put(i+2, board);
            map.put(i+3, cliLine());
            board = "";
        }
        return map;
    }

    /**
     * This private method is an auxiliary method for the toString method of the class
     * @return String It returns the string representing the horizontal numbers
     */
    private String cliHorizontalNums(){
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
    private String cliLine(){
        String line = "  +";
        for (int i=0; i<MAX_WIDTH; i++){
            line = line.concat("---+");
        }
        return line;
    }
}
