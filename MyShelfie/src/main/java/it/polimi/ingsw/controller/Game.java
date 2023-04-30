package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonGoalCard;
import it.polimi.ingsw.model.LivingRoomBoard;

import java.util.ArrayList;
import java.util.List;

public class Game {
     private final String id;
     //private static GoalCardsDesk goalCardsDesk;
     private List<Player> subscribers = new ArrayList<Player>();

     private LivingRoomBoard livingRoomBoard;

    public Game(){
        //initialize id with random string
        id = "0";
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
