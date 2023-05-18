package it.polimi.ingsw.network.MessagesToServer.requestMessage.errorMessages;

public class JoinErrorMessage extends ErrorMessage {
    public JoinErrorMessage(String nickName, String errorMessage) {
        super(nickName, errorMessage,ErrorMessageType.JOIN_GAME_ERROR);
    }

}
