package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.ClientImplementation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * <h1>Class ServerSettingController</h1>
 *this class represents the controller of the fxml scene where
 * the player inserts the IPaddress and the port
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/14/2023
 */

public class ServerSettingsController {
    public String resource = "AddressIp_scene.fxml";
    @FXML
    public AnchorPane PaneforParquet;
    public Text WrongValueInputs;
    @FXML
    TextField ipAddress;

    @FXML
    TextField port;

    @FXML
    Button tryToConnectButton;

    private ClientImplementation client = null;

    private GUI gui=null;

    private ClientImplementation clientImplementation = null;

    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */

    public void setGui(GUI gui) {
    this.gui=gui;
}

    private String address;
    private int chosenPort;


    /**
     *
     This method is used to connect the user as soon as he confirms his choice by pressing the {@link ServerSettingsController tryToConnectButton}
     * @param event the click on the {@link ServerSettingsController tryToConnectButton}
     */

    public void connect(MouseEvent event) throws IOException, NotBoundException {

        address = ipAddress.getText().isEmpty() ? "127.0.0.1" : ipAddress.getText();
        chosenPort = port.getText().isEmpty() ? 1099 : Integer.parseInt(port.getText());

        if(isValidIPAddress(address) && isValidPort(chosenPort)) {
            gui.connectToServer(address, chosenPort);
        }
        else if (!isValidIPAddress(address) || !isValidPort(chosenPort) || isValidIPAddress("")|| isValidIPAddress(" ")){
            WrongValueInputs.setVisible(true);
        }
    }
    /**
     *
     This method is used to check the validity of a specific port
     * @param chosenPort the port chosen by the player to get access to the game
     */
    private boolean isValidPort(int chosenPort) {
        try {
            return chosenPort >= 1 && chosenPort <= 65535;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     *
     This method is used to check the validity of an IPAddress

     * @param address the address chosen by the player to get access to the game
     */
    private boolean isValidIPAddress(String address) {
        String regExp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return address.matches(regExp);
    }

    public void goBack(){
        gui.goBackToPreviousScene(resource);
    }

}


