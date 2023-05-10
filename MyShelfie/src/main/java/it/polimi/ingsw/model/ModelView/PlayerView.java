package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;


import java.util.List;

public class PlayerView {

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
    public boolean isFirstCommonGoalAchieved () { return player.isFirstCommonGoalAchieved();}
    public boolean isSecondCommonGoalAchieved () { return player.isSecondCommonGoalAchieved();}
}
