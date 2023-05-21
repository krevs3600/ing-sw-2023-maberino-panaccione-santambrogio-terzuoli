package it.polimi.ingsw.network.eventMessages;

public class CreatorNicknameMessage extends EventMessage {
        private final long serialVersionUID = 1L;

     public CreatorNicknameMessage(String nickName) {
            super(nickName, EventMessageType.CREATOR_NICKNAME);
        }

        @Override
        public String toString () {
            return "the creator " + getNickname() + " asks to be subscribed";
        }
    }

