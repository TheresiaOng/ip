package katsu;

import katsu.parser.Parser;
import katsu.storage.Storage;
import katsu.tasks.CustomList;
import katsu.tasks.Deadline;
import katsu.tasks.Event;
import katsu.tasks.ToDo;
import katsu.ui.UI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * Main class for Katsu the Duck application.
 * Handles task management, user interface, and storage operations.
 * The <code>Katsu</code> object runs the main program loop and executes user commands.
 */
public class Katsu {
    public static final String NAME = "Katsu the Duck";
    private boolean active;
    private CustomList list;
    private Storage storage;
    private UI ui;

    /**
     * Starting point of the program.
     * Creates a new <code>Katsu</code> object and run it.
     */
    public static void main(String[] args) {
        Katsu katsu = new Katsu();
        katsu.run();
    }

    public enum TaskType {
        TODO, DEADLINE, EVENT
    }

    /**
     * Constructs a new <code>Katsu</code> object.
     * Initializes task list, storage, and UI components.
     * The application is initially inactive.
     */
    public Katsu() {
        this.active = false;
        this.list = new CustomList();
        this.storage = new Storage();
        this.ui = new UI();
    }

    /**
     * Starts the Katsu application.
     * Loads tasks from storage, prints the welcome message, and listens for user input
     * until the application is deactivated.
     */
    public void run() {
        this.active = true;
        try {
            this.list = this.storage.loadSave();
        } catch (FileNotFoundException e) {
            System.out.println(UI.INDENT + "No save file found.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "Wrong task format in save file.");
        }

        this.ui.startingText();
        Scanner scanner = new Scanner(System.in);

        while (this.active) {
            String order = scanner.nextLine();
            Parser.handleCommand(order, this);
        }
    }

    /**
     * Converts a date string into a <code>LocalDate</code> object.
     *
     * @param dateString The date in "yyyy-MM-dd" format.
     * @return <code>LocalDate</code> representing the input string.
     */
    public static LocalDate stringToDateConverter(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * Converts a date and time string into a <code>LocalDateTime</code> object.
     *
     * @param dateTimeString The date in "yyyy-MM-dd HH:mm" format.
     * @return <code>LocalDateTime</code> representing the input string.
     */
    public static LocalDateTime stringToDateTimeConverter (String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /**
     * Prints all available commands to user.
     */
    public static void allCommands() {
        System.out.println(UI.INDENT + "1. list / ls (to show all of your katsu.tasks)");
        System.out.println(UI.INDENT + "2. bye (to end our chat)");
    }

    /**
     * Deactivate the Katsu application.
     * Saves tasks to storage, sets active as false, and prints
     * "Quack. Hope to see you again soon!".
     */
    public void deactivate() {
        try {
            this.storage.save(this.list);
        } catch (IOException e) {
            System.out.println(UI.INDENT + "Error while saving file.");
        }

        this.active = false;
        System.out.println(UI.INDENT + "Quack. Hope to see you again soon!");
    }

    /**
     * Print all task in the task list if not empty
     * else will print "Quack! Your task list is empty.".
     **/
    public void printList() {
        if (!this.list.isEmpty()) {
            System.out.println(UI.INDENT + "Here is all of your task, Quack!");
            this.list.printList();
        } else {
            System.out.println(UI.INDENT + "Quack! Your task list is empty.");
        }
    }

    /**
     * Adds a task to the task list.
     * The type of task is determined by the <code>TaskType</code> parameter.
     *
     * @param words Array of user input words for the task.
     * @param type Type of task to add (TODO, DEADLINE, EVENT).
     */
    public void addTask(String[] words, TaskType type) {
        switch (type) {
            case TODO:
                this.addToDo(words);
                break;
            case DEADLINE:
                this.addDeadline(words);
                break;
            case EVENT:
                this.addEvent(words);
                break;
        }
    }

    /**
     * Adds a task of type TODO to the task list.
     *
     * @param words Array of user input words for the task.
     */
    public void addToDo(String[] words) {
        // join words from index 1 until array length to get task
        String newTask = String.join(" ", Arrays.stream(words).skip(1).toArray(String[]::new));

        if (newTask.isEmpty()) {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the todo's description.");
            return;
        }

        this.list.add(new ToDo(newTask), false);
    }

    /**
     * Adds a task of type DEADLINE to the task list.
     *
     * @param words Array of user input words for the task.
     */
    public void addDeadline(String[] words) {
        String newTask, newDeadline;
        int newTaskUntil = Parser.findWord(words, "/by", -1);

        newTask = (newTaskUntil == -1)
                ? String.join(" ", Arrays.copyOfRange(words, 1, words.length))
                : String.join(" ", Arrays.copyOfRange(words, 1, newTaskUntil));

        if (newTask.isEmpty()) {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the deadline's description.");
            return;
        }

        if (newTaskUntil == -1 || newTaskUntil + 1 >= words.length) {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the deadline.");
            System.out.println(UI.INDENT + "(use '/by' followed by the deadline).");
            return;
        }

        newDeadline = String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, words.length));
        LocalDateTime deadline = stringToDateTimeConverter(newDeadline);

        this.list.add(new Deadline(newTask, deadline), false);
    }

    /**
     * Adds a task of type EVENT to the task list.
     *
     * @param words Array of user input words for the task.
     */
    public void addEvent(String[] words) {
        String newTask, newStartTime, newEndTime;

        int newTaskUntil = Parser.findWord(words, "/from", -1);

        newTask = (newTaskUntil == -1)
                ? String.join(" ", Arrays.copyOfRange(words, 1, words.length))
                : String.join(" ", Arrays.copyOfRange(words, 1, newTaskUntil));

        if (newTask.isEmpty()) {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's description.");
            return;
        }

        if (newTaskUntil == -1) {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's starting time.");
            System.out.println(UI.INDENT + "(use '/from' followed by the start time).");
            return;
        }

        int newStartUntil;

        if (newTaskUntil + 1 < words.length) {
            newStartUntil = Parser.findWord(words, "/to", newTaskUntil + 1);

            newStartTime = (newStartUntil == -1)
                    ? String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, words.length))
                    : String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, newStartUntil));

            if (newStartTime.isEmpty()) {
                System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's starting time.");
                System.out.println(UI.INDENT + "(use '/from' followed by the start time).");
                return;
            }

            if (newStartUntil == -1) {
                System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's ending time.");
                System.out.println(UI.INDENT + "(use '/to' followed by the end time).");
                return;
            }
        } else {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's starting time.");
            System.out.println(UI.INDENT + "(use '/from' followed by the start time).");
            return;
        }

        if (newStartUntil + 1 < words.length) {
            newEndTime = String.join(" ", Arrays.copyOfRange(words, newStartUntil + 1, words.length));
        } else {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's ending time.");
            System.out.println(UI.INDENT + "(use '/to' followed by the end time).");
            return;
        }

        LocalDate startTime = stringToDateConverter(newStartTime);
        LocalDate endTime = stringToDateConverter(newEndTime);

        this.list.add(new Event(newTask, startTime, endTime), false);
    }

    /**
     * Handle marking task in the task list.
     * Either mark or unmark the task as done.
     *
     * @param command Either "mark" or "unmark".
     * @param words Array of user input words for the task.
     */
    public void handleMarking(String command, String[] words) {
        try {
            String taskNum = words[1];
            if (Objects.equals(command, "mark")) {
                this.list.markCompleted(taskNum);
            } else {
                this.list.markUncompleted(taskNum);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "⚠ Quack! You forgot the task number.");
        } catch (NumberFormatException e) {
            System.out.println(UI.INDENT + "⚠ Quack! That does not look like a number... •᷄ɞ•");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "⚠ Quack! You do not have that task number.");
        }
    }

    /**
     * Handle deletion of task in the task list.
     *
     * @param words Array of user input words for the task.
     */
    public void handleDelete(String[] words) {
        try {
            String taskNum = words[1];
            this.list.deleteTask(taskNum);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "⚠ Quack! You forgot the task number.");
        } catch (NumberFormatException e) {
            System.out.println(UI.INDENT + "⚠ Quack! That does not look like a number... •᷄ɞ•");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "⚠ Quack! You do not have that task number.");
        }
    }

    public void handleFind(String[] words) {
        try {
            this.list.hasKeyword(words[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "⚠ Quack! What do you want to find?");
        }
    }
}
