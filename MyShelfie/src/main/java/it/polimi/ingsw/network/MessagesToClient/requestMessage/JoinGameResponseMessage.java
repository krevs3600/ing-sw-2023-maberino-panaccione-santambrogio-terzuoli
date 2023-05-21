package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.util.Set;

public class JoinGameResponseMessage extends RequestMessage {
    private Set<String> AvailableGamesInLobby;
    public JoinGameResponseMessage(boolean validJoin,Set<String> availableGamesInLobby) {
        super(MessageToClientType.JOIN_GAME_RESPONSE);
        this.AvailableGamesInLobby=availableGamesInLobby;
    }

    public Set<String> getAvailableGamesInLobby(){
        return this.AvailableGamesInLobby;
    }
}
