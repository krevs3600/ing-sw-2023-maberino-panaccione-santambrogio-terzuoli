package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;


import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PlayerView implements Serializable {

    private final String nickName;
    private final BookshelfView bookshelfView;
    private final PlayerStatus playerStatus;
    private final int score;
    private final PersonalGoalCard personalGoalCard;
    private final List<ScoringToken> scoringTokens;
    @Serial
    private static final long serialVersionUID = 1L;
    public PlayerView (Player player) {

        this.nickName = player.getName();
        this.bookshelfView  = new BookshelfView(player.getBookshelf(), player.getName());
        this.playerStatus = player.getStatus();
        this.score = player.getScore();
        this.personalGoalCard = player.getPersonalGoalCard();
        this.scoringTokens = player.getTokens();
    }

    public String getName () {
        return this.nickName;
    }

    public PlayerStatus getStatus () {

        return this.playerStatus;
    }

    public BookshelfView getBookshelf () {

        return this.bookshelfView;
    }

    public int getScore () {
        return this.score;
    }

    public PersonalGoalCard getPersonalGoalCard () {
        return this.personalGoalCard;
    }

    public List<ScoringToken> getTokens () {
        return this.scoringTokens;
    }
}
