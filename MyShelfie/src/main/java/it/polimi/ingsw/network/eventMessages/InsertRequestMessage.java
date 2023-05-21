package it.polimi.ingsw.network.eventMessages;

public class InsertRequestMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private int column;

    public InsertRequestMessage (String nickName, int column) {
        super(nickName, EventMessageType.INSERTION_REQUEST);
        this.column = column;
    }

    public int getColumn () {
        return this.column;
    }
    @Override
    public String toString() {
        return getNickname() + " is inserting in column: " + column;
    }

}
