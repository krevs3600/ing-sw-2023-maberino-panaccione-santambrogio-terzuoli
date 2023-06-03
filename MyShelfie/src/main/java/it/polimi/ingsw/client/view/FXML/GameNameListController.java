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

/**
 * <h1>Class GameNameListController</h1>
 *the controller of the scene where the player who wants
 * to join a game visualizes the list of games in the lobby and
 * chooses one among them
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/23/2023
 */

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
    /**
     * Getter method
     * @return the {@link GameNameListController#gameToJoin} present in the {@link GameNameListController}
     * Which is the game the player has decided to join :
     */
    public String getGameToJoin() {
        return this.gameToJoin;
    }


    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */

    public void setGui(GUI gui) {
        this.gui = gui;
    }
    // metodo per confrontare la stringa del gioco inserita con quelle presenti nella lobby


    /**
     *
     This method is used to send the name of the game the player wants to
     join to the GUI as soon as the player clicks
     on the button corresponding to the game he wants to
     join.
     * @param mouseEvent  the click on one of the {@link GameNameListController#games} available in the lobby
     */

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

    /**
     *
     used to initialize the graphic elements of the scene {@code GameNameList_scene}
     in which the player chooses which game to join
     */

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



