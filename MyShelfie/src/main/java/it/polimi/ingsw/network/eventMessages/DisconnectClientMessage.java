package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.Client;

public class DisconnectClientMessage extends EventMessage{

    private final Client client;
    public DisconnectClientMessage(Client client, String nickname) {
        super(nickname, EventMessageType.DISCONNECT_CLIENT);
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
