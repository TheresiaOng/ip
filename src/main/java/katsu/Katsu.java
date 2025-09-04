package katsu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import katsu.parser.Parser;
import katsu.storage.Storage;
import katsu.tasks.CustomList;
import katsu.tasks.Deadline;
import katsu.tasks.Event;
import katsu.tasks.ToDo;
import katsu.ui.MainWindow;
import katsu.ui.Ui;

/**
 * Main class for Katsu the Duck application.
 * Handles task management, user interface, and storage operations.
 * The <code>Katsu</code> object runs the main program loop and executes user commands.
 */
public class Katsu {
    public static final String NAME = "Katsu the Duck";

    private boolean active;
    private CustomList tasks;
    private Storage storage;
    private Ui ui;

    /**
     * Constructs a new <code>Katsu</code> object.
     * Initializes task tasks, storage, and Ui components.
     * The application is initially inactive.
     */
    public Katsu() {
        this.active = false;
        this.tasks = new CustomList();
        this.storage = new Storage();
        this.ui = new Ui();
    }

    /**
     * Starting point of the program.
     * Creates a new <code>Katsu</code> object and run it.
     */
    public static void main(String[] args) {
        Katsu katsu = new Katsu();
        katsu.run();
    }

    /**
     * Represents the different types of tasks supported by the application.
     *
     * The available task types are:
     * <code>TODO</code> - A simple task without any date/time constraints</li>
     * <code>DEADLINE</code> - A task with a specific due date and time</li>
     * <code>EVENT</code> - A task with a start and end date</li>
     */
    public enum TaskType {
        TODO, DEADLINE, EVENT
    }

    /**
     * Starts the Katsu application.
     * Loads tasks from storage, prints the welcome message, and listens for user input
     * until the application is deactivated.
     */
    public void run() {
        this.active = true;
        try {
            this.tasks = this.storage.loadSave();
        } catch (FileNotFoundException e) {
            System.out.println(Ui.INDENT + "No save file found.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Ui.INDENT + "Wrong task format in save file.");
        }

//        this.ui.printStartingText();
//        Scanner scanner = new Scanner(System.in);
//
//        while (this.active) {
//            String order = scanner.nextLine();
//            Parser.handleCommand(order, this);
//        }
    }

    /**
     * Converts a date string into a <code>LocalDate</code> object.
     *
     * @param dateString The date in "yyyy-MM-dd" format.
     * @return <code>LocalDate</code> representing the input string.
     */
    public static LocalDate convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * Converts a date and time string into a <code>LocalDateTime</code> object.
     *
     * @param dateTimeString The date in "yyyy-MM-dd HH:mm" format.
     * @return <code>LocalDateTime</code> representing the input string.
     */
    public static LocalDateTime convertStringToDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /**
     * Prints all available commands to the user.
     */
    public static void printAllCommands() {
        System.out.println(Ui.INDENT + "1. lists / ls (to show all of your katsu.tasks)");
        System.out.println(Ui.INDENT + "2. bye (to end our chat)");
    }

    /**
     * Deactivates the Katsu application.
     * Saves tasks to storage, sets active status to false, and prints farewell message.
     */
    public String deactivate() {
        StringBuilder katsuResponse = new StringBuilder();

        try {
            this.storage.save(this.tasks);
        } catch (IOException e) {
            katsuResponse.append("Error while saving file.\n");
            katsuResponse.append(("Please try again later."));
            return katsuResponse.toString();
        }

        this.active = false;
        return "exit_application";

    }

    /**
     * Prints all tasks in the task tasks if not empty,
     * otherwise prints a message indicating the tasks is empty.
     */
    public String printList() {
        StringBuilder allTasks = new StringBuilder();

        if (!this.tasks.isEmpty()) {
            allTasks.append("Here is all of your task, Quack!\n");
            allTasks.append(this.tasks.printList());
        } else {
            allTasks.append("Quack! Your task list is empty.");
        }
        return allTasks.toString();
    }

    /**
     * Adds a task to the task tasks.
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
        default:
            System.out.println(Ui.INDENT + "⚠ Unknown task type: " + type);
            break;
        }
    }

    /**
     * Adds a task of type TODO to the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public String addToDo(String[] words) {
        // join words from index 1 until array length to get task
        String newTask = String.join(" ", Arrays.stream(words).skip(1).toArray(String[]::new));

        if (newTask.isEmpty()) {
            return "⚠ Quack! You're missing the todo's description.";
        }

        return this.tasks.add(new ToDo(newTask), false);
    }

    /**
     * Adds a task of type DEADLINE to the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public String addDeadline(String[] words) {
        String newTask;
        String newDeadline;
        int byPosition = Parser.findWord(words, "/by", -1);
        StringBuilder katsuResponse = new StringBuilder();

        newTask = (byPosition == -1)
                ? String.join(" ", Arrays.copyOfRange(words, 1, words.length))
                : String.join(" ", Arrays.copyOfRange(words, 1, byPosition));

        if (newTask.isEmpty()) {
            katsuResponse.append("⚠ Quack! You're missing the deadline's description.");
            return katsuResponse.toString();
        }

        if (byPosition == -1 || byPosition + 1 >= words.length) {
            katsuResponse.append("⚠ Quack! You're missing the deadline.\n");
            katsuResponse.append("(use '/by' followed by the deadline).");
            return katsuResponse.toString();
        }

        newDeadline = String.join(" ", Arrays.copyOfRange(words, byPosition + 1, words.length));
        LocalDateTime deadline = convertStringToDateTime(newDeadline);

        return this.tasks.add(new Deadline(newTask, deadline), false);
    }

    /**
     * Adds a task of type EVENT to the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public String addEvent(String[] words) {
        String newTask;
        String newStartTime;
        String newEndTime;
        StringBuilder katsuResponse = new StringBuilder();

        int fromPosition = Parser.findWord(words, "/from", -1);

        newTask = (fromPosition == -1)
                ? String.join(" ", Arrays.copyOfRange(words, 1, words.length))
                : String.join(" ", Arrays.copyOfRange(words, 1, fromPosition));

        if (newTask.isEmpty()) {
            katsuResponse.append("⚠ Quack! You're missing the event's description.\n");
            return katsuResponse.toString();
        }

        if (fromPosition == -1) {
            katsuResponse.append("⚠ Quack! You're missing the event's starting time.\n");
            katsuResponse.append("(use '/from' followed by the start time).");
            return katsuResponse.toString();
        }

        int toPosition;

        if (fromPosition + 1 < words.length) {
            toPosition = Parser.findWord(words, "/to", fromPosition + 1);

            newStartTime = (toPosition == -1)
                    ? String.join(" ", Arrays.copyOfRange(words, fromPosition + 1, words.length))
                    : String.join(" ", Arrays.copyOfRange(words, fromPosition + 1, toPosition));

            if (newStartTime.isEmpty()) {
                katsuResponse.append("⚠ Quack! You're missing the event's starting time.\n");
                katsuResponse.append("(use '/from' followed by the start time).");
                return katsuResponse.toString();
            }

            if (toPosition == -1) {
                katsuResponse.append("⚠ Quack! You're missing the event's ending time.\n");
                katsuResponse.append("(use '/to' followed by the end time).");
                return katsuResponse.toString();
            }
        } else {
            katsuResponse.append("⚠ Quack! You're missing the event's starting time.\n");
            katsuResponse.append("(use '/from' followed by the start time).");
            return katsuResponse.toString();
        }

        if (toPosition + 1 < words.length) {
            newEndTime = String.join(" ", Arrays.copyOfRange(words, toPosition + 1, words.length));
        } else {
            katsuResponse.append("⚠ Quack! You're missing the event's ending time.\n");
            katsuResponse.append("(use '/to' followed by the end time).");
            return katsuResponse.toString();
        }

        LocalDate startDate = convertStringToDate(newStartTime);
        LocalDate endDate = convertStringToDate(newEndTime);

        return this.tasks.add(new Event(newTask, startDate, endDate), false);
    }

    /**
     * Handle marking task in the task tasks.
     * Either mark or unmark the task as done.
     *
     * @param command Either "mark" or "unmark".
     * @param words Array of user input words for the task.
     */
    public String handleMarking(String command, String[] words) {
        try {
            String taskNum = words[1];
            if (Objects.equals(command, "mark")) {
                return this.tasks.markCompleted(taskNum);
            } else {
                return this.tasks.markUncompleted(taskNum);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return "⚠ Quack! You forgot the task number.";
        } catch (NumberFormatException e) {
            return "⚠ Quack! That does not look like a number... •᷄ɞ•";
        } catch (IndexOutOfBoundsException e) {
            return "⚠ Quack! You do not have that task number.";
        }
    }

    /**
     * Handle deletion of task in the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public void handleDelete(String[] words) {
        try {
            String taskNum = words[1];
            this.tasks.deleteTask(taskNum);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Ui.INDENT + "⚠ Quack! You forgot the task number.");
        } catch (NumberFormatException e) {
            System.out.println(Ui.INDENT + "⚠ Quack! That does not look like a number... •᷄ɞ•");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(Ui.INDENT + "⚠ Quack! You do not have that task number.");
        }
    }

    /**
     * Handles searching for tasks containing a specific keyword.
     *
     * @param words array of user input words containing the search keyword
     */
    public String handleFind(String[] words) {
        try {
            return this.tasks.findKeyword(words[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return "⚠ Quack! What do you want to find?";
        }
    }
}
