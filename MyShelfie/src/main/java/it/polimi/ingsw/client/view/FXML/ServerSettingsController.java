/*package it.polimi.ingsw.client.view.FXML;

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

public class ServerSettingsController {
    @FXML
    public AnchorPane PaneforParquet;
    @FXML
    TextField ipAddress;

    @FXML
    TextField port;

    @FXML
    Button tryToConnectButton;



    private int serverPort;
    private String ip;

    public void connect(MouseEvent event) throws IOException {
        this.ip = ipAddress.getCharacters().toString();
        this.serverPort = Integer.parseInt(port.getCharacters().toString());

        Stage stage = JavaFxMain.getWindow();
        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/login_scene.fxml/").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

}

 */
