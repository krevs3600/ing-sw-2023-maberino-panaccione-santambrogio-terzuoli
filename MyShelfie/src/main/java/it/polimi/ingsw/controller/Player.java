package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;

public class Player {
    private final String name;
    private PlayerStatus status;

    private final Bookshelf myShelfie;
    private int score;

    private final PersonalGoalCard personalGoalCard;
    private List<ScoringToken> tokens;

    /**
     * Class constructor
     * @param name the name of the player
     * @param personalGoalCardDeck the deck of goal cards from which the player draws his personal goal of the game
     */
    public Player(String name, PersonalGoalCardDeck personalGoalCardDeck){
        this.name = name;
        this.myShelfie = new Bookshelf();
        this.score = 0;
        this.personalGoalCard = (PersonalGoalCard) personalGoalCardDeck.draw();
        this.tokens = new ArrayList<>();
    }

    /**
     * This getter method gets the status of the player
     * @return PlayerStatus It returns the enumeration reporting the current status of the player
     */
    public PlayerStatus getStatus(){
        return this.status;
    }

    /**
     * This getter method gets the name of the player
     * @return String It returns the name of player
     */
    public String getName(){
        return this.name;
    }


    /**
     * This method allows a player to insert an Item tile he pulled in his bookshelf
     * @param tp the tile pack from which the tile is retrieved
     * @param column the column of the bookshelf in which the tile is placed
     * @throws IndexOutOfBoundsException The esception is thrown if the column index is invalid
     */
    public void insertTile(TilePack tp, int column) throws IndexOutOfBoundsException{
        try{
            myShelfie.insertTile(tp, column);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid column, please select a valid column");
        }

    }

    /**
     * This method allows a player to pick a tile from the board
     * @param board the living room board from which the tile is picked
     * @param pos the position inside the board from which the tile is picked
     * @return ItemTile It returns the item tile that is picked by the player
     * @throws IllegalArgumentException The excpetion is thrown if it is not possible to pick the tile from the selected position
     */
    public ItemTile pickUpTile(LivingRoomBoard board, Position pos) throws IllegalArgumentException{
        if(board.getDrawableTiles().stream().map(Space::getPosition).toList().contains(pos)){
            return board.getSpace(pos).drawTile();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method is used to compute the final score of the player at the end of the game
     * @return int It returns the final score of the player
     */
    public int computeScoreEndGame() {
        //Computation of points from personal goal card
        ItemTile[][] bookshelf = myShelfie.getGrid();
        //personalGoalCard.getScoringItem().forEach((key, value) -> );
        int count = 0;
        for (Map.Entry<Integer, TileType> element : personalGoalCard.getScoringItem().entrySet()) {
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
            Map<Integer, Integer> adjacentTiles = myShelfie.getNumberAdjacentTiles(type);
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
     * This method is used to keep track of changes in the score of a player during the game.
     * In particular, the score is updated every time the player receives a scoring token
     * @return int the updated score of the player
     */
    public int computeScoreMidGame(CommonGoalCardDeck deck){
        List<CommonGoalCard> commonGoalCards = deck.getDeck();
        for(CommonGoalCard card : commonGoalCards) {
            if (card.toBeChecked(myShelfie)) {
                if(card.CheckPattern(myShelfie)){
                    //ScoringToken tempToken = card.getStack().pop();
                    //score+=tempToken.getValue();
                    score+=card.getStack().pop().getValue();
                }
            }
        }

        return score;
    }
    //public boolean isFirstPlayer() {
    //    return firstPlayer;
    //}

    public FirstPlayerSeat getFirstPlayerSeat(){
        return null;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public void winToken(ScoringToken scoringToken){
        this.tokens.add(scoringToken);
    }
}
