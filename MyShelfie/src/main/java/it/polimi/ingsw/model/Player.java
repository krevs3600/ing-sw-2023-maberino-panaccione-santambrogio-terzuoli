package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.utils.Position;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * <h1>Class Player</h1>
 * The class Player contains all information and items related to one player
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/28/2023
 */
public class Player  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final List<ScoringToken> tokens;
    private final Bookshelf bookshelf;
    private PlayerStatus status;
    private PersonalGoalCard personalGoalCard;
    private boolean firstCommonGoalAchieved;
    private boolean secondCommonGoalAchieved;
    private boolean firstToFinish = false;
    private int adjacentTilesScore;
    private int score;
    private int personalGoalCardScore;


    /**
     * Class constructor
     * @param name the {@link Player#name} of the player. It is unique and characterizes the player within the game
     */
    public Player(String name){
        this.name = name;
        this.bookshelf = new Bookshelf();
        this.score = 0;
        this.tokens = new ArrayList<>();
        this.firstCommonGoalAchieved = false;
        this.secondCommonGoalAchieved = false;
    }

    /**
     * This method allows a player to pick an {@link ItemTile} from the {@link LivingRoomBoard}
     * @param board the {@link LivingRoomBoard} from which the {@link ItemTile} is picked
     * @param pos the {@link Position} inside the {@link LivingRoomBoard} from which the {@link ItemTile} is picked
     * @return the {@link ItemTile} picked by the player
     */
    public ItemTile pickUpTile(LivingRoomBoard board, Position pos) {
        return board.getSpace(pos).drawTile();
    }

    /**
     * This method allows a player to insert an {@link ItemTile} he previously picked in a {@link TilePack},
     * in order for it to be inserted later in his bookshelf
     * @param tilePack the {@link TilePack} in which the {@link ItemTile} will be inserted
     * @param column the {@code int} column of the {@link Player#bookshelf} in which the {@link ItemTile} will be inserted
     * @param index the {@code int} index of the {@link TilePack} from which the {@link ItemTile} wll be taken
     * @throws IndexOutOfBoundsException The exception is thrown if the {@link TilePack} is already full
     */
    public void insertTile(TilePack tilePack, int column, int index) throws IndexOutOfBoundsException{
        try{
            bookshelf.insertTile(tilePack, column, index);
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method is used to add a {@link ScoringToken} to the player's collection when he wins it
     */
    public void winToken(ScoringToken scoringToken){
        this.tokens.add(scoringToken);
    }

    /**
     * Setter method that sets the status of the player
     * @param status the {@link Player#status} on which the player needs to be set
     */
    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    /**
     * Setter method
     * @param incremental {@code int} amount by which the {@link Player#score} needs to be raised
     */
    public void setScore (int incremental) { this.score += incremental;}

    public void setPersonalGoalCardScore(int score){
        this.personalGoalCardScore = score;
        System.out.println(getName() + " pcg: " + score);
    }

    public int getPersonalGoalCardScore() {
        return personalGoalCardScore;
    }

    public void setAdjacentTilesScore(int score){
        this.adjacentTilesScore = score;
        System.out.println(getName() + " adj: " + score);
    }

    public int getAdjacentTilesScore(){
        return this.adjacentTilesScore;
    }

    /**
     * Setter method
     * @param personalGoalCard sets the {@link Player#personalGoalCard} of the player
     */
    public void setPersonalGoalCard(GoalCard personalGoalCard) {
        this.personalGoalCard = (PersonalGoalCard) personalGoalCard;
    }

    /**
     * This method is called when the player has achieved the first {@link CommonGoalCard} of the game
     */
    public void hasAchievedFirstGoal () {
        this.firstCommonGoalAchieved = true;
    }

    /**
     * This method is called when the player has achieved the second {@link CommonGoalCard} of the game
     */
    public void hasAchievedSecondGoal () { this.secondCommonGoalAchieved=true;}


    /**
     * Getter method
     * @return {@link Player#status}, which is the value of an enumeration representing the current status of the player
     */
    public PlayerStatus getStatus(){
        return this.status;
    }

    /**
     * Getter method
     * @return the {@link Player#score} of the player
     */
    public int getScore () { return this.score;}

    /**
     * Getter method
     * @return {@link PersonalGoalCard}
     */
    public PersonalGoalCard getPersonalGoalCard () { return this.personalGoalCard;}

    /**
     * This method tells whether the player has achieved the first {@link CommonGoalCard} of the game
     * @return the {@code boolean} that communicates whether he has achieved it or not
     */
    public boolean isFirstCommonGoalAchieved () { return this.firstCommonGoalAchieved;}

    /**
     * This method tells whether the player has achieved the second {@link CommonGoalCard} of the game
     * @return the {@code boolean} that communicates whether he has achieved it or not
     */
    public boolean isSecondCommonGoalAchieved () { return this.secondCommonGoalAchieved;}

    /**
     * Getter method
     * @return  the {@code String} representing the name of the player
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter method
     * @return the personal {@link Player#bookshelf} of the player
     */
    public Bookshelf getBookshelf(){
        return this.bookshelf;
    }

    /**
     * Getter method that gets the {@link ScoringToken}s gathered by the player during the player
     * @return the {@link ScoringToken} of the player
     */
    public List<ScoringToken> getTokens () { return this.tokens;}

    public void setFirstToFinish() {
        this.firstToFinish = true;
    }

    public boolean isFirstToFinish(){
        return firstToFinish;
    }
}
