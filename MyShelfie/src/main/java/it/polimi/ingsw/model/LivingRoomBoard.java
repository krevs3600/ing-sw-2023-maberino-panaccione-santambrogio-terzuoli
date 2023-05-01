package it.polimi.ingsw.model;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>Class LivingRoomBoard</h1>
 * The class LivingRoomBoard represents the central board of the game where the item tiles are placed
 * and from which they can be picked by the players
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/21/2023
 */

public class LivingRoomBoard {
    private static final int MAX_WIDTH = 9;
    private static final int MAX_HEIGHT = 9;
    private final Space[][] spaces = new Space[MAX_WIDTH][MAX_HEIGHT];

    private List<CommonGoalCard> commonGoalCards;

    private final Bag bag = new Bag();

    /**
     * Class constructor
     * @param numberOfPlayers the number of players on which the configuration of playable spaces depends
     * @exception  IllegalArgumentException The exception is thrown if numberOfPlayers is not within the proper interval
     */

    public LivingRoomBoard(NumberOfPlayers numberOfPlayers) throws IllegalArgumentException {
        ArrayList<ArrayList<Long>> activeList;
        CommonGoalCardDeck commonGoalCardDeck = new CommonGoalCardDeck();

        String path = "src/main/java/it/polimi/ingsw/model/LivinRoomBoard.json";
        try {
            Reader file = new FileReader(path);
            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) jsonObj;
            // read config from json
            activeList = (ArrayList<ArrayList<Long>>) jsonObject.get(numberOfPlayers.name());

            int c = 0, r = 0;
            for (ArrayList<Long> list : activeList) {
                // forbidden tiles
                for (int i = 0; i < list.get(0); i++) {
                    Position position = new Position(r, c);
                    spaces[r][c] = new Space(SpaceType.FORBIDDEN, position);
                    c++;
                }
                // active tiles
                for (int i = 0; i < list.get(1); i++) {
                    Position position = new Position(r, c);
                    spaces[r][c] = new Space(SpaceType.DEFAULT, position);
                    c++;
                }
                // forbidden tiles
                for (int i = 0; i < list.get(2); i++) {
                    Position position = new Position(r, c);
                    spaces[r][c] = new Space(SpaceType.FORBIDDEN, position);
                    c++;
                }
                c=0;
                r++;
            }
            placeTilesRandomly();
            // TODO: modify draw method introducing a correct input (nop, roman)
            this.commonGoalCards.add(commonGoalCardDeck.draw());
            this.commonGoalCards.add(commonGoalCardDeck.draw());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method returns the space of the given position
     * @param position the position of the space of interest
     * @return Space It returns the space of interest
     */
    public Space getSpace(Position position) {
        return spaces[position.getRow()][position.getColumn()];
    }

    /**
     * This method finds all the spaces from the LivingRoomBoard with all free spaces on their side
     * @return List<Space> It returns a list with all the free spaces
     */
    public List<Space> getAllFree() {
        List<Space> freeSidesTiles = new ArrayList<>();

        for (int i = 1; i < MAX_WIDTH - 1; i++) {
            for (int j = 1; j < MAX_HEIGHT - 1; j++) {
                if (!getSpace(new Position(i, j)).isFree() && getSpace(new Position(i, j)).getType() != SpaceType.FORBIDDEN) {
                    if (
                            (getSpace(new Position(i, j - 1)).isFree() || getSpace(new Position(i, j - 1)).getType() == SpaceType.FORBIDDEN) &&
                                    (getSpace(new Position(i + 1, j)).isFree() || getSpace(new Position(i + 1, j)).getType() == SpaceType.FORBIDDEN) &&
                                    (getSpace(new Position(i, j + 1)).isFree() || getSpace(new Position(i, j + 1)).getType() == SpaceType.FORBIDDEN) &&
                                    (getSpace(new Position(i - 1, j)).isFree() || getSpace(new Position(i - 1, j)).getType() == SpaceType.FORBIDDEN)
                    ) {
                        freeSidesTiles.add(getSpace(new Position(i, j)));
                    }
                }
            }
        }
        return freeSidesTiles;
    }

    /**
     * This method finds all the spaces from the LivingRoomBoard that have at least one free side.
     * @return List<Space> It returns a list with all the spaces with one free side
     */
    public List<Space> getDrawableTiles() {
        List<Space> drawablesTiles = new ArrayList<>();

        for (int i = 0; i < MAX_WIDTH; i++) {
            for (int j = 0; j < MAX_HEIGHT; j++) {
                if (!getSpace(new Position(i, j)).isFree() && getSpace(new Position(i,j)).getType() == SpaceType.DEFAULT) {
                    // inner spaces
                    if (i==0 || i==MAX_HEIGHT-1 || j==0 || j==MAX_WIDTH-1){
                        drawablesTiles.add(getSpace(new Position(i, j)));
                    }
                    else if (
                            (getSpace(new Position(i, j - 1)).isFree() || getSpace(new Position(i, j - 1)).getType() == SpaceType.FORBIDDEN) ||
                                    (getSpace(new Position(i + 1, j)).isFree() || getSpace(new Position(i + 1, j)).getType() == SpaceType.FORBIDDEN) ||
                                    (getSpace(new Position(i, j + 1)).isFree() || getSpace(new Position(i, j + 1)).getType() == SpaceType.FORBIDDEN) ||
                                    (getSpace(new Position(i - 1, j)).isFree() || getSpace(new Position(i - 1, j)).getType() == SpaceType.FORBIDDEN)
                    ) {
                        drawablesTiles.add(getSpace(new Position(i, j)));
                    }

                }

            }
        }
        return drawablesTiles;


    }

    //TODO: getCommonGoalCards
    public List<CommonGoalCard> getCommonGoalCards() {
        return commonGoalCards;
    }

    /**
     * This method refills the living room board with new ItemTiles.
     * First, the item tiles left on the board without any other adjacent tile are put back in the bag
     * Then, new ones are drawn and placed.
     */
    public void refill() {
        if (getDrawableTiles().size() == getAllFree().size()) { // this condition will probably go in the controller
            bag.insertTiles(getAllFree().stream().map(Space::getTile).collect(Collectors.toList()));
            for (Space space : getAllFree()) {
                space.drawTile();
            }
            placeTilesRandomly();
        }
    }

    /**
     * This method draws the ItemTiles from the bag and places them randomly in the LivingRoomBoard on the playable spaces.
     */
    private void placeTilesRandomly() {
        for (int i = 0; i < MAX_WIDTH; i++) {
            for (int j = 0; j < MAX_HEIGHT; j++) {
                if (getSpace(new Position(i, j)).getType() == SpaceType.DEFAULT) {
                    getSpace(new Position(i, j)).setTile(bag.drawTile());
                }
            }
        }
    }

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

    private String cliPrintHorizontalNums(){
        String line = "   ".concat("\033[0;31m");
        for (int i=0; i<MAX_WIDTH; i++){
            line = line.concat(" " + i + "  ");
        }
        return line.concat("\033[0m");
    }
    private String cliPrintLine(){
        String line = "  +";
        for (int i=0; i<MAX_WIDTH; i++){
            line = line.concat("---+");
        }
        return line;
    }
}