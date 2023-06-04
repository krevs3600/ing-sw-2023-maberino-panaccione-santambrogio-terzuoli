package it.polimi.ingsw.model.utils;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.LivingRoomBoard;

/**
 * <h1>Enumeration TileType</h1>
 * The enumeration TileType contains all the types of {@link ItemTile} that can be placed e.g. on the {@link LivingRoomBoard} or on the {@link Bookshelf}
 *
 * @author Carlo Terzuoli, Francesco Maberino
 * @version 1.0
 * @since 3/21/2023
 */
public enum TileType {

    CAT("C", "\033[42m"),
    BOOK("B", "\033[47m"),
    GAME("G", "\033[43m"),
    FRAME("F", "\033[44m"),
    TROPHY("T", "\033[46m"),
    PLANT("P", "\033[45m");

    final String abbreviation;
    final String colorBackground;

    private TileType(String abbreviation, String colorBackground){
        this.abbreviation = abbreviation;
        this.colorBackground = colorBackground;
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
