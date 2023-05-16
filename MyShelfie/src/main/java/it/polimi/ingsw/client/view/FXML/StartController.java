package it.polimi.ingsw.client.view.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class StartController {
    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button playBtn;

    @FXML
    public Button quitBtn;
    @FXML
    public Button quitBtn1;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void StartGame(ActionEvent actionEvent) {
    }

    public void closeJavaFxMain(){
       JavaFxMain.getWindow().close();
    }

    public void switchToConnection(ActionEvent event) throws IOException {
        URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/server_scene.fxml/").toURI().toURL();

        root = FXMLLoader.load(url);
        stage = JavaFxMain.getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
