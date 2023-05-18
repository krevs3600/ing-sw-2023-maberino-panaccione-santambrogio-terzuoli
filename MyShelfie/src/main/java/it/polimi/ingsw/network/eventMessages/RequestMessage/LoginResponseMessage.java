package it.polimi.ingsw.network.eventMessages.RequestMessage;

import it.polimi.ingsw.network.EventMessage;
import it.polimi.ingsw.network.eventMessages.MessageType;

public class LoginResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validNickname;

    private String Nickname;

    public LoginResponseMessage(boolean validNickname) {
        super(RequestMessageType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
    }

    public LoginResponseMessage(String Nickname,boolean validNickname) {
        super(RequestMessageType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
        this.Nickname=Nickname;
    }
    public boolean isValidNickname() { return validNickname;}

    public String getNickname(){
        return this.Nickname;
    }

}
