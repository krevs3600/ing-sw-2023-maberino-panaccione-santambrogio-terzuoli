package it.polimi.ingsw.view.cli;

public enum MessageCLI {
    TITLE,
    GAME_MODE,
    MENU;

    @Override
    public String toString() {
        switch(this){
            case TITLE -> {
                return
                        """                                                                                                                                                                                                                                                                                                                                                                                 
                                                                                                📚📚📚                                                                                             \s
                                                                                              📚📚📚📚📚                                                                                           \s
                                            📚📚📚📚   📚📚📚📚                            📚📚📚 📚📚📚                                              📚📚                                      \s
                                             📚📚📚     📚📚📚                            📚📚📚  📚📚📚    📚📚                            📚📚   📚📚📚📚                                    \s
                                             📚📚📚     📚📚📚                            📚📚📚  📚📚📚    📚📚                            📚📚  📚📚📚📚📚                                   \s
                                              📚📚📚   📚📚📚                             📚📚   📚📚📚     📚📚                            📚📚  📚📚  📚📚📚                                 \s
                                              📚📚📚  📚📚📚      📚📚📚  📚📚📚        📚📚📚             📚📚                            📚📚  📚📚  📚📚📚                                 \s
                                              📚📚📚  📚📚📚     📚📚📚   📚📚📚         📚📚📚📚          📚📚📚📚                       📚📚   📚📚   📚📚   📚                            \s
                                              📚📚📚  📚📚📚      📚📚📚  📚📚📚            📚📚📚📚       📚📚📚📚📚       📚📚📚       📚📚   📚📚                  📚📚📚                \s
                                              📚📚📚📚📚📚📚       📚📚📚📚📚📚               📚📚📚📚     📚📚📚📚📚📚   📚📚📚📚      📚📚  📚📚📚📚📚   📚📚    📚📚📚📚               \s
                                              📚📚 📚📚 📚📚          📚📚📚📚                   📚📚📚     📚📚📚📚📚📚  📚📚  📚📚📚   📚📚   📚📚📚📚📚  📚📚   📚📚  📚📚📚            \s
                                              📚📚 📚📚 📚📚   📚📚   📚📚📚             📚📚    📚📚📚    📚📚📚 📚📚📚 📚📚📚📚📚📚   📚📚    📚📚📚      📚📚  📚📚📚📚📚📚            \s
                                              📚📚 📚📚 📚📚  📚📚📚  📚📚📚            📚📚📚   📚📚📚   📚📚     📚📚  📚📚📚📚📚📚   📚📚      📚📚      📚📚  📚📚📚📚📚📚            \s
                                              📚📚  📚  📚📚   📚📚📚📚📚📚             📚📚📚📚 📚📚📚   📚📚     📚📚  📚📚            📚📚      📚📚      📚📚   📚📚                    \s
                                             📚📚📚    📚📚📚   📚📚📚📚📚               📚📚📚📚📚📚📚   📚📚     📚📚  📚📚    📚📚   📚📚       📚📚      📚📚   📚📚    📚📚           \s
                                             📚📚📚    📚📚📚   📚📚📚📚                  📚📚📚📚📚📚    📚📚     📚📚   📚📚📚📚📚📚  📚📚       📚📚      📚📚   📚📚📚📚📚📚           \s
                                             📚📚📚    📚📚📚    📚📚📚                     📚📚📚📚      📚📚📚   📚📚    📚📚📚📚📚    📚📚      📚📚       📚📚    📚📚📚📚📚            \s
                                             📚📚📚    📚📚📚    📚📚📚                     📚📚📚📚      📚📚📚  📚📚📚    📚📚📚📚     📚📚     📚📚📚      📚📚     📚📚📚📚             \s
                                                                                                                                                                                                                                                                                                                               \
                                """;
            }

            case GAME_MODE -> {
                return
                        """
                                Please select the game mode, cli or gui
                                 - A) Retro mode from textual user interface
                                 - B) Graphical User Interface""";
            }

            case MENU -> {
                return
                        """

                                 Please choose what you'd like to do
                                 - 1) create game
                                 - 2) join game
                                 - 3) resume game
                                 - 4) reload game
                                 - 5) exit\
                                """;
            }
            default -> {
                return "";
            }
        }
    }
}
