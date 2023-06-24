package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class LoginResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validNickname;

    public LoginResponseMessage(String nickname, boolean validNickname) {
        super(nickname, MessageToClientType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
    }

    public boolean isValidNickname() { return validNickname;}
}
