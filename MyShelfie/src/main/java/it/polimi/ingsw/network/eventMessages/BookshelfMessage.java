package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.network.MessageType;

public class BookshelfMessage extends EventMessage {

    private final long serialVersionUID = 1L;
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
