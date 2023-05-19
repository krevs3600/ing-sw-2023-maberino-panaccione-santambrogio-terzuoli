package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.ModelView.TilePackView;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.TileType;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.observer_observable.Observable;

import java.util.*;

/**
 * <h1>Class Game</h1>
 * The class Game initiates LivingRoomBoard and keeps track of the players
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 4/30/2023
 */
public class Game extends Observable<EventMessage> {

    public enum Phase {
        INIT_GAME,
        INIT_TURN,
        PICKING_TILES,
        PLACING_TILES,
        END_TURN,
        END_GAME;
    }
    private String gameName;
    private PersonalGoalCardDeck personalGoalCardDeck;
    private List<Player> subscribers = new ArrayList<>();
     private LivingRoomBoard livingRoomBoard;
     private NumberOfPlayers numberOfPlayers;
     private int cursor;
     private Phase turnPhase;
     private List<Space> drawableTiles = new ArrayList<>();

     private TilePack tilePack;

     private int columnChoice;

     private int currentPlayerScore;

     private List<Position> buffer;
     private boolean isFinalTurn;

     private boolean alongSideRow;
     private boolean alongSideColumn;

    public Game(NumberOfPlayers numberOfPlayers, String gameName){
        //initialize id with random string, This should be quiet random...
        this.gameName = gameName;
        //this.livingRoomBoard = new LivingRoomBoard(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
        this.personalGoalCardDeck = new PersonalGoalCardDeck();
        this.tilePack = new TilePack();
        //this.turnPhase = Phase.INIT_TURN;
        this.buffer = new ArrayList<>();
        this.isFinalTurn = false;
        this.alongSideRow = false;
        this.alongSideColumn = false;
    }

    public void initLivingRoomBoard(){
        this.livingRoomBoard = new LivingRoomBoard(numberOfPlayers);
        //LivingRoomBoardView livingRoomBoardView = new LivingRoomBoardView(this.livingRoomBoard);
        //TODO: da cambiare i messaggi di inizializzazione
        //setChanged();
        //notifyObservers(new BoardMessage("tutti", livingRoomBoardView));
    }

    public void setGameName (String gameName) {
        this.gameName = gameName;
    }

    public boolean isFinalTurn () { return isFinalTurn;}
    public void setFinalTurn() {isFinalTurn = true;}

    public ItemTile drawTile(Position position) throws IllegalArgumentException{
        ItemTile itemTile = getLivingRoomBoard().getSpace(position).drawTile();
        setChanged();
        notifyObservers(new TilePositionMessage(getCurrentPlayer().getName(), (position)));
        return itemTile;
    }

    public void insertTileInTilePack (ItemTile itemTile) {

        this.getTilePack().insertTile(itemTile);
        TilePackView tilePackView = new TilePackView(this.getTilePack());
        setChanged();
        notifyObservers(new TilePackMessage(getCurrentPlayer().getName(), (tilePackView)));
    }

    /**
     * This method adds a Player to the game
     * @param player the player to be subscribed
     */
    public void subscribe(Player player){
        subscribers.add(player);
        /*setChanged();
        notifyObservers(new NumOfPlayersRequestMessage(player.getName()));
         */
    }

    public void setDrawableTiles(){
        this.drawableTiles = getLivingRoomBoard().getDrawableTiles();
        LivingRoomBoardView livingRoomBoardView = new LivingRoomBoardView(this.getLivingRoomBoard());
        setChanged();
        notifyObservers(new BoardMessage(getCurrentPlayer().getName(), livingRoomBoardView));
    }
    public List<Space> getDrawableTiles(){
        return this.drawableTiles;
    }
    /**
     * This method chooses the order of play of the game and sets to PICKING_TILES the status of the first player
     */
    //TODO: create new eventMessage type
    public void startGame(){
        Collections.shuffle(subscribers);
        Player firstPlayer = subscribers.get(0);
        firstPlayer.setStatus(PlayerStatus.PICKING_TILES);
        this.cursor = 0;
        setChanged();
        notifyObservers(new PlayerTurnMessage(firstPlayer.getName()));
    }
    /**
     * This method stops the game by setting to INACTIVE the status of all the players
     */
    public void endGame(){
        for(Player player : subscribers){
            player.setStatus(PlayerStatus.INACTIVE);
        }
        // maybe call to a method to show results
    }

    /**
     * This method gets the current Player
     * @return Player the player who is currently playing
     */
    //TODO: create new event message to switch player turn
    public Player getCurrentPlayer(){
        return subscribers.get(cursor);
    }

    /**
     * This method switches to the next player to have his turn
     */
    //TODO: add message event
    public void changeTurn(){
        getBuffer().clear();
        setDrawableTiles();
        this.cursor = cursor < subscribers.size()-1 ? cursor+1 : 0;
        setChanged();
        notifyObservers(new PlayerTurnMessage(getCurrentPlayer().getName()));
    }



    /**
     * This method refills the LivingRoomBoard by calling its proper method
     */
    public void refillLivingRoomBoard(){
        getLivingRoomBoard().refill();
        setChanged();
        LivingRoomBoardView livingRoomBoardView = new LivingRoomBoardView(getLivingRoomBoard());
        notifyObservers(new BoardMessage(getCurrentPlayer().getName(), livingRoomBoardView));
    }

    /**
     * This getter method gets the player's id
     * @return String It returns the string representing the identification of the player
     */
    public String getGameName(){
        return this.gameName;
    }

    /**
     * This getter method gets the number of players in game
     * @return NumberOfPlayers It returns the enumeration value representing the number of players
     */
    public NumberOfPlayers getNumberOfPlayers(){
        return this.numberOfPlayers;
    }

    /**
     * This method gets the common goal cards in play
     * @return List<CommonGoalCard> It returns the list of the two common goal cards in play
     */
    // don't remember why private
    private List<CommonGoalCard> getCommonGoalCards(){
        return livingRoomBoard.getCommonGoalCards();
    }

    /**
     * This method takes the ScoringToken from the commonGoalCard and gives it to the correct Player
     * @param commonGoalCard the card of the achieved common goal
     * @param player the scoring player
     */
    public void pullScoringTokens(CommonGoalCard commonGoalCard, Player player){
        ScoringToken scoringToken = commonGoalCard.pop();
        player.winToken(scoringToken);
    }

    /**
     * This getter method gets the players subscribed to the game
     * @return List<Player> It returns the list of players subscribed
     */
    public List<Player> getSubscribers(){
        return subscribers;
    }

    /**
     * This getter method gets the central board of the game
     * @return LivingRoomBoard It returns the living room board
     */
    public LivingRoomBoard getLivingRoomBoard() {
        return livingRoomBoard;
    }

    public PersonalGoalCardDeck getPersonalGoalCardDeck(){ return personalGoalCardDeck;}

    public int getCursor () {return cursor;}

    /*
    public static void main(String[] args){
        PersonalGoalCardDeck personalGoalCardDeck = new PersonalGoalCardDeck();

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many players are going to play? ");
        int numberOfPlayers = scanner.nextInt();
        Game game = new Game(NumberOfPlayers.TWO_PLAYERS, "game");
        for (int i = 0; i<numberOfPlayers; i++){
            System.out.print("Enter your name: ");
            String name = scanner.next();
            game.subscribe(new Player(name));
        }
        System.out.println("Game " + game.getGameName());
        TilePack tilePack = new TilePack();
        game.startGame();
        LivingRoomBoard livingRoomBoard = game.getLivingRoomBoard();

        while(true){
            Player currentPlayer = game.getCurrentPlayer();
            Player nextPlayer = game.getNextPlayer();
            List<Space> drawableTiles = livingRoomBoard.getDrawableTiles();
            System.out.println(livingRoomBoard);
            System.out.println(drawableTiles);
            System.out.print(currentPlayer.getName() + " how many tiles would you like to get? ");

            int numberOfTiles = scanner.nextInt();
            for (int i=0; i<numberOfTiles; i++) {
                while (tilePack.getSize() < numberOfTiles) {
                    System.out.print("x: ");
                    int x = scanner.nextInt();
                    System.out.print("y: ");
                    int y = scanner.nextInt();

                    if (drawableTiles.contains(livingRoomBoard.getSpace(new Position(x, y)))) {
                        tilePack.insertTile(currentPlayer.pickUpTile(livingRoomBoard, new Position(x, y)));
                        System.out.println(tilePack);
                        System.out.println(livingRoomBoard);
                    } else {
                        System.out.println("You can't take this tile, try again");
                    }
                }
            }
            currentPlayer.setStatus(PlayerStatus.INSERTING_TILES);
            // while(tilePack.getSize() > 0){
            System.out.println(currentPlayer.getBookshelf());
            System.out.println(tilePack);
            System.out.print("In which column do you want to put your tiles? ");
            int column = scanner.nextInt();
            currentPlayer.insertTile(tilePack, column);
            System.out.println(currentPlayer.getBookshelf());
            //}
            currentPlayer.setStatus(PlayerStatus.INACTIVE);
            nextPlayer.setStatus(PlayerStatus.PICKING_TILES);
            game.nextPlayer();

        }


    }
    */
    
    public Phase getTurnPhase() {return this.turnPhase;};
    public void setTurnPhase(Phase turnPhase) {this.turnPhase = turnPhase;}

    public TilePack getTilePack () { return tilePack;}

    //TODO: change getSubscribers into getCurrentPlayer
    public void setColumnChoice (int columnChoice) throws IndexOutOfBoundsException {
        if (columnChoice >= 0 && columnChoice < getSubscribers().get(0).getBookshelf().getMaxWidth()) this.columnChoice = columnChoice;
        else throw new IndexOutOfBoundsException("invalid column, please choose another one;");
        setChanged();
        // BookshelfView bookshelfView = new BookshelfView(getSubscribers().get(0).getBookshelf());
        notifyObservers(new InsertRequestMessage(getSubscribers().get(0).getName(), columnChoice));
    }

    public int getColumnChoice() {
        return columnChoice;
    }

   //TODO
    /**public void setCurrentPlayerScore (int score) {
        this.currentPlayerScore += score;
        setChanged();
        notifyObservers(this);
    }
     */
    public int getCurrentPlayerScore () {return this.currentPlayerScore;}

    public List<Position> getBuffer(){
        return this.buffer;
    }

    public boolean isAlongSideRow () {return this.alongSideRow; }
    public void setAlongSideRow (boolean alongSideRow) {this.alongSideRow = alongSideRow; }

    public boolean isAlongSideColumn () {return this.alongSideColumn; }
    public void setAlongSideColumn (boolean alongSideColumn) {this.alongSideColumn = alongSideColumn; }
}
