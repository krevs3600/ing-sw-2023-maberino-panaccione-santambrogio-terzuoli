package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Bookshelf;

/**
 * <h1>Class BookshelfColumnMessage</h1>
 * The class BookshelfColumnMessage extends the abstract class EventMessage. This type of message is used to signal to the {@link Server}
 * that the current {@link Player} has selected in which column of his {@link Bookshelf} to insert the tiles he/she has
 * picked
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/9/2023
 */
public class BookshelfColumnMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int column;
    public BookshelfColumnMessage(String nickName, int column) {
        super(nickName, EventMessageType.BOOKSHELF_COLUMN);
        this.column = column;
    }

    /**
     * Getter method
     * @return the column of the {@link Bookshelf} that has been selected
     */
    public int getColumn(){
        return this.column;
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString(){
        return "" + getNickname() + " selected column number " + getColumn();
    }




}
