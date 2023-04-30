package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCardDeck;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.NumberOfPlayers;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Class Game</h1>
 * The class Game initiate LivingRoomBoard and keeps track of the players
 *
 * @version 1.0
 * @since 4/30/2023
 */
public class Game {
     private final String id;
     private List<Player> subscribers = new ArrayList<Player>();
     private LivingRoomBoard livingRoomBoard;
     private NumberOfPlayers numberOfPlayers;

    /**
     * Class constructor
     * @param numberOfPlayers enum
     */
    public Game(NumberOfPlayers numberOfPlayers){
        //initialize id with random string, This should be quiet random...
        this.id = String.valueOf(String.format("%d", System.currentTimeMillis()).hashCode());
        this.livingRoomBoard = new LivingRoomBoard(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * This method add a Player to the game
     * @param player the player to be subscribed
     */
    public void subscribe(Player player){
        subscribers.add(player);
    }

    /**
     * This method choose the order of play of the game and set to PICKING_TILES the first player
     */
    public void startGame(){

    }
    /**
     * This method stop the game.
     */
    public void endGame(){

    }

    /**
     * This method returns the current Player
     */
    public Player getCurrentPlayer(){
        return null;
    }
    /**
     * This method returns the next Player
     */
    public Player getNextPlayer(){
        return null;
    }

    /**
     * This method refills the LivingRoomBoard
     */
    public void refillLivingRoomBoard(){
        livingRoomBoard.refill();
    }

    /**
     * This method returns the game id
     */
    public String getGameId(){
        return this.id;
    }

    /**
     * This method returns the number of Players in the game
     */
    public NumberOfPlayers numberOfPlayers(){
        return this.numberOfPlayers;
    }

    /**
     * This method returns the list of chosen CommonGoalCards
     */
    // don't remember why private
    private List<CommonGoalCard> getCommonGoalCards(){
        return livingRoomBoard.getCommonGoalCards();
    }
    /**
     * This method take the ScoringToken from the commonGoalCard and gives it to the correct Player
     * @param commonGoalCard the achieved commonGoalCard
     * @param player the scoring player
     */
    public void pullScoringTokens(CommonGoalCard commonGoalCard, Player player){

    }
    /**
     * This method assigns a PersonalGoalCard to each Player
     */
    public void dealPersonalGoalCards(){

    }

    /**
     * This method returns the list of Players in the game
     */
    public List<Player> getSubscribers(){
        return subscribers;
    }

}
