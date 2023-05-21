package it.polimi.ingsw.network.eventMessages;

public class MenuOptionMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int menuOption;
    public MenuOptionMessage(String nickname, int menuOption) {
        super(nickname, EventMessageType.MENU_OPTION);
        this.menuOption = menuOption;
    }

    public int getMenuOption() {
        return menuOption;
    }

    @Override
    public String toString() {
        return getNickname() + " choose menu option " + getMenuOption();
    }
}
