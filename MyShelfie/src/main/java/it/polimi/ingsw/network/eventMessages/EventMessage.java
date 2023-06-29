package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

/**
 * <h1>Abstract Class EventMessage</h1>
 * The abstract class EventMessage main purpose is to communicate to the {@link Server} that something has changed
 * and it needs to react to it. Such changes mainly regard  the state of the model, but can also involve the state
 * of the connection of a  player
 *
 * @author Francesco Maberino, Francesco Santambrogio, Carlo Terzuoli
 * @version 1.0
 * @since 5/9/2023
 */
public abstract class EventMessage implements Serializable {
    private final String nickname;
    private final EventMessageType type;

    /**
     * Class constructor. Each message must include the nickname of the {@link Player} and must specify the
     * type of the {@link EventMessage#type}
     * @param nickName of the {@link Player} from which originates the message
     * @param messageType the type of message
     */
    public EventMessage(String nickName, EventMessageType messageType){
        this.nickname = nickName;
        this.type = messageType;
    }

    /**
     * Getter method
     * @return the nickname of the {@link Player}
     */
    public String getNickname(){
        return this.nickname;
    }

    /**
     * Getter method
     * @return the {@link EventMessage#type} fo the message
     */
    public EventMessageType getType() {
        return type;
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the message
     */
    @Override
    public String toString(){
        return this.nickname + " sends message of type " + type.name();
    }

}
