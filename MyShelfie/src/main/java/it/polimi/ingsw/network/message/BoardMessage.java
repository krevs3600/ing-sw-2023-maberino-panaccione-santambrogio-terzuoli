package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;

public class BoardMessage extends Message{

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
