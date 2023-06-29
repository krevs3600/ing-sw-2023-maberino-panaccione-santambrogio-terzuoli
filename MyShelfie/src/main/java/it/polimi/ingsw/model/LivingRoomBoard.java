package it.polimi.ingsw.model;


import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.SpaceType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
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

public class LivingRoomBoard  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int MAX_WIDTH = 9;
    private static final int MAX_HEIGHT = 9;
    private final Space[][] spaces = new Space[MAX_WIDTH][MAX_HEIGHT];

    private final List<CommonGoalCard> commonGoalCards = new ArrayList<>();

    //private Stack<EndGameToken> endGameToken;

    private final Bag bag = new Bag();

    /**
     * Class constructor
     * This method initializes the board with the correct configuration according to the number of players,
     * utilizing {@link #placeTilesRandomly()} to place the tiles over the board.
     * Moreover, the two {@link CommonGoalCard}s for the game are picked.
     * @param numberOfPlayers the number of players on which the configuration of playable {@link Space}s depends
     * @exception  IllegalArgumentException The exception is thrown if {@link NumberOfPlayers} is not within the proper interval
     *
     */

    public LivingRoomBoard(NumberOfPlayers numberOfPlayers) throws IllegalArgumentException {
        ArrayList<ArrayList<Long>> activeList;
        CommonGoalCardDeck commonGoalCardDeck = new CommonGoalCardDeck();
        //endGameToken.push(new EndGameToken());

        String path = "src/main/java/it/polimi/ingsw/model/configs/LivingRoomBoard.json";
        //String path = "C:\\Users\\franc\\IdeaProjects\\ing-sw-2023-maberino-panaccione-santambrogio-terzuoli\\MyShelfie\\src\\main\\java\\it\\polimi\\ingsw\\model\\configs\\LivingRoomBoard.json";
        try {
            Reader file = new FileReader(path);
            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) jsonObj;
            // read config from json
            activeList = (ArrayList<ArrayList<Long>>) jsonObject.get(numberOfPlayers.name());
            //initialization of the board
            int c = 0, r = 0;
            for (ArrayList<Long> list : activeList) {
                // forbidden tiles
                for (int i = 0; i < list.get(0); i++) {
                    Position position = new Position(r, c);
                    getSpaces()[r][c] = new Space(SpaceType.FORBIDDEN, position);
                    c++;
                }
                // active tiles
                for (int i = 0; i < list.get(1); i++) {
                    Position position = new Position(r, c);
                    getSpaces()[r][c] = new Space(SpaceType.PLAYABLE, position);
                    c++;
                }
                // forbidden tiles
                for (int i = 0; i < list.get(2); i++) {
                    Position position = new Position(r, c);
                    getSpaces()[r][c] = new Space(SpaceType.FORBIDDEN, position);
                    c++;
                }
                c=0;
                r++;
            }
            placeTilesRandomly();

            CommonGoalCard cgc1 = commonGoalCardDeck.draw();
            CommonGoalCard cgc2 = commonGoalCardDeck.draw();
            cgc1.stackScoringTokens(numberOfPlayers, RomanNumber.ONE);
            cgc2.stackScoringTokens(numberOfPlayers, RomanNumber.TWO);
            this.commonGoalCards.add(cgc1);
            this.commonGoalCards.add(cgc2);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method returns the {@link Space} of the given position
     * @param position the {@link Position} of the space of interest
     * @return the {@link Space} of interest
     */
    public Space getSpace(Position position) {
        return spaces[position.getRow()][position.getColumn()];
    }

    /**
     * This method finds all the {@link Space}s from the {@link LivingRoomBoard} whose sides are all free
     * @return a list containing all the free {@link Space}s
     */
    public List<Space> getAllFreeTiles() {
        List<Space> freeSidesTiles = new ArrayList<>();

        for (int i = 0; i < MAX_WIDTH; i++) {
            for (int j = 0; j < MAX_HEIGHT; j++) {
                if (!getSpace(new Position(i, j)).isFree() && getSpace(new Position(i, j)).getType() != SpaceType.FORBIDDEN) {
                    if (i==0){
                        if (
                                (getSpace(new Position(i, j - 1)).isFree() || getSpace(new Position(i, j - 1)).getType() == SpaceType.FORBIDDEN) &&
                                        (getSpace(new Position(i + 1, j)).isFree() || getSpace(new Position(i + 1, j)).getType() == SpaceType.FORBIDDEN) &&
                                        (getSpace(new Position(i, j + 1)).isFree() || getSpace(new Position(i, j + 1)).getType() == SpaceType.FORBIDDEN)
                        ) {
                            freeSidesTiles.add(getSpace(new Position(i, j)));
                        }
                    }
                    else if (i==MAX_WIDTH-1) {
                        if (
                                (getSpace(new Position(i, j - 1)).isFree() || getSpace(new Position(i, j - 1)).getType() == SpaceType.FORBIDDEN) &&
                                        (getSpace(new Position(i, j + 1)).isFree() || getSpace(new Position(i, j + 1)).getType() == SpaceType.FORBIDDEN) &&
                                        (getSpace(new Position(i - 1, j)).isFree() || getSpace(new Position(i - 1, j)).getType() == SpaceType.FORBIDDEN)
                        ) {
                            freeSidesTiles.add(getSpace(new Position(i, j)));
                        }
                    }
                    else if (j==0) {
                        if (
                                        (getSpace(new Position(i + 1, j)).isFree() || getSpace(new Position(i + 1, j)).getType() == SpaceType.FORBIDDEN) &&
                                        (getSpace(new Position(i, j + 1)).isFree() || getSpace(new Position(i, j + 1)).getType() == SpaceType.FORBIDDEN) &&
                                        (getSpace(new Position(i - 1, j)).isFree() || getSpace(new Position(i - 1, j)).getType() == SpaceType.FORBIDDEN)
                        ) {
                            freeSidesTiles.add(getSpace(new Position(i, j)));
                        }
                    }
                    else if (j==MAX_HEIGHT-1) {
                        if (
                                (getSpace(new Position(i, j - 1)).isFree() || getSpace(new Position(i, j - 1)).getType() == SpaceType.FORBIDDEN) &&
                                        (getSpace(new Position(i + 1, j)).isFree() || getSpace(new Position(i + 1, j)).getType() == SpaceType.FORBIDDEN) &&
                                        (getSpace(new Position(i - 1, j)).isFree() || getSpace(new Position(i - 1, j)).getType() == SpaceType.FORBIDDEN)
                        ) {
                            freeSidesTiles.add(getSpace(new Position(i, j)));
                        }
                    }
                    else if (
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
     * This method finds all the {@link Space}s from the {@link LivingRoomBoard} that have at least one free side.
     * @return a list containing all the {@link Space}s which have at least one free side
     */
    public List<Space> getDrawableTiles() {
        List<Space> drawablesTiles = new ArrayList<>();

        for (int i = 0; i < MAX_WIDTH; i++) {
            for (int j = 0; j < MAX_HEIGHT; j++) {
                if (!getSpace(new Position(i, j)).isFree() && getSpace(new Position(i,j)).getType() == SpaceType.PLAYABLE) {
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

    /**
     * This method refills the {@link LivingRoomBoard} with new {@link ItemTile}s.
     * First, the item tiles left on the board without any other adjacent tile are put back in the {@link Bag}.
     * Afterwards, new ones are drawn from the {@link Bag} and placed on the {@link LivingRoomBoard}.
     */
    public void refill() {
        getBag().insertTiles(getAllFreeTiles().stream().map(Space::getTile).collect(Collectors.toList()));
        for (Space space : getAllFreeTiles()) {
            space.drawTile();
        }
        placeTilesRandomly();
    }

    /**
     * This method draws the {@link ItemTile}s from the {@link Bag} and places them randomly in the {@link LivingRoomBoard} on the playable {@link Space}s.
     */
    private void placeTilesRandomly() {
        for (int i = 0; i < MAX_WIDTH; i++) {
            for (int j = 0; j < MAX_HEIGHT; j++) {
                if (getSpace(new Position(i, j)).getType() == SpaceType.PLAYABLE) {
                    getSpace(new Position(i, j)).setTile(getBag().drawTile());
                }
            }
        }
    }

    /**
     * Getter method
     * @return {@link LivingRoomBoard#spaces}, namely the matrix of {@link Space}s representing the central board
     */
    public Space[][] getSpaces () {
        return spaces;
    }

    /**
     * Getter method
     * @return {@link LivingRoomBoard#commonGoalCards}, namely the two {@link CommonGoalCard}s active during the game
     */
    public List<CommonGoalCard> getCommonGoalCards() {
        return commonGoalCards;
    }

    /**
     * Getter method
     * @return {@link LivingRoomBoard#MAX_WIDTH}
     */
    public int getMaxWidth(){
        return MAX_WIDTH;
    }

    /**
     * Getter method
     * @return {@link LivingRoomBoard#MAX_HEIGHT} of the board
     */
    public int getMaxHeight() {
        return MAX_HEIGHT;
    }

    /**
     * Getter method
     * @return the {@link LivingRoomBoard#bag} linked to the board
     */
    public Bag getBag () {
        return bag;
    }

}