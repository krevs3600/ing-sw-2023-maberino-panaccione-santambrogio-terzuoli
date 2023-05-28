package it.polimi.ingsw.network.eventMessages;

public class ColumnChoiceMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    public ColumnChoiceMessage(String player) {
        super(player, EventMessageType.COLUMN_CHOICE);
    }

    @Override
    public String toString() {
        return getNickname() + " is choosing the column where to insert the tiles";
    }
}
