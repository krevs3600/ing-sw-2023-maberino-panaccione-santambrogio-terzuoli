package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.Collections;
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
     private List<Player> subscribers = new ArrayList<>();
     private final LivingRoomBoard livingRoomBoard;
     private final NumberOfPlayers numberOfPlayers;
     private int cursor;

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
        Collections.shuffle(subscribers);
        Player firstPlayer = subscribers.get(0);
        firstPlayer.setStatus(PlayerStatus.PICKING_TILES);
        this.cursor = 0;
    }
    /**
     * This method stop the game.
     */
    public void endGame(){
        for(Player player : subscribers){
            player.setStatus(PlayerStatus.INACTIVE);
        }
        // maybe call to a method to show results
    }

    /**
     * This method returns the current Player
     */
    public Player getCurrentPlayer(){
        return subscribers.get(cursor);
        //subscribers.stream().filter(x -> x.getStatus() != PlayerStatus.INACTIVE).collect(Collectors.toList()).get(0);
    }
    /**
     * This method returns the next Player
     */
    public Player getNextPlayer(){
        return cursor < subscribers.size()-1 ? subscribers.get(cursor+1) : subscribers.get(0);
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
    public String getId(){
        return this.id;
    }

    /**
     * This method returns the number of Players in the game
     */
    public NumberOfPlayers getNumberOfPlayers(){
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
        ScoringToken scoringToken = commonGoalCard.pop();
        player.winToken(scoringToken);
    }

    /**
     * This method returns the list of Players in the game
     */
    public List<Player> getSubscribers(){
        return subscribers;
    }

}
