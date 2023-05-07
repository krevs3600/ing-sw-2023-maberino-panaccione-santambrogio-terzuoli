package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

public class PlayerView extends Observable implements Observer {

    private final Player player;

    public PlayerView (Player player) {
        this.player = player;
    }

    public String getName () {
        return player.getName();
    }

    public PlayerStatus getStatus () {
        return player.getStatus();
    }

    public BookshelfView getBookshelf () {
        return new BookshelfView(player.getBookshelf());
    }

    public int getScore () {
        return player.getScore();
    }

    public PersonalGoalCard getPersonalGoalCard () {
        return player.getPersonalGoalCard();
    }

    public List<ScoringToken> getTokens () {
        return player.getTokens();
    }
}
