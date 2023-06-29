package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class GameNameChoiceMessage</h1>
 * The class GameNameChoiceMessage extends the abstract class EventMessage. With this type of message a {@link Player}
 * who has previously expressed to be wanting to join a game selects the name of the game he/she wants to join
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/9/2023
 */
public class GameNameChoiceMessage extends EventMessage {

    private final long serialVersionUID = 1L;
    String gameChoice;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     * @param gameChoice the name of the game that the {@link Player} wants to join
     */
    public GameNameChoiceMessage(String nickName, String gameChoice) {
        super(nickName, EventMessageType.GAME_CHOICE);
        this.gameChoice = gameChoice;
    }

    /**
     * Getter Method
     * @return the name of the game
     */
    public String getGameChoice () { return  gameChoice;}

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    public String toString(){
        return "" + getNickname() + " has chosen the " + getGameChoice() + " game";
    }
}
