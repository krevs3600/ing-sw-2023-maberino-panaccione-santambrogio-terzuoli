package it.polimi.ingsw.network.eventMessages;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class NickNameMessage</h1>
 * The class NickNameMessage extends the abstract class EventMessage. This type of message is used signal to the {@link Server}
 * that a {@link Player} who is connected to it has sent his/her nickname
 *
 * @author Francesco Maberino
 * @version 1.0
 * @since 5/10/2023
 */
public class NicknameMessage extends EventMessage {
    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     */
    public NicknameMessage(String nickName) {
        super(nickName, EventMessageType.NICKNAME);
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString(){
        return getNickname() + " asks to be subscribed";
    }
}
