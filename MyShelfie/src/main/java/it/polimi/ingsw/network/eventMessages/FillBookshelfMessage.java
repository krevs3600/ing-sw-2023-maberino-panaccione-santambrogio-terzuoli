package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;

public class FillBookshelfMessage extends EventMessage{

    public FillBookshelfMessage(String nickName) {
        super(nickName, MessageType.FILL_BOOSHELF);
    }
}
