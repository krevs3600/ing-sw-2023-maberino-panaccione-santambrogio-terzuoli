package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.BookshelfView;

public class BookshelfMessage extends EventMessage {

    private final long serialVersionUID = 1L;
    private final BookshelfView bookshelfView;
    public BookshelfMessage(String nickName, BookshelfView bookshelfView) {
        super(nickName, EventMessageType.BOOKSHELF);
        this.bookshelfView = bookshelfView;
    }

    public BookshelfView getBookshelfView(){
        return this.bookshelfView;
    }

    public String toString(){
        return "" + getNickname() + "'s bookshelf: " + "\n" + bookshelfView.toString();
    }
}
