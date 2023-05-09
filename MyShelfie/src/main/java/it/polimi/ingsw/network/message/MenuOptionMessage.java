package it.polimi.ingsw.network.message;

public class MenuOptionMessage extends Message {

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
