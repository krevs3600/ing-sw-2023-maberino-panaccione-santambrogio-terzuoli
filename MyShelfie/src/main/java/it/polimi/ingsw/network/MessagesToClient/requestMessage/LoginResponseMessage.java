package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.util.Set;

public class LoginResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validNickname;

    private  boolean GamesOk;
    private Set<String> availableGames;

    public LoginResponseMessage(String nickname, boolean validNickname) {
        super(nickname, MessageToClientType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
    }

    public LoginResponseMessage(String nickname, boolean validNickname,boolean GamesOk,Set<String> availableGames) {
        super(nickname, MessageToClientType.LOGIN_RESPONSE);
        this.validNickname = validNickname;
        this.availableGames=availableGames;
        this.GamesOk=GamesOk;
    }


public boolean isGamesOk(){return GamesOk;}
    public boolean isValidNickname() { return validNickname;}

    public Set<String> getAvailableGames(){
        return this.availableGames;
    }
}
