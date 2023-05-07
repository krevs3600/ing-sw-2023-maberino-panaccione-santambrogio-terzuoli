package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.TileType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.Map;

/**
 * <h1>Class Bag</h1>
 * The class Bag contains all the item tiles that are not placed on the board or on the Players' bookshelves
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/28/2023
 */
public class Player {
    private String name;
    private PlayerStatus status;

    private Bookshelf bookshelf;
    private int score;
    private PersonalGoalCard personalGoalCard;
    private List<ScoringToken> tokens;

    /**
     * Class constructor
     * @param name the name of the player
     * @param personalGoalCardDeck the goal card deck from which the player takes his own personal goal card
     */
    public Player(String name, PersonalGoalCardDeck personalGoalCardDeck){
        this.name = name;
        this.bookshelf = new Bookshelf();
        this.score = 0;
        this.personalGoalCard = (PersonalGoalCard) personalGoalCardDeck.draw();
        this.tokens = new ArrayList<>();
    }

    /**
     * This method allows a player to pick a tile from the board
     * @param board the living room board from which the tile is picked
     * @param pos the position inside the board from which the tile is picked
     * @return ItemTile It returns the item tile picked by the player
     */
    public ItemTile pickUpTile(LivingRoomBoard board, Position pos) {
        return board.getSpace(pos).drawTile();
    }

    /**
     * This method allows a player to insert an Item tile he previously picked in his bookshelf
     * @param column the column of the bookshelf in which the tile will then be placed
     * @throws IndexOutOfBoundsException The exception is thrown if the chosen column index is invalid
     */
    public void insertTile(TilePack tilePack, int column) throws IndexOutOfBoundsException{
        try{
            bookshelf.insertTile(tilePack, column);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid column, please select another one valid");
        }

    }

    /**
     * This method is used to compute the final score of the player at the end of the game
     * @return int It returns the final score of the player
     */
    public int computeScoreEndGame() {
        //Computation of points from personal goal card
        ItemTile[][] bookshelf = this.bookshelf.getGrid();
        //personalGoalCard.getScoringItem().forEach((key, value) -> );
        int count = 0;
        for (Map.Entry<Integer, TileType> element :
                personalGoalCard.getScoringItem().entrySet()) {
            if (bookshelf[(element.getKey())/5][(element.getKey())%5].getType().equals(element.getValue())) {
                count++;
            }
        }

        ArrayList<Integer> points;
        try {
            Reader file = new FileReader("src/main/java/it/polimi/ingsw/model/PersonalGoalCards.json");
            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) jsonObj;
            // read points from json
            points = (ArrayList<Integer>) jsonObject.get("points");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        score+=points.get(count);

        //Computation of points from adjacent tiles groups at end of game
        ArrayList<List<Integer>> pointsAdj = new ArrayList<>();
        pointsAdj.add(Arrays.asList(3, 4, 5, 6));
        pointsAdj.add(Arrays.asList(2, 3, 5, 8));

        for (TileType type : TileType.values()) {
            Map<Integer, Integer> adjacentTiles = this.bookshelf.getNumberAdjacentTiles(type);
            for (Integer key : adjacentTiles.keySet()) {
                for (int i = 0; i < pointsAdj.get(0).size(); i++) {
                    if (key.equals(pointsAdj.get(0).get(i))) {
                        score = score + (pointsAdj.get(1).get(i)) * adjacentTiles.get(key);
                    } else if (key > pointsAdj.get(0).get(3)) {
                        score = score + (pointsAdj.get(1).get(3)) * adjacentTiles.get(key);
                    }
                }
            }

        }
        return score;
    }


    /**
     * This method is used to keep track of score changes of a player during the game.
     * In particular, the score is updated every time the player receives a scoring token
     * @return int It returns the updated score of the player
     */
    public int computeScoreMidGame(CommonGoalCardDeck deck){
        List<CommonGoalCard> commonGoalCards = deck.getDeck();
        for(CommonGoalCard card : commonGoalCards) {
            if (card.toBeChecked(bookshelf)) {
                if(card.CheckPattern(bookshelf)){
                    //ScoringToken tempToken = card.getStack().pop();
                    //score+=tempToken.getValue();
                    score+=card.getStack().pop().getValue();
                }
            }
        }

        return score;
    }

    /**
     * This method is used to add a scoring token to the player's collection when he wins it
     */
    public void winToken(ScoringToken scoringToken){
        this.tokens.add(scoringToken);
    }


    /**
     * This getter method gets the status of the player
     * @return PlayerStatus It returns the value of an enumeration representing the current status of the player
     */
    public PlayerStatus getStatus(){
        return this.status;
    }

    /**
     * This getter method gets the name of the player
     * @return String It returns the string representing the name of the player
     */
    public String getName(){
        return this.name;
    }

    /**
     * This getter method gets the first player seat, if he is the first player
     * @return String It returns the string representing the name of the player
     */
    public FirstPlayerSeat getFirstPlayerSeat(){
        return null;
    }

    /**
     * This setter method sets the status of the player
     */
    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    /**
     * This getter method gets the bookshelf of the player
     * @return Bookshelf It returns the personal bookshelf of the player
     */
    public Bookshelf getBookshelf(){
        return this.bookshelf;
    }

    public int getScore () { return this.score;}

    public PersonalGoalCard getPersonalGoalCard () { return this.personalGoalCard;}

    public List<ScoringToken> getTokens () { return this.tokens;}

}
