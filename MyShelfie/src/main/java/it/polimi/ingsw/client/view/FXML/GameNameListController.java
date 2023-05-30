package it.polimi.ingsw.client.view.FXML;



import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

public class GameNameListController {


    private GUI gui;


    @FXML
    public TextField GameNameTextField;

    @FXML
    public Button OKButton;
    @FXML
    public VBox games;
    @FXML
    public Label InvalidGameChoice;

    @FXML
    ScrollPane scrollPane;
    private String gameToJoin;

    // quando cambio scena setto questo set di stringhe in modo che poi lo passo anche al parametro della funzione, cosi da poter fare override
    private Set<String> currentLobbyGameNames = new HashSet<>();


    public void setCurrentLobbyGameNames(Set<String> currentLobbyGameNames) {
        this.currentLobbyGameNames = currentLobbyGameNames;
    }

    public String getGameToJoin() {
        return this.gameToJoin;
    }


    public void setGui(GUI gui) {
        this.gui = gui;
    }
    // metodo per confrontare la stringa del gioco inserita con quelle presenti nella lobby

    public void GameNameChosen(MouseEvent mouseEvent) {
        // TODO: controllo sull'input vuoto
        String gameToJoin = ((Button) mouseEvent.getSource()).getText();
        this.gameToJoin = gameToJoin;
        if (currentLobbyGameNames.contains(gameToJoin)) {
            gui.showGameNamesList(currentLobbyGameNames);
        } else {
            InvalidGameChoice.setVisible(true);

        }

    }

    public void initialize(){
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        games.setSpacing(15);
        for(String name : currentLobbyGameNames){
            Button button = new Button();
            button.setText(name);
            button.getStyleClass().setAll("button");
            button.setPrefWidth(games.getPrefWidth());
            button.setPrefHeight(60);
            button.setAlignment(Pos.CENTER);
            button.setOnMouseClicked(this::GameNameChosen);
            games.getChildren().add(button);
        }
    }
}



