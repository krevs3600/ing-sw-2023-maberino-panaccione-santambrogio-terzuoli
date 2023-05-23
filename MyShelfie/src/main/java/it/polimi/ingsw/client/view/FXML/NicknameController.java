package it.polimi.ingsw.client.view.FXML;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class NicknameController  {

  @FXML
    public AnchorPane PaneforParquet;

  @FXML
    public Button OKButton;
    @FXML
    public Label InvalidNickname;

    @FXML
    public Button CreateNewGame;

    @FXML
    public Button joinGame;


    //TODO: refactor field

    @FXML
    TextField nicknameTextFiled;



    private String nickname;
    private GUI gui;


    public String getNickname(){
        return this.nickname;
    }

    public void connect(MouseEvent event) throws IOException {
        //TODO: FARE i check sull'input che arriva
        this.nickname = nicknameTextFiled.getText();
        gui.askNickname();







    }

    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
