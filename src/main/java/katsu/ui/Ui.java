package katsu.ui;

import katsu.Katsu;

/**
 * Ui class for Katsu the Duck application.
 * Handles Ui.
 */
public class Ui {
    public static final String INDENT = "    ";
    public static final String SEPARATOR = INDENT + "____________________________________________________________";

    /**
     * Outputs the starting text for
     * Katsu the Duck application
     */
    public void printStartingText() {
        String logo = INDENT + " _  __     _\n"
                + INDENT + "| |/ /__ _| |_ ___ _   _\n"
                + INDENT + "| ' // _` | __/ __| | | |\n"
                + INDENT + "| . \\ (_| | |_\\__ \\ |_| |\n"
                + INDENT + "|_|\\_\\__,_|\\__|___/\\__,_|\n";

        System.out.println(Ui.SEPARATOR);
        System.out.println(logo);
        System.out.println(INDENT + "Hello! I'm " + Katsu.NAME + " ꒰ঌ( •ө• )໒꒱");
        System.out.println(INDENT + "What can I do for you?");
        System.out.println(Ui.SEPARATOR + "\n");
    }
}
