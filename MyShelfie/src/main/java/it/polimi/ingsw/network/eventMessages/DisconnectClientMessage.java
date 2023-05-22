package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.Client;

public class DisconnectClientMessage extends EventMessage{


    public DisconnectClientMessage(String nickname) {
        super(nickname, EventMessageType.DISCONNECT_CLIENT);
    }


    }

