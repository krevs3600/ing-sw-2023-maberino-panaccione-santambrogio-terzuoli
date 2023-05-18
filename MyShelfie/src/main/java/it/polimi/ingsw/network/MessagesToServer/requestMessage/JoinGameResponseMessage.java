package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.network.MessagesToServer.MessageToClientType;

import java.util.Set;

public class JoinGameResponseMessage extends RequestMessage {
    private Set<String> AvailableGamesInLobby;
    public JoinGameResponseMessage(boolean validJoin,Set<String> availableGamesInLobby) {
        super(MessageToClientType.JOINGAME_RESPONSE);
        this.AvailableGamesInLobby=availableGamesInLobby;
    }

    public Set<String> getAvailableGamesInLobby(){
        return this.AvailableGamesInLobby;
    }
}
