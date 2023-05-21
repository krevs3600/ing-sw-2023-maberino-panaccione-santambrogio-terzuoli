package it.polimi.ingsw.network.eventMessages;

public class FillBookshelfMessage extends EventMessage{

    public FillBookshelfMessage(String nickName) {
        super(nickName, EventMessageType.FILL_BOOKSHELF);
    }
}
