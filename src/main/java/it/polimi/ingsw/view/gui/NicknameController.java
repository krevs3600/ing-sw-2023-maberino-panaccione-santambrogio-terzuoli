package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


/**
 * <h1>Class NicknameController</h1>
 *this class represents the controller of the fxml scene of the lobby where
 *  the playerchooses a nickname to register in the game
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/14/2023
 */

public class NicknameController {
    public String resource = "login_scene.fxml";

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
    TextField nicknameTextField;




    private String nickname;
    private GUI gui;


    /**
     * Getter method
     * @return the {@link NicknameController#nickname} present in the {@link NicknameController}
     * which is the unique nickname chosen by the player
     *
     */

    public String getNickname(){
        return this.nickname;
    }

    /**
     *
     This method is used to take the nickname typed by the player  as soon as he confirms his choice by pressing the {@link NicknameController OKButton} in the
     {@code login_scene} and after that, the nickname is sent to the GUI to subsequently verify its validity.
     * @param event the click on the {@link NicknameController#OKButton}
     */


    public void connect(MouseEvent event) throws IOException {
        this.nickname = nicknameTextField.getText();
        gui.askNickname();
    }

    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void goBack(){
        gui.goBackToPreviousScene(resource);
    }
}
