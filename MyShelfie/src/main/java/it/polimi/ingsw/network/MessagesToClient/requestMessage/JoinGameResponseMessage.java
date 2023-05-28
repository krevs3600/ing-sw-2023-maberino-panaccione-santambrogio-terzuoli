package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.util.Set;

public class JoinGameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private Set<String> AvailableGamesInLobby;
    public JoinGameResponseMessage(String nickname, boolean validJoin,Set<String> availableGamesInLobby) {
        super(nickname, MessageToClientType.JOIN_GAME_RESPONSE);
        this.AvailableGamesInLobby=availableGamesInLobby;
    }

    public Set<String> getAvailableGamesInLobby(){
        return this.AvailableGamesInLobby;
    }
}
