package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;

/**
 * <h1>Class LoginResponseMessage</h1>
 * The class LoginResponseMessage extends the abstract class RequestMessage. Message the {@link Server} sends {@link Client}
 * after a login attempt, to tell if the nickname provided is valid
 *
 * @author Francesca Pia Panaccione
 * @version 1.0
 * @since 5/18/2023
 */
public class LoginResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validNickname;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     * @param validNickname {@code boolean} value telling if {@code nickname} is valid
     */
    public LoginResponseMessage(String nickname, boolean validNickname) {
        super(nickname, MessageToClientType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
    }

    /**
     * method that tells if the provided {@code nickname} is valid
     */
    public boolean isValidNickname() { return validNickname;}
}
