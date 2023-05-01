package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.*;

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
        // subscribers.stream().filter(x -> x.getStatus() != PlayerStatus.INACTIVE).toList().get(0);
    }
    /**
     * This method returns the next Player
     */
    public Player getNextPlayer(){
        return cursor < subscribers.size()-1 ? subscribers.get(cursor+1) : subscribers.get(0);
        // cursor < subscribers.size()-1 ? subscribers.get(cursor+1) : subscribers.get(0);
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

    /**
     * This method returns the LivingRoomBoard of the game
     */
    public LivingRoomBoard getLivingRoomBoard() {
        return livingRoomBoard;
    }

    public void nextPlayer(){
        this.cursor = cursor < subscribers.size()-1 ? cursor+1 : 0;
    }
    public static void main(String[] args){
        PersonalGoalCardDeck personalGoalCardDeck = new PersonalGoalCardDeck();

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many players are going to play? ");
        int numberOfPlayers = scanner.nextInt();
        Game game = new Game(Arrays.stream(NumberOfPlayers.values()).filter(x -> x.getValue() == numberOfPlayers).toList().get(0));
        for (int i = 0; i<numberOfPlayers; i++){
            System.out.print("Enter your name: ");
            String name = scanner.next();
            game.subscribe(new Player(name, personalGoalCardDeck));
        }
        System.out.println("Game " + game.getId());
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


}
