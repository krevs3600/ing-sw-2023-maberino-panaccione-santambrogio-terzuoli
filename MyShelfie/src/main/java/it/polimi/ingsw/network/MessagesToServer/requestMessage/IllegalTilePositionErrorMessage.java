package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.ModelView.TilePackView;
import it.polimi.ingsw.model.TilePack;
import it.polimi.ingsw.network.MessagesToServer.MessageToClientType;
import it.polimi.ingsw.network.MessagesToServer.errorMessages.ErrorMessage;

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
