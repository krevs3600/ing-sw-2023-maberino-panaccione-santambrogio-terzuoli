package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.ModelView.BookshelfView;

public class BookshelfMessage extends Message{

    private BookshelfView bookshelfView;
    public BookshelfMessage(String nickName, BookshelfView bookshelfView) {
        super(nickName, MessageType.BOOKSHELF);
        this.bookshelfView = bookshelfView;
    }

    public BookshelfView getBookshelfView(){
        return this.bookshelfView;
    }

    public String toString(){
        return "" + getNickName() + "'s bookshelf: " + "\n" + bookshelfView.toString();
    }
}
