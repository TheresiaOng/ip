package katsu;

import javafx.application.Application;

/**
 * Launcher class for the Katsu JavaFX application.
 * Serves as the entry point to start the JavaFX application.
 */
public class Launcher {
    /**
     * Main entry point for the application.
     * Launches the JavaFX application by delegating to the Main class.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
