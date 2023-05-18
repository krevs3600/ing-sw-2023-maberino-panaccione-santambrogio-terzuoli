package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.network.MessagesToServer.MessageToServerType;

import java.util.Set;

public class JoinGameResponseMessage extends RequestMessage {
    private Set<String> AvailableGamesInLobby;
    public JoinGameResponseMessage(boolean validJoin,Set<String> availableGamesInLobby) {
        super(MessageToServerType.JOINGAME_RESPONSE);
        this.AvailableGamesInLobby=availableGamesInLobby;
    }

    public Set<String> getAvailableGamesInLobby(){
        return this.AvailableGamesInLobby;
    }
}
