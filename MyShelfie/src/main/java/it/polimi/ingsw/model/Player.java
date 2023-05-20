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

    private boolean firstCommonGoalAchieved;
    private boolean secondCommonGoalAchieved;

    /**
     * Class constructor
     * @param name the name of the player
     */
    //TODO: change the moment when player draws personal goal card
    public Player(String name){
        this.name = name;
        this.bookshelf = new Bookshelf();
        this.score = 0;
        //this.personalGoalCard = (PersonalGoalCard) personalGoalCardDeck.draw();
        this.tokens = new ArrayList<>();
        this.firstCommonGoalAchieved = false;
        this.secondCommonGoalAchieved = false;
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

    public void insertTile(TilePack tilePack, int column, int index) throws IndexOutOfBoundsException{
        try{
            bookshelf.insertTile(tilePack, column, index);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid column, please select another one valid");
        }

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
    public void setScore (int incremental) { this.score += incremental;}
    public boolean isFirstCommonGoalAchieved () { return this.firstCommonGoalAchieved;}
    public boolean isSecondCommonGoalAchieved () { return this.secondCommonGoalAchieved;}
    public void hasAchievedFirstGoal () { this.firstCommonGoalAchieved=true;}
    public void hasAchievedSecondGoal () { this.secondCommonGoalAchieved=true;}

    public void setPersonalGoalCard(GoalCard personalGoalCard) {
        this.personalGoalCard = (PersonalGoalCard) personalGoalCard;
    }

}
