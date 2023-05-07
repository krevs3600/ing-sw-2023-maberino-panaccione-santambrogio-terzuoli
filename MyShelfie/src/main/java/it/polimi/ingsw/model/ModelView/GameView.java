package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import javafx.scene.effect.Light;

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

    public List<Player> getSubscribers () {
        return game.getSubscribers();
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

    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Game model)){
            System.err.println("Discarding update from " + o);
        }

        if (arg instanceof Game game){
            setChanged();
            notifyObservers(new GameView(game));
        }

    }
}
