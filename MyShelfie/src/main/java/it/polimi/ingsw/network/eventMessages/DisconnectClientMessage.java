package it.polimi.ingsw.network.eventMessages;

public class DisconnectClientMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public DisconnectClientMessage(String nickname) {
        super(nickname, EventMessageType.DISCONNECT_CLIENT);
    }
}
