package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.network.MessagesToServer.MessageToServerType;

import java.util.Set;

public class LoginResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validNickname;

    private  boolean GamesOk;
    private Set<String> availableGames;

    private String Nickname;

    public LoginResponseMessage(boolean validNickname) {
        super(MessageToServerType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
    }

    public LoginResponseMessage(boolean validNickname,String nickname) {
        super(MessageToServerType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
        this.Nickname=nickname;

       // this.GamesOk=GamesOk;

    }

    public LoginResponseMessage(String Nickname, boolean validNickname,boolean GamesOk,Set<String> availableGames) {
        super(MessageToServerType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
        this.Nickname=Nickname;
        this.availableGames=availableGames;
        this.GamesOk=GamesOk;
    }


public boolean isGamesOk(){return GamesOk;}
    public boolean isValidNickname() { return validNickname;}

    public String getNickname(){
        return this.Nickname;
    }

    public Set<String> getAvailableGames(){
        return this.availableGames;
    }
}
