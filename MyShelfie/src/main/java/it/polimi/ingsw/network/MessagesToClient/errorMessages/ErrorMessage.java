package it.polimi.ingsw.network.MessagesToClient.errorMessages;

import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.Client;

import java.io.Serializable;


/**
 * <h1>Abstract Class ErrorMessage</h1>
 * The abstract class ErrorMessage extends the asbtract class MessageToClient. The main purpose of the class is
 *  is for the {@link Server} to send asynchronous messages to the {@link Client}, to signal that a en error
 *  has occured
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 5/18/2023
 */
public abstract class ErrorMessage extends MessageToClient {
    private final long serialVersionUID = 1L;

    private final MessageToClientType type;
    private final String errorMessage;

    /**
     * Class constructor
     * @param nickName of the {@link Client} that has to be reached
     * @param errorMessage the content of the {@link MessageToClient}
     * @param messageType of the message
     */
    public ErrorMessage(String nickName, String errorMessage, MessageToClientType messageType) {
        super(nickName, messageType);
        this.errorMessage = errorMessage;
        this.type=messageType;
    }

    /**
     * Getter method
     * @return the {@code String} content {@link MessageToClient}
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Getter method
     * @return the type of the {@link MessageToClient}
     */
    public MessageToClientType getType() {
        return type;
    }

    /**
     * To String method
     */
    @Override
    public String toString(){
        return getNickname() + " got error: " + errorMessage;
    }

}
