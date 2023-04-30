package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonGoalCard;
import it.polimi.ingsw.model.CommonGoalCardDeck;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.NumberOfPlayers;

import java.util.ArrayList;
import java.util.List;

public class Game {
     private final String id;
     private CommonGoalCardDeck commonGoalCardDeck;
     private List<Player> subscribers = new ArrayList<Player>();

     private LivingRoomBoard livingRoomBoard;

    public Game(NumberOfPlayers numberOfPlayers){
        //initialize id with random string, This should be quiet random...
        this.id = String.valueOf(String.format("%d", System.currentTimeMillis()).hashCode());
        this.livingRoomBoard = new LivingRoomBoard(numberOfPlayers);

    }



    public void subscribe(Player player){

    }

    public void startGame(){

    }

    public void endGame(){

    }

    public Player getCurrentPlayer(){
        return null;
    }

    public Player getNextPlayer(){
        return null;
    }

    public void refillLivingRoomBoard(){

    }


    public String getGameId(){
        return this.id;
    }

    public int numberOfPlayers(){
        return 0;
    }

    private void drawCommonGoalCards(){

    }

    private List<CommonGoalCard> getCommonGoalCards(){
        return null;
    }

    public void pushScoringTokens(){

    }

    public void dealPrivateGoalCards(){

    }

    public List<Player> getSubscribers(){
        return null;
    }

}
