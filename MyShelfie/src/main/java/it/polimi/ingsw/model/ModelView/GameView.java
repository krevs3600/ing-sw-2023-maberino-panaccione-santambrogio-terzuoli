package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.view.cli.ColorCLI;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class GameView implements Serializable {
    private final String id;
    private final PersonalGoalCardDeckView personalGoalCardDeckView;
    private final List<PlayerView> subscribers;

    private final List<PlayerView> subscribersWithoutCurrent=new ArrayList<>();
    private final LivingRoomBoardView livingRoomBoardView;
    private final NumberOfPlayers numberOfPlayers;
    private final int cursor;
    private final TilePackView tilePackView;
    private final PlayerView currentPlayer;

    @Serial
    private static final long serialVersionUID = 1L;
    private final GamePhase turnPhase;

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

    public List<PlayerView> otherPlayersList(PlayerView player) {
        for (PlayerView otherPlayer : subscribers) {
            if(!otherPlayer.getName().equals(player.getName())){
                subscribersWithoutCurrent.add(otherPlayer);
            }

        }
        return  subscribersWithoutCurrent;
    }

    public int getCursor () {
        return this.cursor;
    }

    public TilePackView getTilePack () { return this.tilePackView;}
    public PlayerView getCurrentPlayer(){
        return this.currentPlayer;
    }
    public PlayerView getPlayer(String nickname){
        return this.getSubscribers().stream().filter(x->x.getName().equals(nickname)).toList().get(0);
    }
    public GamePhase getTurnPhase(){
        return this.turnPhase;
    }
    public List<CommonGoalCardView> getCommonGoalCards(){
        return this.getLivingRoomBoard().getCommonGoalCards();
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

    private int getNextPlayerIndex(String nickname) {
        int index = getPersonalGameIndex(nickname);
        if (index != -1) {
            return (index + 1 < numberOfPlayers.getValue()) ? index + 1 : 0;
        }
        return -1;
    }

    private List<Integer> orderedIndexes(String nickname){
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




    public String toCLI(String nickname) {
        // todo optimization can be made
        List<Integer> indexes = orderedIndexes(nickname);

        String game = "";
        String DIVIDER = "      ";
        String UP_MARGIN = "\n\n";
        String HEADER_PERSONAL_CARD = "YOUR PERSONAL GOAL CARD:";

        HashMap<Integer, String> personalCard =  this.getSubscribers().get(0).getPersonalGoalCard().toDict();
        HashMap<Integer, String> gameBoard = this.getLivingRoomBoard().toDict();
        HashMap<Integer, String> bookshelf = this.getSubscribers().get(getPersonalGameIndex(nickname)).getBookshelf().toDict();

        game = game.concat(UP_MARGIN);
        Stack stack = getCommonGoalCards().get(0).getStack();
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

    private static String getStringWithSpaces(int rowLength) {
        int spaces = rowLength - "".length();
        String result = "";
        result = result.concat("");
        for(int i=0; i<spaces; i++){
            result = result.concat(" ");
        }
        return result;
    }

    public static void main(String[] args){
        Game game = new Game(NumberOfPlayers.FOUR_PLAYERS, "Zo");
        game.subscribe(new Player("Carlo"));
        game.subscribe(new Player("Fra"));
        game.subscribe(new Player("Pi"));
        game.subscribe(new Player("Mabe"));
        game.initLivingRoomBoard();
        PersonalGoalCardDeck deck = new PersonalGoalCardDeck();
        game.getSubscribers().get(0).setPersonalGoalCard(deck.draw());
        game.getSubscribers().get(1).setPersonalGoalCard(deck.draw());
        GameView view = new GameView(game);
        // clear terminal screen, in a real terminal it should be working
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(view.toCLI("Carlo"));
    }
}
