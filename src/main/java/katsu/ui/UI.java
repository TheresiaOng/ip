package katsu.ui;
import katsu.Katsu;

/**
 * UI class for Katsu the Duck application.
 * Handles UI.
 */
public class UI {
    public static final String INDENT = "    ";
    public static final String SEPARATOR = INDENT + "____________________________________________________________";

    /**
     * Outputs the starting text for
     * Katsu the Duck application
     */
    public void startingText() {
        String logo = INDENT + " _  __     _\n"
                + INDENT + "| |/ /__ _| |_ ___ _   _\n"
                + INDENT + "| ' // _` | __/ __| | | |\n"
                + INDENT + "| . \\ (_| | |_\\__ \\ |_| |\n"
                + INDENT + "|_|\\_\\__,_|\\__|___/\\__,_|\n";

        System.out.println(UI.SEPARATOR);
        System.out.println(logo);
        System.out.println(INDENT + "Hello! I'm " + Katsu.NAME + " ꒰ঌ( •ө• )໒꒱");
        System.out.println(INDENT + "What can I do for you?");
        System.out.println(UI.SEPARATOR + "\n");
    }
}
