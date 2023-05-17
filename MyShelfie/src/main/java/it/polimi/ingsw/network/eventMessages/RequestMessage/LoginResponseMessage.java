package it.polimi.ingsw.network.eventMessages.RequestMessage;

import it.polimi.ingsw.network.EventMessage;
import it.polimi.ingsw.network.eventMessages.MessageType;

public class LoginResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validNickname;

    public LoginResponseMessage(boolean validNickname) {
        super(RequestMessageType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
    }

    public boolean isValidNickname() { return validNickname;}

}
