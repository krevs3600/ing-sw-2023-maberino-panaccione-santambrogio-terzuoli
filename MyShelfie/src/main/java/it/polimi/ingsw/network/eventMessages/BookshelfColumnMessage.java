package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class BookshelfColumnMessage extends EventMessage {
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
        return "" + getNickName() + " selected column number " + getColumn();
    }




}
