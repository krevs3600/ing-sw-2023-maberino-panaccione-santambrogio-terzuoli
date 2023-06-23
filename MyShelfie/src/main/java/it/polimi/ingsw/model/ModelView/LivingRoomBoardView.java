package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.utils.Position;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Class LivingRoomBoardView</h1>
 * This class is the immutable version of class LivingRoomBoard
 *
 * @author Carlo Terzuoli
 * @version 1.0
 * @since 5/06/2023
 */
public class LivingRoomBoardView implements Serializable {
    /**
     * LivingRoomBoard's width
     */
    private final int MAX_WIDTH;
    /**
     * LivingRoomBoard's height
     */
    private final int MAX_HEIGHT;
    /**
     * LivingRoomBoard's matrix of immutable spaces
     */
    private final SpaceView[][] spaces;
    /**
     * LivingRoomBoard's immutable Bag
     */
    private final BagView bagView;
    /**
     * LivingRoomBoard's list of immutable CommonGoalCards
     */
    private final List<CommonGoalCardView> commonGoalCards;
    /**
     * UID version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for class LivingRoomBoardView
     * @param livingRoomBoard object to create immutable version
     */
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

    /**
     * Getter method for the spaces' matrix
     * @return the spaces' matrix
     */
    public SpaceView[][] getSpaces() {
        return this.spaces;
    }

    /**
     * Getter method for the CommonGoalCards
     * @return the list of CommonGoalCards
     */
    public List<CommonGoalCardView> getCommonGoalCards () {
        return this.commonGoalCards;
    }

    /**
     * Getter method for the Bag
     * @return the immutable Bag
     */
    public BagView getBag () {
        return this.bagView;
    }

    /**
     * Getter method for the Space
     * @param position from where to retrieve the space
     * @return the SpaceView at that specific position
     */
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

    /**
     * Method useful for GameView string representation. Each line of the LivingRoomBoard is indexed by an integer
     * to print the LivingRoomBoard line by line where is needed.
     * @return map HashMap indexing line's number with string's line
     */
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
