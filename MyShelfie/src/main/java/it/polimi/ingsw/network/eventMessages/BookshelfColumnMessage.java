package it.polimi.ingsw.network.eventMessages;

public class BookshelfColumnMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int column;
    public BookshelfColumnMessage(String nickName, int column) {
        super(nickName, EventMessageType.BOOKSHELF_COLUMN);
        this.column = column;
    }

    public int getColumn(){
        return this.column;
    }

    @Override
    public String toString(){
        return "" + getNickname() + " selected column number " + getColumn();
    }




}
