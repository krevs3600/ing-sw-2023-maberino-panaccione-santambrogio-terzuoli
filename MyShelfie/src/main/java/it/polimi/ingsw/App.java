package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.cli.TextualUI;

public class App {
    public static void main(String[] args) {
        TextualUI view = new TextualUI();
        GameController controller = new GameController(view);
        view.addObserver(controller);

        view.run();
    }
}
