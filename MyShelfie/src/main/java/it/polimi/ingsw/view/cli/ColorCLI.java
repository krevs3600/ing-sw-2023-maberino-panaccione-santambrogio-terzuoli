package it.polimi.ingsw.view.cli;


    /**
     * This class contains all colors used in Cli.
     */
    public enum ColorCLI {
        //Color end string, color reset
        RESET("\033[0m"),
        CLEAR("\033[H\033[2J"),

        // Background
        RED("\033[41m"),
        GREEN("\033[42m"),
        WHITE("\033[47m"),
        YELLOW("\033[43m"),
        BLUE("\033[44m"),
        CYAN("\033[46m"),
        PURPLE("\033[45m");

        private final String code;

        ColorCLI(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
}
