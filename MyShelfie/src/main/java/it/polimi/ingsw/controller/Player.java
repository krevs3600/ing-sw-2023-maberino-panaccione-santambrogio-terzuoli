package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.stream.Collectors;

public class Player {
    private final String name;
    private PlayerStatus status;
    //private final Player nextPlayer;
    //private final boolean firstPlayer;

    private Bookshelf myShelfie;
    private int score = 0;

    public Player(String name, Bookshelf myShelfie){
        this.name = name;
        this.myShelfie = myShelfie;
        this.score = score;
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

    public void insertTile(TilePack tp,int column) throws IndexOutOfBoundsException{
        try{
            myShelfie.insertTile(tp, column);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Error, please select a valid column");
        }

    }

    public void pickUpTile(ItemTile tile){

    }

    public ItemTile pickUpTilePROVA(LivingRoomBoard board, Position pos) throws IllegalArgumentException{
        if(board.getDrawableTiles().stream().map(x -> x.getPosition()).collect(Collectors.toList()).contains(pos)){
            board.getSpace(pos).drawTile();
        } else {
            throw new IllegalArgumentException();
        }
        return null;
    }

    public int computeScore(){
        return 0;
    }

    //public boolean isFirstPlayer() {
    //    return firstPlayer;
    //}

    public FirstPlayerSeat getFirstPlayerSeat(){
        return null;
    }




}
