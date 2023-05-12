package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class MenuOptionMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int menuOption;
    public MenuOptionMessage(String nickname, int menuOption) {
        super(nickname, MessageType.MENU_OPTION);
        this.menuOption = menuOption;
    }

    public int getMenuOption() {
        return menuOption;
    }

    @Override
    public String toString() {
        return getNickName() + " choose menu option " + getMenuOption();
    }
}
