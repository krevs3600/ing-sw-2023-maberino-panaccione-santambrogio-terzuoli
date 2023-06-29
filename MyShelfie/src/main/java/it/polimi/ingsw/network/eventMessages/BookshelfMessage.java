package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class BookshelfMessage</h1>
 * The class BookshelfMessage extends the abstract class EventMessage. This type of message is used to send to the View
 * the {@link BookshelfView}
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/9/2023
 */
public class BookshelfMessage extends EventMessage {

    private final long serialVersionUID = 1L;
    private final BookshelfView bookshelfView;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     * @param bookshelfView to be sent
     */
    public BookshelfMessage(String nickName, BookshelfView bookshelfView) {
        super(nickName, EventMessageType.BOOKSHELF);
        this.bookshelfView = bookshelfView;
    }

    /**
     * Getter method
     * @return the {@link BookshelfView} of the message
     */
    public BookshelfView getBookshelfView(){
        return this.bookshelfView;
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    public String toString(){
        return "" + getNickname() + "'s bookshelf: " + "\n" + bookshelfView.toString();
    }
}
