package it.polimi.ingsw.model;

/**
 * <h1>Class EndGameToken</h1>
 * The class EndGameToken represents the token taken by the first player who fills all the spaces of his bookshelf
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 4/8/2023
 */
public class EndGameToken {

    /**
     * Class constructor
     */
    public EndGameToken(){}

    /**
     * This method is used to pick the end game token as soon as a player fills all the spaces of his bookshelf
     * @return EndGameToken It returns the end game token itself
     */
    public EndGameToken pick(){return this;}
}
