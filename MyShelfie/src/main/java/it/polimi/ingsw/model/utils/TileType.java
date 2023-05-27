package it.polimi.ingsw.model.utils;

/**
 * <h1>Enumeration TileType</h1>
 * The enumeration TileType represents the types of item tiles which are essential for the goal card achievement
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getColorBackground() {
        return colorBackground;
    }
}
