package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

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

    public LivingRoomBoard getLivingRoomBoard () {
        return game.getLivingRoomBoard();
    }

    public NumberOfPlayers getNumberOfPlayers () {
        return game.getNumberOfPlayers();
    }

    public int getCursor () {
        return game.getCursor();
    }

}
