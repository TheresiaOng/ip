package katsu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import katsu.parser.Parser;
import katsu.storage.Storage;
import katsu.tasks.CustomList;
import katsu.tasks.Deadline;
import katsu.tasks.Event;
import katsu.tasks.ToDo;
import katsu.ui.Ui;
import katsu.util.DateUtils;

/**
 * Main class for Katsu the Duck application.
 * Handles task management, user interface, and storage operations.
 * The <code>Katsu</code> object runs the main program loop and executes user commands.
 */
public class Katsu {
    public static final String NAME = "Katsu the Duck";

    private CustomList tasks;
    private Storage storage;

    /**
     * Constructs a new <code>Katsu</code> object.
     * Initializes task tasks, storage, and Ui components.
     * The application is initially inactive.
     */
    public Katsu() {
        this.tasks = new CustomList();
        this.storage = new Storage();
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
     * Starts the Katsu application.
     * Loads tasks from storage, prints the welcome message, and listens for user input
     * until the application is deactivated.
     */
    public void run() {
        try {
            this.tasks = this.storage.loadSave();
        } catch (FileNotFoundException e) {
            System.out.println(Ui.INDENT + "No save file found.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Ui.INDENT + "Wrong task format in save file.");
        }
    }


    /**
     * Prints all available commands to the user.
     */
    public String printAllCommands() {
        StringBuilder katsuResponse = new StringBuilder();

        katsuResponse.append("Here are some commands you could use ꒰ঌ( •ө• )໒꒱:\n");
        katsuResponse.append("1. lists (to show all of your tasks)\n");
        katsuResponse.append("2. todo <description> (to add a todo to your task list)\n");
        katsuResponse.append("3. deadline <description> /by <yyyy-MM-dd HH:mm>"
                + "(to add a deadline to your task list)\n");
        katsuResponse.append("4. event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>"
                + "(to add an event to your task list)\n");
        katsuResponse.append("5. mark <task number> (to mark a task as completed)\n");
        katsuResponse.append("6. unmark <task number> (to unmark a completed task)\n");
        katsuResponse.append("7. find <description> (to list all task with matching description)\n");
        katsuResponse.append("8. delete <task number> (to delete a task from your list)\n");
        katsuResponse.append("9. bye (to end our chat)");

        return katsuResponse.toString();
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
     * Adds a task of type TODO to the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public String addToDo(String... words) {
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
    public String addDeadline(String... words) {
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
        LocalDateTime deadline = DateUtils.convertStringToDateTime(newDeadline);

        return this.tasks.add(new Deadline(newTask, deadline), false);
    }

    /**
     * Adds a task of type EVENT to the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public String addEvent(String... words) {
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

        LocalDateTime startDate = DateUtils.convertStringToDateTime(newStartTime);
        LocalDateTime endDate = DateUtils.convertStringToDateTime(newEndTime);

        return this.tasks.add(new Event(newTask, startDate, endDate), false);
    }

    /**
     * Handle marking task in the task tasks.
     * Either mark or unmark the task as done.
     *
     * @param command Either "mark" or "unmark".
     * @param words Array of user input words for the task.
     */
    public String handleMarking(String command, String... words) {
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
    public String handleDelete(String... words) {
        try {
            String taskNum = words[1];
            return this.tasks.deleteTask(taskNum);
        } catch (ArrayIndexOutOfBoundsException e) {
            return "⚠ Quack! You forgot the task number.";
        } catch (NumberFormatException e) {
            return "⚠ Quack! That does not look like a number... •᷄ɞ•";
        } catch (IndexOutOfBoundsException e) {
            return "⚠ Quack! You do not have that task number.";
        }
    }

    /**
     * Handles searching for tasks containing a specific keyword.
     *
     * @param words array of user input words containing the search keyword
     */
    public String handleFind(String... words) {
        try {
            return this.tasks.findKeyword(words[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return "⚠ Quack! What do you want to find?";
        }
    }

    public String handleSort(String... words) {
        try {
            if (words[1].equalsIgnoreCase("earliest")) {
                return this.tasks.sortEarliest();
            } else if (words[1].equalsIgnoreCase("latest")) {
                return this.tasks.sortLatest();
            } else {
                return "⚠ Quack! Which way do you want to sort? (earliest/latest)";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return "⚠ Quack! Which way do you want to sort? (earliest/latest)";
        }
    }
}
