package it.polimi.ingsw.persistence;

import it.polimi.ingsw.controller.GameController;

import java.io.*;
import java.nio.file.Files;

public class Storage {

    public void store (GameController gameController) {
        PersistentGame persistentGame = new PersistentGame(gameController);

        try (FileOutputStream fileOutputStream = new FileOutputStream(GameController.savedGameFile)) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(persistentGame);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void delete() {
        File file = new File(GameController.savedGameFile);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
