package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import javafx.scene.effect.Light;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameView extends Observable implements Observer {
    private final Game game;

    public GameView (Game game) {
        this.game = game;
    }

    public String getId () {
        return game.getId();
    }

    public PersonalGoalCardDeck getPersonalGoalCardDeck () {
        return game.getPersonalGoalCardDeck();
    }

    public List<PlayerView> getSubscribers () {

        return new ArrayList<>(game.getSubscribers().stream().map(PlayerView::new).toList());
    }

    public LivingRoomBoardView getLivingRoomBoard () {
        return new LivingRoomBoardView(game.getLivingRoomBoard());
    }

    public NumberOfPlayers getNumberOfPlayers () {
        return game.getNumberOfPlayers();
    }

    public int getCursor () {
        return game.getCursor();
    }

    public TilePackView getTilePack () { return new TilePackView(game.getTilePack());}

    @Override
    public void update(Observable o, Object arg) {
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
}
