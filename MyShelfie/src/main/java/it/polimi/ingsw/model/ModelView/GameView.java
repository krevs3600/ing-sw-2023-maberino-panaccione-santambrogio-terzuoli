package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.utils.NumberOfPlayers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameView implements Serializable {
    private final String id;
    private final PersonalGoalCardDeckView personalGoalCardDeckView;
    private final List<PlayerView> subscribers;
    private final LivingRoomBoardView livingRoomBoardView;
    private final NumberOfPlayers numberOfPlayers;
    private final int cursor;
    private final TilePackView tilePackView;
    private final int currentPlayerScore;
    private final PlayerView currentPlayer;

    private static final long serialVersionUID = 1L;

    public GameView (Game game) {

        this.cursor = game.getCursor();
        this.id = game.getGameName();
        this.numberOfPlayers = game.getNumberOfPlayers();
        subscribers = new ArrayList<>();
        for(Player player : game.getSubscribers()){
            this.subscribers.add(new PlayerView(player));
        }
        this.tilePackView = new TilePackView(game.getTilePack());
        this.currentPlayerScore = game.getCurrentPlayerScore();
        this.livingRoomBoardView = new LivingRoomBoardView(game.getLivingRoomBoard());
        this.personalGoalCardDeckView = new PersonalGoalCardDeckView(game.getPersonalGoalCardDeck());
        this.currentPlayer = new PlayerView(game.getCurrentPlayer());
    }

    public String getId () {
        return this.id;
    }

    public PersonalGoalCardDeckView getPersonalGoalCardDeck () {
        return this.personalGoalCardDeckView;
    }

    public List<PlayerView> getSubscribers () {

        return this.subscribers;
    }

    public LivingRoomBoardView getLivingRoomBoard () {
        return this.livingRoomBoardView;
    }

    public NumberOfPlayers getNumberOfPlayers () {
        return this.numberOfPlayers;
    }

    public int getCursor () {
        return this.cursor;
    }

    public TilePackView getTilePack () { return this.tilePackView;}

    public int getCurrentPlayerScore () { return this.currentPlayerScore;}
    public PlayerView getCurrentPlayer(){
        return this.currentPlayer;
    }
    public PlayerView getPlayer(String nickname){
        return this.getSubscribers().stream().filter(x->x.getName().equals(nickname)).toList().get(0);
    }


    /**public void update(Observable o, Object arg) {
        if (!(o instanceof Game model)){
            System.err.println("Discarding update from " + o);
        }

        if (arg instanceof String subscriber){
            setChanged();
            notifyObservers(subscriber);
        }

        if (arg instanceof Game game){
            setChanged();
            notifyObservers(new GameView(game));
        }

    }
     */
    public String toCLI(String nickname) {
        String code = "\033[H\033[2J"; // out.flush
        String game = "";
        String DIVIDER = "      ";
        String UP_MARGIN = "\n\n\n";
        String MARGIN = "\n\n";
        String HEADER_PERSONAL_CARD = "YOUR PERSONAL GOAL CARD:";

        HashMap<Integer, String> personalCard =  this.getSubscribers().get(0).getPersonalGoalCard().toDict();
        HashMap<Integer, String> gameBoard = this.getLivingRoomBoard().toDict();
        HashMap<Integer, String> bookshelf = this.getSubscribers().get(0).getBookshelf().toDict();
        int personal = 0;
        int board = 0;
        int book = 0;
        game = game.concat(UP_MARGIN);
        for (int i=0; i<20; i++){
            if (i==0){
                game = game.concat("YOUR PERSONAL GOAL CARD:").concat(DIVIDER);
                game = game.concat(gameBoard.get(board)).concat("\n");
                board += 1;
            } else if (i<8){
                game = game.concat(personalCard.get(personal)).concat(DIVIDER);
                personal+=1;
                game = game.concat(gameBoard.get(board).concat(DIVIDER)).concat("\n");
                board+=1;
            } else if (i<13) {
                game = game.concat(getStringWithSpaces("", HEADER_PERSONAL_CARD.length())).concat(DIVIDER);
                game = game.concat(gameBoard.get(board)).concat("\n");
                board+=1;
            } else {
                game = game.concat(bookshelf.get(book)).concat(DIVIDER);
                book+=1;
                game = game.concat(gameBoard.get(board).concat(DIVIDER)).concat("\n");
                board+=1;
            }
        }
        return game;
    }
    private static String getStringWithSpaces(String text, int rowLength) {
        int spaces = rowLength - text.length();
        String result = "";
        result = result.concat(text);
        for(int i=0; i<spaces; i++){
            result = result.concat(" ");
        }
        return result;
    }

    public static void main(String args[]){
        Game game = new Game(NumberOfPlayers.TWO_PLAYERS, "Zo");
        game.subscribe(new Player("Carlo"));
        game.subscribe(new Player("Fra"));
        game.initLivingRoomBoard();
        PersonalGoalCardDeck deck = new PersonalGoalCardDeck();
        game.getSubscribers().get(0).setPersonalGoalCard(deck.draw());
        game.getSubscribers().get(1).setPersonalGoalCard(deck.draw());
        GameView view = new GameView(game);
        System.out.println(view.toCLI("Carlo"));
    }

}
