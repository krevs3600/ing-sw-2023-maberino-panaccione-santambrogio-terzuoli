package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import junit.framework.TestCase;
import org.junit.Before;

public class GameControllerTest extends TestCase {


    private GameController gameController;

    private Game twoPlayerGame;
    private Game threePlayerGame;
    private Game fourPlayerGame;

    private String twoPlayerGameName;
    private String threePlayerGameName;
    private String fourPlayerGameName;

    @Before
    public void setUp(){
        twoPlayerGameName = "twoPlayerGameName";
        threePlayerGameName = "threePlayerGameName";
        fourPlayerGameName = "fourPlayerGameName";
        twoPlayerGame = new Game(NumberOfPlayers.TWO_PLAYERS, twoPlayerGameName);
        threePlayerGame = new Game(NumberOfPlayers.THREE_PLAYERS, threePlayerGameName);
        fourPlayerGame = new Game(NumberOfPlayers.FOUR_PLAYERS, fourPlayerGameName);
    }

}