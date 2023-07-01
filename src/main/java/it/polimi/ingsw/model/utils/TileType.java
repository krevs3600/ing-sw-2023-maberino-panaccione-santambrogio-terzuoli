package it.polimi.ingsw.model.utils;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.view.cli.ColorCLI;

/**
 * <h1>Enumeration TileType</h1>
 * The enumeration TileType contains all the types of {@link ItemTile} that can be placed e.g. on the {@link LivingRoomBoard} or on the {@link Bookshelf}
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/21/2023
 */
public enum TileType {

    CAT("C", ColorCLI.GREEN),
    BOOK("B", ColorCLI.WHITE),
    GAME("G", ColorCLI.YELLOW),
    FRAME("F", ColorCLI.BLUE),
    TROPHY("T", ColorCLI.CYAN),
    PLANT("P", ColorCLI.PURPLE);

    final String abbreviation;
    final String colorBackground;

    private TileType(String abbreviation, ColorCLI color){
        this.abbreviation = abbreviation;
        this.colorBackground = color.getCode();
    }

    /**
     * To String method
     * @return the {@code String} version of {@link TileType}
     */
    @Override
    public String toString() {
        switch (abbreviation){
            case "C" -> {
                return "Gatti";
            }
            case "B" -> {
                return "Libri";
            }
            case "G" -> {
                return "Giochi";
            }
            case "F" -> {
                return "Cornici";
            }
            case "T" -> {
                return "Trofei";
            }
            case "P" -> {
                return "Piante";
            }
            default -> {
                return "";
            }
        }
    }

    /**
     * Getter method
     * @return the {@code String} representing the abbreviation of the {@link TileType}
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Getter method
     * @return the {@code String} representing the abbreviation of the {@link TileType}
     */
    public String getColorBackground() {
        return colorBackground;
    }
}
