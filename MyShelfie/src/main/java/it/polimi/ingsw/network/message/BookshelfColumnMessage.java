package it.polimi.ingsw.network.message;

public class BookshelfColumnMessage extends Message{
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
