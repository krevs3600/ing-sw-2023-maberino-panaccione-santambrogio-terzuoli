package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
/**
 * <h1>Class GameView</h1>
 * This class is the immutable version of class Game
 *
 * @author Francesca Santambrogio, Carlo Terzuoli
 * @version 1.0
 * @since 5/06/2023
 */
public class GameView implements Serializable {
    /**
     * Game's unique id
     */
    private final String id;
    /**
     * Immutable Deck of PersonalGoalCards
     */
    private final PersonalGoalCardDeckView personalGoalCardDeckView;
    /**
     * List of Immutable Player's in the game
     */
    private final List<PlayerView> subscribers;
    /**
     * List of Immutable Player's in the game except for the Player in status ACTIVE
     */
    private final List<PlayerView> subscribersWithoutCurrent = new ArrayList<>();
    /**
     * Immutable LivingRoomBoard
     */
    private final LivingRoomBoardView livingRoomBoardView;
    /**
     * Number of players in the game
     */
    private final NumberOfPlayers numberOfPlayers;
    /**
     * Int indexing the current player in the subscribers' list
     */
    private final int cursor;
    /**
     * Immutable TilePack
     */
    private final TilePackView tilePackView;
    /**
     * Immutable Player in status ACTIVE
     */
    private final PlayerView currentPlayer;
    /**
     * UID version
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Game's turn phase
     */
    private final GamePhase turnPhase;

    /**
     * Constructor for class GameView
     * @param game Game object to create immutable version
     */
    public GameView (Game game) {
        this.turnPhase = game.getTurnPhase();
        this.cursor = game.getCursor();
        this.id = game.getGameName();
        this.numberOfPlayers = game.getNumberOfPlayers();
        subscribers = new ArrayList<>();
        for(Player player : game.getSubscribers()){
            this.subscribers.add(new PlayerView(player));
        }
        this.tilePackView = new TilePackView(game.getTilePack());
        this.livingRoomBoardView = new LivingRoomBoardView(game.getLivingRoomBoard());
        this.personalGoalCardDeckView = new PersonalGoalCardDeckView(game.getPersonalGoalCardDeck());
        this.currentPlayer = new PlayerView(game.getCurrentPlayer());
    }

    /**
     * Getter method for game's id
     * @return the game's id
     */
    public String getId () {
        return this.id;
    }

    /**
     * Getter method for the PersonalGoalCardDeck
     * @return the PersonalGoalCardDeck
     */
    public PersonalGoalCardDeckView getPersonalGoalCardDeck () {
        return this.personalGoalCardDeckView;
    }
    /**
     * Getter method for the list of subscribers
     * @return the list of subscribers
     */
    public List<PlayerView> getSubscribers () {
        return this.subscribers;
    }

    /**
     * Getter method for the list of subscribers
     * @return the list of subscribers
     */
    public LivingRoomBoardView getLivingRoomBoard () {
        return this.livingRoomBoardView;
    }

    /**
     * Getter method for the number of players
     * @return the number of players
     */
    public NumberOfPlayers getNumberOfPlayers () {
        return this.numberOfPlayers;
    }

    /**
     * Getter method for the players' list except the one in status ACTIVE
     * @return the players' list except the one in status ACTIVE
     */
    public List<PlayerView> otherPlayersList(PlayerView player) {
        for (PlayerView otherPlayer : subscribers) {
            if(!otherPlayer.getName().equals(player.getName())){
                subscribersWithoutCurrent.add(otherPlayer);
            }
        }
        return  subscribersWithoutCurrent;
    }

    /**
     * Getter method for the cursor
     * @return the cursor
     */
    public int getCursor () {
        return this.cursor;
    }

    /**
     * Getter method for the immutable TilePack
     * @return the immutable TilePack
     */
    public TilePackView getTilePack () {
        return this.tilePackView;
    }

    /**
     * Getter method for the player in status ACTIVE
     * @return the player in status ACTIVE
     */
    public PlayerView getCurrentPlayer(){
        return this.currentPlayer;
    }

    /**
     * Given a nickname it returns the player in the game
     * @param nickname of the player to be retrieved
     * @return the PlayerView associated to the given nickname
     */
    public PlayerView getPlayer(String nickname){
        for (PlayerView player : getSubscribers()){
            if (player.getName().equals(nickname)){
                return player;
            }
        }
        return null;
    }

    /**
     * Getter method for the game's turn phase
     * @return the game's turn phase
     */
    public GamePhase getTurnPhase(){
        return this.turnPhase;
    }

    /**
     * Getter method for the CommonGoalCards drawn for the game
     * @return the game's CommonGoalCards
     */
    public List<CommonGoalCardView> getCommonGoalCards(){
        return this.getLivingRoomBoard().getCommonGoalCards();
    }

    /**
     * Method to calculate the index of the player wrt the list of subscribers of the game
     * @param nickname name of the player
     * @return int the index of the player otherwise -1
     */
    public int getPersonalGameIndex(String nickname) {
        int personalGameIndex;
        for(int i=0; i<this.getSubscribers().size(); i++){
            if(this.getSubscribers().get(i).getName().equals(nickname)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Method to calculate the index of the next player wrt the list of subscribers of the game
     * @param nickname name of the player
     * @return int the index of the next player otherwise -1
     */
    private int getNextPlayerIndex(String nickname) {
        int index = getPersonalGameIndex(nickname);
        if (index != -1) {
            return (index + 1 < numberOfPlayers.getValue()) ? index + 1 : 0;
        }
        return -1;
    }

    /**
     * Method to order the subscribers' list in the game placing nickname at index 0
     * @param nickname the nickname to place at index 0
     * @return the reordered list of subscribers
     */
    private List<Integer> orderedIndexes(String nickname){
        System.out.println(nickname.trim());
        System.out.println("inizio");
        for (Character c : nickname.toCharArray()){
            System.out.println(c);
        }
        System.out.println("fine");
        for (PlayerView player : getSubscribers()){
            System.out.println(player.getName());
        }
        List<String> subscriberNames = new ArrayList<>(getSubscribers().stream().map(PlayerView::getName).toList());
        List<Integer> subscribersIndex = new ArrayList<>();

        String name = "";
        int clientIndex;
        for (int i = 0; i < getNumberOfPlayers().getValue(); i++){
            if (i == 0) {
                clientIndex = getPersonalGameIndex(nickname);
                subscribersIndex.add(clientIndex);
                name = subscriberNames.get(getNextPlayerIndex(nickname));
            } else {
                clientIndex = getPersonalGameIndex(name);
                subscribersIndex.add(clientIndex);
                name = subscriberNames.get(getNextPlayerIndex(name));
            }
        }
        return subscribersIndex;
    }


    /**
     * Method to create an horizontal representation of the game, containing all the information
     * of the CommonGoalCards and their ScoringTokens, the Players' bookshelves, the LivingRoomBoard
     * and the TilePack.
     * @param nickname viewing the string representation
     * @return the horizontal string representation of the CLI
     */
    public String toCLI(String nickname) {
        List<Integer> indexes = orderedIndexes(nickname);

        String game = "";
        String DIVIDER = "      ";
        String UP_MARGIN = "\n\n";
        String HEADER_PERSONAL_CARD = "YOUR PERSONAL GOAL CARD:";

        HashMap<Integer, String> personalCard =  this.getSubscribers().get(getPersonalGameIndex(nickname)).getPersonalGoalCard().toDict();
        HashMap<Integer, String> gameBoard = this.getLivingRoomBoard().toDict();
        HashMap<Integer, String> bookshelf = this.getSubscribers().get(getPersonalGameIndex(nickname)).getBookshelf().toDict();

        game = game.concat(UP_MARGIN);
        Stack<ScoringToken> stack = getCommonGoalCards().get(0).getStack();
        game = game.concat(stack.get(stack.size()-1).toString() + " ");
        game = game.concat(getCommonGoalCards().get(0).toString() + "\n\n");
        stack = getCommonGoalCards().get(1).getStack();
        game = game.concat(stack.get(stack.size()-1).toString() + " ");
        game = game.concat(getCommonGoalCards().get(1).toString() + "\n\n");

        int personal = 0;
        int board = 0;
        int book = 0;
        int book_1 = 0;
        int book_2 = 0;
        int book_3 = 0;

        for (int i=0; i<20; i++){
            if (i==0){
                // print board lines
                game = game.concat("YOUR PERSONAL GOAL CARD:").concat(DIVIDER);
                game = game.concat(gameBoard.get(board));
                board += 1;
                // print bookshelf 1 (game has at least two players)
                game = game.concat(DIVIDER);
                game = game.concat(getSubscribers().get(indexes.get(1)).getBookshelf().toDict().get(book_1));
                book_1 += 1;
                // with 4 players
                if (getNumberOfPlayers().getValue() == 4){
                    game = game.concat(DIVIDER);
                    game = game.concat(getSubscribers().get(indexes.get(2)).getBookshelf().toDict().get(book_2)).concat("\n");
                    book_2 += 1;
                } else {
                    game = game.concat("\n");
                }
            } else if (i<8){
                game = game.concat(personalCard.get(personal)).concat(DIVIDER);
                personal+=1;
                game = game.concat(gameBoard.get(board).concat(DIVIDER));
                board+=1;
                // print bookshelf 1 (game has at least two players)
                game = game.concat(DIVIDER);
                game = game.concat(getSubscribers().get(indexes.get(1)).getBookshelf().toDict().get(book_1));
                book_1 += 1;
                // with 4 players
                if (getNumberOfPlayers().getValue() == 4){
                    game = game.concat(DIVIDER);
                    game = game.concat(getSubscribers().get(indexes.get(2)).getBookshelf().toDict().get(book_2) + "\n");
                    book_2 += 1;
                } else {
                    game = game.concat("\n");
                }
            } else if (i<12) {
                game = game.concat(getStringWithSpaces(HEADER_PERSONAL_CARD.length())).concat(DIVIDER);
                game = game.concat(gameBoard.get(board)).concat("\n");
                board+=1;
            } else {
                game = game.concat(bookshelf.get(book)).concat(DIVIDER);
                book+=1;
                game = game.concat(gameBoard.get(board).concat(DIVIDER));
                board+=1;
                if (getNumberOfPlayers().getValue() > 2){
                    if (getNumberOfPlayers().getValue() == 4){
                        game = game.concat(DIVIDER);
                        game = game.concat(getSubscribers().get(indexes.get(3)).getBookshelf().toDict().get(book_3) + "\n");
                        book_3 += 1;
                    } else {
                        game = game.concat(DIVIDER);
                        game = game.concat(getSubscribers().get(indexes.get(2)).getBookshelf().toDict().get(book_2) + "\n");
                        book_2 += 1;
                    }

                } else {
                    game = game.concat("\n");
                }
            }
        }
        game = game.concat(getTilePack().toDict().get(0) + "\n" + getTilePack().toDict().get(1));

        return game;
    }

    /**
     * Method to generate an empty string
     * @param rowLength wanted length
     * @return an empty string of length rowLength
     */
    private static String getStringWithSpaces(int rowLength) {
        int spaces = rowLength - "".length();
        String result = "";
        result = result.concat("");
        for(int i=0; i<spaces; i++){
            result = result.concat(" ");
        }
        return result;
    }
}
