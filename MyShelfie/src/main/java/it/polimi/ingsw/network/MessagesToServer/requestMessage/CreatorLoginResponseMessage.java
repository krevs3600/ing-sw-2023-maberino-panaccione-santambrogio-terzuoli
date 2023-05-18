package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.network.MessagesToServer.MessageToServerType;

public class CreatorLoginResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validNickname;

    private String Nickname;

    public CreatorLoginResponseMessage(boolean validNickname) {
        super(MessageToServerType.CREATOR_LOGIN_RESPONSE);
        this.validNickname = validNickname;
    }

    public CreatorLoginResponseMessage(String Nickname, boolean validNickname) {
        super(MessageToServerType.CREATOR_LOGIN_RESPONSE);
        this.validNickname = validNickname;
        this.Nickname=Nickname;
    }
    public boolean isValidNickname() { return validNickname;}

    public String getNickname(){
        return this.Nickname;
    }

}
