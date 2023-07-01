package it.polimi.ingsw;

import it.polimi.ingsw.view.gui.GuiApp;
import it.polimi.ingsw.view.cli.CLI;
import javafx.application.Application;

/**
 * <h1>Class ClientApp</h1>
 * The class ClientApp is used to run the CLI
 *
 * @author Francesco Santambrogio, Francesco Maberino, Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/18/2023
 */

public class ClientApp {
    /**
     * Main client application method
     */
    public static void main(String[] args) {

        boolean cliParam = false; // default value

        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliParam = true;
                break;
            }
        }

        if (cliParam) {
            CLI cli = new CLI();
            cli.run();
        } else {
            Application.launch(GuiApp.class);
        }
    }
}
