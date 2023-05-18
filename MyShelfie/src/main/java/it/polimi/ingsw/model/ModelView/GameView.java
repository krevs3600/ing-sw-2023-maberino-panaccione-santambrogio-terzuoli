package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.utils.NumberOfPlayers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameView implements Serializable {
    private String id;
    private PersonalGoalCardDeckView personalGoalCardDeckView;
    private List<PlayerView> subscribers;
    private LivingRoomBoardView livingRoomBoardView;
    private NumberOfPlayers numberOfPlayers;
    private int cursor;
    private TilePackView tilePackView;
    private int currentPlayerScore;

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
}
