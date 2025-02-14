package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * <h1>Class CreateorJoinGameController</h1>
 *this class represents the controller of the fxml scene where
 *  the player where the player chooses whether to create a game or join an existing one
 *
 * @author Francesca Pia Panaccione
 * @version 1.0
 * @since 5/23/2023
 */
public class MenuController {
    public String resource = "Menu_scene.fxml";
    @FXML
    public Button CreateNewGame;
    @FXML
    public Button joinGame;
    @FXML
    public Button EXIT;
    @FXML
    public Button ResumeGameButton;
    @FXML
    public Button ReloadGameButton;

    private boolean createGame = false;
    private GUI gui;



    /**
     * Getter method
     * @return the {@link MenuController#createGame} present in the {@link MenuController}
     * which is a booelan that is "true" if the player decides to create a new game
     *
     */
    public boolean getCreateGame(){
        return createGame;
    }

    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */
    public void setGui(GUI gui){
        this.gui=gui;
   }

    /**
     *
     This method is used to handle the click of the {@link MenuController#CreateNewGame} button that the player selects
     if he decides to create a new game
     * @param mouseEvent  the click on the {@link MenuController#CreateNewGame}  button
     */
    public void CreateGame(MouseEvent mouseEvent) {
       createGame=true;
       gui.gameMenu();
    }

    /**
     *
     This method is used to handle the click of the {@link MenuController#joinGame} button that the player selects
     if he decides to join an existing game
     * @param mouseEvent  the click on the {@link MenuController#joinGame}  button
     */


    public void JoinGame(MouseEvent mouseEvent) {
       //gui.gameMenu();
        gui.joinGame();
    }

    /**
     *
     This method is used to handle the click of the {@link MenuController#EXIT} button that the player selects
     if he decides to exit application
     * @param mouseEvent  the click on the {@link MenuController#joinGame}  button
     */
    public void ExitGame(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void ResumeGame(MouseEvent mouseEvent) {
        gui.resumeGame();
    }

    public void ReloadGame(MouseEvent mouseEvent) {
        gui.reloadGame();
    }
}
