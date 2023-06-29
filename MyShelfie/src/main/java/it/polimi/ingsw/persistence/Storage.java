package it.polimi.ingsw.persistence;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;

import java.io.*;
import java.nio.file.Files;
/**
 * <h1>Class Storage</h1>
 * The class Storage is used to store the {@link GameController} on a file, so that the {@link Game}
 * can be reloaded, when the player decides to reload it in the menu, in case a server crash happened
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/22/2023
 */
public class Storage {

    /**
     * This methd is used to store the {@link GameController} on a file
     * @param gameController
     */
    public void store (GameController gameController) {
        PersistentGame persistentGame = new PersistentGame(gameController);

        try (FileOutputStream fileOutputStream = new FileOutputStream(GameController.savedGameFile)) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(persistentGame);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to restore the {@link GameController} from a file in input
     * @return the {@link GameController} of the {@link Game}, the player wishes to reload
     */
    public GameController restore () {
        PersistentGame persistentGame;

        try (FileInputStream fileInputStream = new FileInputStream(GameController.savedGameFile)) {

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            persistentGame = (PersistentGame) objectInputStream.readObject();
            return persistentGame.gameController();

        } catch (IOException | ClassNotFoundException e) {
         return null;
        }
    }

    /**
     * Delete a saved {@link GameController}
     */
    public void delete() {
        File file = new File(GameController.savedGameFile);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
