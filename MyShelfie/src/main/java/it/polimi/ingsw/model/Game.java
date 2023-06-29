package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.ModelView.PlayerView;
import it.polimi.ingsw.model.ModelView.TilePackView;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.observer_observable.Observable;
import it.polimi.ingsw.observer_observable.Observer;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * <h1>Class Game</h1>
 * The class Game initiates LivingRoomBoard and keeps track of the players
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 4/30/2023
 */
public class Game extends Observable<EventMessage>  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String gameName;
    private final PersonalGoalCardDeck personalGoalCardDeck;
    private final List<Player> subscribers = new ArrayList<>();
    private LivingRoomBoard livingRoomBoard;
    private final NumberOfPlayers numberOfPlayers;
    private int cursor;


    private Player firstPlayer;
    private GamePhase turnPhase;
    private List<Space> drawableTiles = new ArrayList<>();

    private final TilePack tilePack;

    private int columnChoice;

    private final List<Position> buffer;
    private boolean isFinalTurn;
    private boolean isEnded = false;

    private boolean alongSideRow;
    private boolean alongSideColumn;

    private boolean firstPlayerHasEnded;

    /**
     * Class constructor
     * @param numberOfPlayers the {@link Game#numberOfPlayers} of the game.
     * @param gameName the {@link Game#gameName} which identifies the game
     */
    public Game(NumberOfPlayers numberOfPlayers, String gameName){
        //initialize id with random string, This should be quiet random...
        this.gameName = gameName;
        this.numberOfPlayers = numberOfPlayers;
        this.personalGoalCardDeck = new PersonalGoalCardDeck();
        this.tilePack = new TilePack();
        this.buffer = new ArrayList<>();
        this.isFinalTurn = false;
        this.alongSideRow = false;
        this.alongSideColumn = false;
        this.firstPlayerHasEnded = false;
    }

    /**
     * This method initializes a new {@link LivingRoomBoard} for the game setting the {@link Game#livingRoomBoard} attribute
     */
    public void initLivingRoomBoard(){
        this.livingRoomBoard = new LivingRoomBoard(numberOfPlayers);
    }

    /**
     * This method adds a {@link Player} to the game
     * @param player the {@link Player} to be subscribed
     */
    public void subscribe(Player player){
        subscribers.add(player);
    }

    /**
     * This method chooses the order of play of the game and sets to {@code PICKING_TILES} the status of the {@link Game#firstPlayer}
     * and notifies the {@link Observer}s about the starting of the game
     * by sending a {@link PlayerTurnMessage}
     */
    public void startGame(){
        Collections.shuffle(subscribers);
        setFirstPlayer(subscribers.get(0));
        for (Player player: getSubscribers()) {
            player.setStatus(PlayerStatus.ACTIVE);
        }
        getFirstPlayer().setStatus(PlayerStatus.PICKING_TILES);
        this.cursor = 0;
        setTurnPhase(GamePhase.INIT_GAME);
        setChanged();
        notifyObservers(new PlayerTurnMessage(firstPlayer.getName()));
    }


    /**
     * This method sets the {@link Game#firstPlayer} with a given one
     * @param firstPlayer the {@link Player} who is then saved as the first player of the game
     */
    public void setFirstPlayer(Player firstPlayer) {this.firstPlayer=firstPlayer;}

    public void setTurnPhase(GamePhase turnPhase) {
        this.turnPhase = turnPhase;
        if (turnPhase.equals(GamePhase.PLACING_TILES)) {
            setChanged();
            notifyObservers(new PlacingTilesMessage(getCurrentPlayer().getName()));
        }
        else if (turnPhase.equals(GamePhase.INIT_GAME)) {
            setChanged();
            notifyObservers(new BoardMessage(getCurrentPlayer().getName(), new LivingRoomBoardView(getLivingRoomBoard())));
        }
        else if (turnPhase.equals(GamePhase.INIT_TURN)){
            getBuffer().clear();
            getTilePack().getTiles().clear();
            setAlongSideColumn(false);
            setAlongSideRow(false);
            setDrawableTiles();
            incrementCursor();
            setChanged();
            notifyObservers(new BoardMessage(getCurrentPlayer().getName(), new LivingRoomBoardView(getLivingRoomBoard())));
            setChanged();
            notifyObservers(new PlayerTurnMessage(getCurrentPlayer().getName()));
        }
        else if (turnPhase.equals(GamePhase.PICKING_TILES)) {
            setChanged();
            notifyObservers(new PickingTilesMessage(getCurrentPlayer().getName()));
        }
        else if (turnPhase.equals(GamePhase.COLUMN_CHOICE)) {
            BookshelfView bookshelfView = new BookshelfView(getCurrentPlayer().getBookshelf(), getCurrentPlayer().getName());
            setChanged();
            notifyObservers(new BookshelfMessage(getCurrentPlayer().getName(), bookshelfView));
            setChanged();
            notifyObservers(new ColumnChoiceMessage(getCurrentPlayer().getName()));
        }
    }

    /**
     * This method sets the attribute {@link Game#drawableTiles}
     * by taking the value of {@link LivingRoomBoard#getDrawableTiles()} of the {@link Game#livingRoomBoard}.
     * It is used to make a snapshot of the drawable tiles of the {@link Game#livingRoomBoard}
     * in a particular phase of the game, usually the beginning of a new turn
     */
    public void setDrawableTiles(){
        this.drawableTiles = getLivingRoomBoard().getDrawableTiles();
    }

    /**
     * This method inserts an {@link ItemTile} in the {@link TilePack}
     * and notifies the {@link Observer}s about the insertion
     * by sending a {@link TilePackMessage}
     * @param itemTile the {@link ItemTile} to insert
     */
    public void insertTileInTilePack (ItemTile itemTile) {
        this.getTilePack().insertTile(itemTile);
        TilePackView tilePackView = new TilePackView(this.getTilePack());
        setChanged();
        notifyObservers(new TilePackMessage(getCurrentPlayer().getName(), tilePackView));
    }

    /**
     * This method sets to a given {@code boolean} the {@link Game#alongSideRow} attribute
     * @param alongSideRow the {@code boolean} set in the attribute
     */
    public void setAlongSideRow(boolean alongSideRow) {this.alongSideRow = alongSideRow; }

    /**
     * This method sets to a given {@code boolean} the {@link Game#alongSideColumn} attribute
     * @param alongSideColumn the {@code boolean} set in the attribute
     */
    public void setAlongSideColumn(boolean alongSideColumn) {this.alongSideColumn = alongSideColumn; }

    /**
     * This method sets to a given {@code int} the {@link Game#columnChoice} attribute
     * representing the column chosen by the {@link Game#getCurrentPlayer()} to insert his {@link ItemTile}s
     * @param columnChoice the {@code int} representing the chosen column
     */
    public void setColumnChoice (int columnChoice) throws IndexOutOfBoundsException {
        if (columnChoice >= 0 && columnChoice < getCurrentPlayer().getBookshelf().getMaxWidth()) {
            this.columnChoice = columnChoice;
        }
        else throw new IndexOutOfBoundsException("invalid column, please choose another one;");
        setChanged();
        notifyObservers(new BookshelfMessage(getCurrentPlayer().getName(), new BookshelfView(getCurrentPlayer().getBookshelf(), getCurrentPlayer().getName())));
    }

    /**
     * This method increments the value of the {@link Game#cursor} in a circular way
     * to indicate the following {@link Player} who has to play his turn
     */
    public void incrementCursor() {
        this.cursor = cursor < subscribers.size()-1 ? cursor+1 : 0;
        while (subscribers.get(cursor).getStatus().equals(PlayerStatus.INACTIVE)) {
            this.cursor = cursor < subscribers.size()-1 ? cursor+1 : 0;
        }
    }

    /**
     * This method pops a {@link ScoringToken} from the {@link CommonGoalCard} specified by the index
     * then notifies the {@link Observer}s about the popping
     * by sending a {@link CommonGoalCardMessage}
     * @param commonGoalCardIndex the index of the {@link CommonGoalCard} from which the {@link ScoringToken} is popped
     * @return and {@code int} representing the value of the popped {@link ScoringToken}
     */
    public int popCommonGoalCardStack(int commonGoalCardIndex){
        CommonGoalCard card = getLivingRoomBoard().getCommonGoalCards().get(commonGoalCardIndex);
        setChanged();
        notifyObservers(new  CommonGoalCardMessage(getCurrentPlayer().getName(), card, commonGoalCardIndex));
        return card.getStack().pop().getValue();
    }


    /**
     * This method takes the ScoringToken from the commonGoalCard and gives it to the correct Player
     * @param commonGoalCard the card of the achieved common goal
     * @param player the scoring player
     */
    // TODO: understand if it is needed
    public void pullScoringTokens(CommonGoalCard commonGoalCard, Player player){
        ScoringToken scoringToken = commonGoalCard.pop();
        player.winToken(scoringToken);
    }

    /**
     * This method sets to {@code True} the {@link Game#firstPlayerHasEnded} attribute
     */
    public void setFirstPlayerHasEnded() {
        this.firstPlayerHasEnded = true;
        this.getCurrentPlayer().setFirstToFinish();
    }

    /**
     * This method sets to {@code True} the attribute {@link Game#isFinalTurn}
     * and notifies the {@link Observer}s about the starting of the last turn
     * by sending a {@link LastTurnMessage}
     */
    public void setFinalTurn() {
        isFinalTurn = true;
        setChanged();
        notifyObservers(new LastTurnMessage(getCurrentPlayer().getName()));
    }

    /**
     * This method stops the game by setting to {@code INACTIVE} the {@link PlayerStatus} of all the {@link Game#subscribers}
     * then notifies the {@link Observer}s about the starting of the game
     * by sending a {@link WinGameMessage}
     * @param winner the {@link Player} who has won the game
     */
    public void endGame(Player winner){
        hasEnded();
        for(Player player : subscribers){
            player.setStatus(PlayerStatus.INACTIVE);
        }
        setChanged();
        notifyObservers(new WinGameMessage(new PlayerView(winner)));
    }

    /**
     * This method sets to {@code True} the attribute {@link Game#isEnded}
     */
    public void hasEnded() { this.isEnded = true;}

    /**
     * This method gets the current {@link Player}
     * @return the {@link Player} who is currently playing, the one indicated by the {@link Game#cursor}
     */
    public Player getCurrentPlayer(){
        return subscribers.get(cursor);
    }

    /**
     * This method gets the last {@link Player}
     * @return the {@link Player} who is the last playing his turn
     */
    public Player getLastPlayer() {
        return subscribers.get(subscribers.size()-1);
    }

    /**
     * Getter method
     * @return the {@link Game#firstPlayer}
     */
    public Player getFirstPlayer () {return this.firstPlayer;}

    /**
     * Getter method
     * @return the {@link Game#drawableTiles}
     */
    public List<Space> getDrawableTiles(){
        return this.drawableTiles;
    }

    /**
     * Getter method
     * @return the {@link Game#gameName}
     */
    public String getGameName(){
        return this.gameName;
    }

    /**
     * Getter method
     * @return the {@link Game#numberOfPlayers}
     */
    public NumberOfPlayers getNumberOfPlayers(){
        return this.numberOfPlayers;
    }

    /**
     * Getter method
     * @return the {@link Game#subscribers}
     */
    public List<Player> getSubscribers(){
        return subscribers;
    }


    /**
     * Getter method
     * @return the {@link Game#livingRoomBoard}
     */
    public LivingRoomBoard getLivingRoomBoard() {
        return livingRoomBoard;
    }

    /**
     * Getter method
     * @return the {@link Game#personalGoalCardDeck}
     */
    public PersonalGoalCardDeck getPersonalGoalCardDeck(){
        return personalGoalCardDeck;}

    /**
     * Getter method
     * @return the {@link Game#cursor}
     */
    public int getCursor () {return cursor;}

    /**
     * Getter method
     * @return the {@link Game#turnPhase}
     */
    public GamePhase getTurnPhase() {return this.turnPhase;}

    /**
     * Getter method
     * @return the {@link Game#tilePack}
     */
    public TilePack getTilePack () { return tilePack;}

    /**
     * Getter method
     * @return the {@link Game#columnChoice}
     */
    public int getColumnChoice() {
        return columnChoice;
    }

    /**
     * Getter method
     * @return the {@link Game#buffer} storing the {@link Position}s chosen so far by the current {@link Player}
     */
    public List<Position> getBuffer(){
        return this.buffer;
    }

    /**
     * Getter method
     * @return the {@link Game#alongSideColumn}
     */
    public boolean isAlongSideRow() {return this.alongSideRow; }

    /**
     * Getter method
     * @return the {@link Game#alongSideColumn}
     */
    public boolean isAlongSideColumn() {return this.alongSideColumn; }

    /**
     * Getter method
     * @return the {@link Game#firstPlayerHasEnded}
     */
    public boolean isFirstPlayerHasEnded() {return this.firstPlayerHasEnded;}


    /**
     * Getter method
     * @return the @boolean attribute {@link Game#isEnded}
     */
    public boolean isEnded() {
        return isEnded;
    }

    /**
     * Getter method
     * @return the {@code boolean} attribute {@link Game#isFinalTurn}
     */
    public boolean isFinalTurn () { return isFinalTurn;}
}
