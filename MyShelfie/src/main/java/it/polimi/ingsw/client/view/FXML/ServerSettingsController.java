package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.AppServer;
import it.polimi.ingsw.network.ClientImplementation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerSettingsController {
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


    public void setGui(GUI gui) {
    this.gui=gui;
}

    private String address;
    private int chosenPort;

    public void connect(MouseEvent event) throws IOException, NotBoundException {

        address = ipAddress.getText().isEmpty() ? "127.0.0.1" : ipAddress.getText();
        chosenPort= port.getText().isEmpty() ? 1243 : Integer.parseInt(port.getText());

        //TODO: check for empty value and for string instead of integer v
        // alue
      if(isValidIPAddress(address) && isValidPort(chosenPort)) {
        //  try {
        //      Registry registry = LocateRegistry.getRegistry(address, chosenport);
        //      AppServer server = (AppServer) registry.lookup("MyShelfieServer");
        //      this.client = new ClientImplementation(gui, server.connect());
        //      gui.setClient(client);
        //  }

          gui.createConnection(address,chosenPort);
      }
      else if (!isValidIPAddress(address) || !isValidPort(chosenPort) || isValidIPAddress("")|| isValidIPAddress(" ")){
          WrongValueInputs.setVisible(true);
      }
    }


    private boolean isValidPort(int chosenPort) {
        try {
            return chosenPort >= 1 && chosenPort <= 65535;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private boolean isValidIPAddress(String address) {
        String regExp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return address.matches(regExp);
    }

}


