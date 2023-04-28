package it.polimi.ingsw.model;

import it.polimi.ingsw.model.LivingRoomBoard;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class LivingRoomBoardTest {

    /**
     * Testing the creation of the object, not its correctness
     */
    @Test
    public void constructorArgumentTest(){
        int[] testNumberOfPlayers = {2,3,4};
        for (int i: testNumberOfPlayers){
            LivingRoomBoard testLivingRoomBoard = new LivingRoomBoard(i);
            assertTrue(testLivingRoomBoard != null); // no errors in the creation of the object
        }

        int[] testExceptions = {0,1,5};
        for (int i: testExceptions){
            assertThrows(IllegalArgumentException.class, () -> {
                new LivingRoomBoard(i);;
            });

        }
    }

    /**
     * Each space after the initialization should be != null and not free
     * getAllFree should return 0
     * getDrawableTiles should get 16
     */
    @Test
    public void livingRoomStateTestAfterInit(){
        int MAX_WIDTH = 9;
        int MAX_HEIGHT = 9;
        int[] testNumberOfPlayers = {2, 3, 4};
        int[] testDrawableTiles = {16, 20, 20};
        for (int n = 0; n < 3; n++){
            System.out.println("Number of players under test: " + testNumberOfPlayers[n]);
            LivingRoomBoard livingRoomBoard = new LivingRoomBoard(testNumberOfPlayers[n]);
            for(int i = 0; i < MAX_WIDTH; i++){
                for(int j = 0; j < MAX_HEIGHT; j++){
                    assertNotNull(livingRoomBoard.getSpace(new Position(i,j)));
                    assertTrue(!livingRoomBoard.getSpace(new Position(i,j)).isFree());
                }
            }
            System.out.println(livingRoomBoard.getAllFree().size());
            System.out.println(livingRoomBoard.getDrawableTiles().size());
            for(Space space : livingRoomBoard.getDrawableTiles()){
                System.out.println(space.getPosition().toString());
            }
            assertTrue(livingRoomBoard.getAllFree().size() == 0);
            assertTrue(livingRoomBoard.getDrawableTiles().size() == testDrawableTiles[n]);
        }

    }

    @Test
    public void printBoardTest(){
        int[] test = {2,3,4};

        for(int i: test){
            LivingRoomBoard livingRoomBoard = new LivingRoomBoard(i);
            System.out.println(livingRoomBoard);
            assertNotNull(livingRoomBoard);
        }
    }

    @Test
    public void drawTile(){
        LivingRoomBoard livingRoomBoard = new LivingRoomBoard(4);
        System.out.println(livingRoomBoard.toString());
        livingRoomBoard.getSpace(new Position(4,0)).drawTile();
        livingRoomBoard.getSpace(new Position(0,3)).drawTile();
        livingRoomBoard.getSpace(new Position(3,7)).drawTile();
        livingRoomBoard.getSpace(new Position(7,3)).drawTile();
        System.out.println(livingRoomBoard.toString());
    }









}
