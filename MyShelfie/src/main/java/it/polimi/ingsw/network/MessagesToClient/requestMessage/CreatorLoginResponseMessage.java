package it.polimi.ingsw.network.MessagesToClient.requestMessage;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
/**
 * <h1>Class CreatorLoginResponseMessage</h1>
 * The class CreatorLoginResponseMessage extends the abstract class RequestMessage. The purpose of this message is for the {@link Server}
 * respond to a {@link Client} who has tried to create a new {@link Game}.
 * Such response will also tell the{@link Client} whether its nickname is valid
 *
 * @author Francesco Santambrogio, Carlo Terzuoli, Francesca Pia Panaccione
 * @version 1.0
 * @since 5/18/2023
 */
public class CreatorLoginResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validNickname;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     * @param validNickname {@code  boolean} value that tells whether the {@link Client}'s nickname is valid
     */
    public CreatorLoginResponseMessage(String nickname, boolean validNickname) {
        super(nickname, MessageToClientType.CREATOR_LOGIN_RESPONSE);
        this.validNickname = validNickname;
    }

    /**
     * This method tells if the nickname chosen by the {@link Client} is valid
     */
    public boolean isValidNickname() { return validNickname;}

}
