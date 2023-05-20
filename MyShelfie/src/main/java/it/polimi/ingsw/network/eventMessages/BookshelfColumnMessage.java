package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;

public class BookshelfColumnMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private int column;
    public BookshelfColumnMessage(String nickName, int column) {
        super(nickName, MessageType.BOOKSHELF_COLUMN);
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
