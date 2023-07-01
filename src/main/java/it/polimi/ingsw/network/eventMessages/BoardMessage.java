package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.Player;

/**
 * <h1>Class BoardMessage</h1>
 * The class BoardMessage extends the abstract class EventMessage. This type of message is used to send to the View
 * the {@link LivingRoomBoardView}
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/9/2023
 */
public class BoardMessage extends EventMessage{
    private final long serialVersionUID = 1L;
    private final LivingRoomBoardView board;

    /**
     * Class constructor
     * @param nickName of the {@link Player}
     * @param board the {@link LivingRoomBoardView}
     */
    public BoardMessage(String nickName, LivingRoomBoardView board) {
        super(nickName, EventMessageType.BOARD);
        this.board = board;
    }

    /**
     * Getter method
     * @return the {@link LivingRoomBoardView}
     */
    public LivingRoomBoardView getBoard(){
        return this.board;
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    public String toString(){
        return "Living room board: " + "\n" + board.toString();
    }
}
