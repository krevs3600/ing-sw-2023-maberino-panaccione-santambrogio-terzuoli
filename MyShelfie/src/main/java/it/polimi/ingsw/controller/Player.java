package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.Map;

public class Player {
    private final String name;
    private PlayerStatus status;
    //private final Player nextPlayer;
    //private final boolean firstPlayer;

    private final Bookshelf bookshelf;
    private int score;

    private final PersonalGoalCard personalGoalCard;
    private List<ScoringToken> tokens;

    /**
     * Class constructor
     * @param name name of the player
     * @param personalGoalCardDeck from which the player will get his own personal goal card
     */
    public Player(String name, PersonalGoalCardDeck personalGoalCardDeck){
        this.name = name;
        this.bookshelf = new Bookshelf();
        this.score = 0;
        this.personalGoalCard = (PersonalGoalCard) personalGoalCardDeck.draw();
        this.tokens = new ArrayList<>();
    }

    public PlayerStatus getStatus(){
        return this.status;
    }

    public String getName(){
        return this.name;
    }

    //public Player getNextPlayer() {
    //    return nextPlayer;
    //}

    /**
     * This method allows a player to insert an Item tile he pulled in his bookshelf
     * @param tp the tile pack from which the tile is retrieved
     * @param column the column of the bookshelf in which the tile will then be placed
     * @throws IndexOutOfBoundsException for invalid column index
     */
    public void insertTile(TilePack tp, int column) throws IndexOutOfBoundsException{
        try{
            bookshelf.insertTile(tp, column);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Error, please select a valid column");
        }

    }

    /**
     * This method allows a player to pick a tile from the board
     * @param board from which the tile is picked
     * @param pos the position inside the board from which the tile is picked
     * @return the item tile that is picked by the player
     */
    public ItemTile pickUpTile(LivingRoomBoard board, Position pos) {
        return board.getSpace(pos).drawTile();
    }

    /**
     * This method is used to compute the final score of the player at the end of the game
     * @return the final score of the player
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
     * This method is used to keep track of changes in the score of a player during the game. In particular,
     * the score is updated every time the player receives a scoring token
     * @return the updated score of the player
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

    public Bookshelf getBookshelf(){
        return this.bookshelf;
    }
}
