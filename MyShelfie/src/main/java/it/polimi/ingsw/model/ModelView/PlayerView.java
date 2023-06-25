package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.*;


import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <h1>Class PlayerView</h1>
 * This class is the immutable version of class Player
 *
 * @author Francesca Santambrogio
 * @version 1.0
 * @since 5/07/2023
 */
public class PlayerView implements Serializable {

    /**
     * Player's nickname
     */
    private final String nickName;
    /**
     * Immutable bookshelf associated to the player
     */
    private final BookshelfView bookshelfView;
    /**
     * Player's status
     */
    private final PlayerStatus playerStatus;
    /**
     * Player's score
     */
    private final int score;

    private int personalgoalcardscore;

    private int adjacenttilesscore;
    /**
     * PersonalGoalCard given to the player for the game he's playing
     */
    private final PersonalGoalCard personalGoalCard;
    /**
     * Scoring tokens associated to the CommonGoalCards that the player has won
     */
    private final List<ScoringToken> scoringTokens;
    /**
     * UID version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for class BookshelfView
     * @param player Player object to create the immutable version
     */
    public PlayerView (Player player) {

        this.nickName = player.getName();
        this.bookshelfView  = new BookshelfView(player.getBookshelf(), player.getName());
        this.playerStatus = player.getStatus();
        this.score = player.getScore();
        this.personalGoalCard = player.getPersonalGoalCard();
        this.scoringTokens = player.getTokens();
        this.personalgoalcardscore=player.getPersonalgoalcardscore();
        this.adjacenttilesscore=player.getAdjacenttilesscore();
    }

    /**
     * Getter method for the player's nickname
     * @return the player's nickname
     */
    public String getName () {
        return this.nickName;
    }
    /**
     * Getter method for the player's status
     * @return the player's status
     */
    public PlayerStatus getStatus () {
        return this.playerStatus;
    }
    /**
     * Getter method for the player's bookshelf
     * @return the player's immutable bookshelf
     */
    public BookshelfView getBookshelf () {
        return this.bookshelfView;
    }

    /**
     * Getter method for the player's score
     * @return the player's score
     */
    public int getScore () {
        return this.score;
    }
    /**
     * Getter method for the player's PersonalGoalCard
     * @return the player's PersonalGoalCard
     */
    public PersonalGoalCard getPersonalGoalCard () {
        return this.personalGoalCard;
    }


    public int getAdjacenttilesscore(){return this.adjacenttilesscore;}
    public int getPersonalgoalcardscore() {
        return personalgoalcardscore;
    }

    /**
     * Getter method for the player's won scoringTokens
     * @return the player's won scoringTokens
     */
    public List<ScoringToken> getTokens () {
        return this.scoringTokens;
    }
}
