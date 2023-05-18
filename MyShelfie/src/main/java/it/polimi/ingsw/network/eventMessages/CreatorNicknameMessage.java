package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;

public class CreatorNicknameMessage extends EventMessage {
        private final long serialVersionUID = 1L;

     public CreatorNicknameMessage(String nickName) {
            super(nickName, MessageType.CREATOR_NICKNAME);
        }

        @Override
        public String toString () {
            return "the creator " +getNickName() + " asks to be subscribed";
        }
    }

