package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.network.MessageType;

public class BoardMessage extends EventMessage{
    private final long serialVersionUID = 1L;
    private LivingRoomBoardView board;
    public BoardMessage(String nickName, LivingRoomBoardView board) {
        super(nickName, MessageType.BOARD);
        this.board = board;
    }

    public LivingRoomBoardView getBoard(){
        return this.board;
    }

    public String toString(){
        return "Living room board: " + "\n" + board.toString();
    }
}
