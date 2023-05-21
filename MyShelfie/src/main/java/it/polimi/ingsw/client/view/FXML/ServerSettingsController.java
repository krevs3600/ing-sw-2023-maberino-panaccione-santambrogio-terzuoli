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
    @FXML
    TextField ipAddress;

    @FXML
    TextField port;

    @FXML
    Button tryToConnectButton;

    private ClientImplementation client=null;

    private GUI gui=null;



    private String address;
    private int chosenport;

    public void connect(MouseEvent event) throws IOException {
        this.chosenport=Integer.parseInt(port.getText());
        this.address = (ipAddress.getText());

        try {
            Registry registry = LocateRegistry.getRegistry(address, chosenport);
            AppServer server = (AppServer) registry.lookup("MyShelfieServer");

         //   this.client = new ClientImplementation(gui, server.connect());
           // askNickname();
        } catch (NotBoundException e) {
            System.err.println("not bound exception registry");
        }


        Stage stage = JavaFxMain.getWindow();
        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/login_scene.fxml/").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

}


