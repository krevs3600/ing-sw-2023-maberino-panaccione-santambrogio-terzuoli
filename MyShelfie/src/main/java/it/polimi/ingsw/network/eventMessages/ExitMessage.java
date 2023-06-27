package it.polimi.ingsw.network.eventMessages;

public class ExitMessage extends EventMessage{
    private final long serialVersionUID = 1L;
    public ExitMessage(String nickname) {
        super(nickname, EventMessageType.EXIT);
    }


    }

