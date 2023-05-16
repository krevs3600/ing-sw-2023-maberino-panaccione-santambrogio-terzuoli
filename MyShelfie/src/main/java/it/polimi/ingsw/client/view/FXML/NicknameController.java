package it.polimi.ingsw.client.view.FXML;

import javafx.event.ActionEvent;
import javafx.event.Event;
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

public class NicknameController {

    public AnchorPane PaneforParquet;
    public Button TryToConnectButton;
    @FXML
    TextField nicknameTextFiled;

    private String nickname;

    public void connect(ActionEvent event) throws IOException {
        this.nickname = nicknameTextFiled.getCharacters().toString();

        Stage stage = JavaFxMain.getWindow();
        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/livingBoard_scene.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

}
