package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;

public class InsertRequestMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private int column;

    public InsertRequestMessage (String nickName, int column) {
        super(nickName, MessageType.INSERTION_REQUEST);
        this.column = column;
    }

    public int getColumn () {
        return this.column;
    }
    @Override
    public String toString() {
        return getNickName() + " is inserting in column: " + column;
    }

}
