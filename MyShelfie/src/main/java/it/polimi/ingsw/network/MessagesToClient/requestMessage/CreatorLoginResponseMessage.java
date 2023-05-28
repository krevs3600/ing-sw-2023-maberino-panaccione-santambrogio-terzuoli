package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class CreatorLoginResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validNickname;

    public CreatorLoginResponseMessage(String nickname, boolean validNickname) {
        super(nickname, MessageToClientType.CREATOR_LOGIN_RESPONSE);
        this.validNickname = validNickname;
    }
    public boolean isValidNickname() { return validNickname;}

}
