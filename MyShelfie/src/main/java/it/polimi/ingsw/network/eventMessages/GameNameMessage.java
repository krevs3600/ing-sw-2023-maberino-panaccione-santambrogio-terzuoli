package it.polimi.ingsw.network.eventMessages;


import it.polimi.ingsw.model.Player;

/**
 * <h1>Class GameNameMessage</h1>
 * The class GameNameMessage extends the abstract class EventMessage. With this type of message a {@link Player}
 * who has previously expressed to be wanting to join a game selects the name of the game he/she wants to join
 *
 * @author Francesca Pia Panaccione
 * @version 1.0
 * @since 5/9/2023
 */
public class GameNameMessage extends EventMessage {
    private final String GameName;
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     * @param GameName name of the game that should be joined
     */
    public GameNameMessage(String nickName,String GameName) {
        super(nickName, EventMessageType.GAME_NAME);
        this.GameName=GameName;


    }

    /**
     * Getter Method
     * @return the name of the game
     */
    public String getGameName() {
        return GameName;
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString(){
        return getNickname() + " asks to choose a specific Game Name";
    }
}
