package it.polimi.ingsw.model;

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

    /**
     * Class constructor
     */
    private TileType(String abbreviation, String colorBackground){
        this.abbreviation = abbreviation;
        this.colorBackground = colorBackground;
    }

    /**
     * This getter method gets the abbreviation of the tile type
     * @return String It returns the string with the characters of the tile type abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }
}
