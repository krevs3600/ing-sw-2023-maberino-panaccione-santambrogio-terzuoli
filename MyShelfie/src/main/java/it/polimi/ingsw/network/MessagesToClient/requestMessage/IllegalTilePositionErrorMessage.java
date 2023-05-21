package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class IllegalTilePositionErrorMessage extends RequestMessage {
    private final LivingRoomBoardView board;
    private final String nickname;
    public IllegalTilePositionErrorMessage(String nickname, LivingRoomBoardView board) {
        super(MessageToClientType.ILLEGAL_POSITION);
        this.board = board;
        this.nickname = nickname;
    }

    public LivingRoomBoardView getLivingRoomBoard() {
        return board;
    }

    public String getNickname() {
        return nickname;
    }
}
