package katsu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Objects;

import katsu.parser.Parser;
import katsu.response.ErrorResponse;
import katsu.response.KatsuResponse;
import katsu.response.SuccessResponse;
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
    public KatsuResponse printAllCommands() {
        StringBuilder text = new StringBuilder();

        text.append("Here are some commands you could use ꒰ঌ( •ө• )໒꒱:\n");
        text.append("1. lists (to show all of your tasks)\n");
        text.append("2. todo <description> (to add a todo to your task list)\n");
        text.append("3. deadline <description> /by <yyyy-MM-dd HH:mm>"
                + "(to add a deadline to your task list)\n");
        text.append("4. event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>"
                + "(to add an event to your task list)\n");
        text.append("5. mark <task number> (to mark a task as completed)\n");
        text.append("6. unmark <task number> (to unmark a completed task)\n");
        text.append("7. find <description> (to list all task with matching description)\n");
        text.append("8. delete <task number> (to delete a task from your list)\n");
        text.append("9. bye (to end our chat)");

        return new SuccessResponse("", text.toString());
    }

    /**
     * Deactivates the Katsu application.
     * Saves tasks to storage, sets active status to false, and prints farewell message.
     */
    public KatsuResponse deactivate() {
        try {
            this.storage.save(this.tasks);
        } catch (IOException e) {
            String error = "Error while saving file.\nPlease try again later.";
            KatsuResponse katsuResponse = new ErrorResponse("", error);
            return katsuResponse;
        }

        return new SuccessResponse("", "exit_application");

    }

    /**
     * Prints all tasks in the task tasks if not empty,
     * otherwise prints a message indicating the tasks is empty.
     */
    public KatsuResponse printList() {
        StringBuilder allTasks = new StringBuilder();

        if (!this.tasks.isEmpty()) {
            allTasks.append("Here is all of your task, Quack!\n");
            allTasks.append(this.tasks.printList());
        } else {
            allTasks.append("Quack! Your task list is empty.");
        }
        return new SuccessResponse("", allTasks.toString());
    }

    /**
     * Adds a task of type TODO to the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public KatsuResponse addToDo(String... words) {
        String newTask = String.join(" ", Arrays.stream(words).skip(1).toArray(String[]::new))
                .replaceAll("\\s+", " ").trim();

        if (newTask.isEmpty()) {
            String userInput = String.join(" ", words).trim();
            return new ErrorResponse(userInput, "⚠ Quack! You're missing the todo's description.");
        }

        return new SuccessResponse("", this.tasks.add(new ToDo(newTask), false));
    }

    /**
     * Adds a task of type DEADLINE to the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public KatsuResponse addDeadline(String... words) {
        // Normalize redundant spaces in each word and rebuild
        String input = String.join(" ", words)
                .trim()
                .replaceAll("\\s+", " "); // collapse multiple spaces

        // Split normalized input back into array
        String[] cleanedWords = input.split(" ");

        String newTask;
        String newDeadline;
        int byPosition = Parser.findWord(cleanedWords, "/by", -1);

        newTask = (byPosition == -1)
                ? String.join(" ", Arrays.copyOfRange(cleanedWords, 1, cleanedWords.length))
                : String.join(" ", Arrays.copyOfRange(cleanedWords, 1, byPosition));

        if (newTask.isEmpty()) {
            return new ErrorResponse(input,
                    "⚠ Quack! You're missing the deadline's description.");
        }

        if (byPosition == -1 || byPosition + 1 >= cleanedWords.length) {
            return new ErrorResponse(input,
                    "⚠ Quack! You're missing the deadline.\n(use '/by' followed by the deadline).");
        }

        newDeadline = String.join(" ", Arrays.copyOfRange(cleanedWords, byPosition + 1, cleanedWords.length));

        try {
            LocalDateTime deadline = DateUtils.convertStringToDateTime(newDeadline);
            return new SuccessResponse("", this.tasks.add(new Deadline(newTask, deadline), false));
        } catch (DateTimeParseException e) {
            return new ErrorResponse(input,
                    "⚠ Quack! That does not look like a valid date... •᷄ɞ•\n"
                            + "Please use the format yyyy-MM-dd HH:mm");
        }
    }

    /**
     * Adds a task of type EVENT to the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public KatsuResponse addEvent(String... words) {
        String input = String.join(" ", words).trim().replaceAll("\\s+", " ");
        String[] cleanedWords = input.split(" ");

        int fromPos = Parser.findWord(cleanedWords, "/from", -1);
        int toPos = Parser.findWord(cleanedWords, "/to", fromPos + 1);

        // Extract task description
        String newTask = (fromPos == -1)
                ? String.join(" ", Arrays.copyOfRange(cleanedWords, 1, cleanedWords.length))
                : String.join(" ", Arrays.copyOfRange(cleanedWords, 1, fromPos));

        if (newTask.isEmpty()) {
            return new ErrorResponse(input,
                    "⚠ Quack! You're missing the event's description.\n");
        }

        if (fromPos == -1 || fromPos + 1 >= cleanedWords.length) {
            return new ErrorResponse(input,
                    "⚠ Quack! You're missing the event's starting time.\n(use '/from' followed by the start time).");
        }

        // Extract start time
        String newStartTime = (toPos == -1)
                ? String.join(" ", Arrays.copyOfRange(cleanedWords, fromPos + 1, cleanedWords.length))
                : String.join(" ", Arrays.copyOfRange(cleanedWords, fromPos + 1, toPos));

        if (newStartTime.isEmpty()) {
            return new ErrorResponse(input,
                    "⚠ Quack! You're missing the event's starting time.\n(use '/from' followed by the start time).");
        }

        if (toPos == -1 || toPos + 1 >= cleanedWords.length) {
            return new ErrorResponse(input,
                    "⚠ Quack! You're missing the event's ending time.\n(use '/to' followed by the end time).");
        }

        // Extract end time
        String newEndTime = String.join(" ", Arrays.copyOfRange(cleanedWords, toPos + 1, cleanedWords.length));

        if (newEndTime.isEmpty()) {
            return new ErrorResponse(input,
                    "⚠ Quack! You're missing the event's ending time.\n(use '/to' followed by the end time).");
        }

        try {
            // Convert strings to LocalDateTime
            LocalDateTime startDate = DateUtils.convertStringToDateTime(newStartTime);
            LocalDateTime endDate = DateUtils.convertStringToDateTime(newEndTime);

            // Add event and return success
            return new SuccessResponse("", this.tasks.add(new Event(newTask, startDate, endDate), false));
        } catch (DateTimeParseException e) {
            return new ErrorResponse(input,
                    "⚠ Quack! That does not look like a valid ate... •᷄ɞ•\n"
                            + "Please use the format yyyy-MM-dd HH:mm");
        }
    }

    /**
     * Handle marking task in the task tasks.
     * Either mark or unmark the task as done.
     *
     * @param command Either "mark" or "unmark".
     * @param words Array of user input words for the task.
     */
    public KatsuResponse handleMarking(String command, String... words) {
        String input = String.join(" ", words).trim().replaceAll("\\s+", " ");
        String[] cleanedWords = input.split(" ");

        try {
            String taskNum = cleanedWords[1];
            if (Objects.equals(command, "mark")) {
                return this.tasks.markCompleted(taskNum, input);
            } else {
                return this.tasks.markUncompleted(taskNum, input);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return new ErrorResponse(input, "⚠ Quack! You forgot the task number.");
        } catch (NumberFormatException e) {
            return new ErrorResponse(input, "⚠ Quack! That does not look like a number... •᷄ɞ•");
        } catch (IndexOutOfBoundsException e) {
            return new ErrorResponse(input, "⚠ Quack! You do not have that task number.");
        }
    }

    /**
     * Handle deletion of task in the task tasks.
     *
     * @param words Array of user input words for the task.
     */
    public KatsuResponse handleDelete(String... words) {
        String input = String.join(" ", words).trim().replaceAll("\\s+", " ");
        String[] cleanedWords = input.split(" ");

        try {
            String taskNum = cleanedWords[1];
            return new SuccessResponse("", this.tasks.deleteTask(taskNum));
        } catch (ArrayIndexOutOfBoundsException e) {
            return new ErrorResponse(input, "⚠ Quack! You forgot the task number.");
        } catch (NumberFormatException e) {
            return new ErrorResponse(input, "⚠ Quack! That does not look like a number... •᷄ɞ•");
        } catch (IndexOutOfBoundsException e) {
            return new ErrorResponse(input, "⚠ Quack! You do not have that task number.");
        }
    }

    /**
     * Handles searching for tasks containing a specific keyword.
     *
     * @param words array of user input words containing the search keyword
     */
    public KatsuResponse handleFind(String... words) {
        String input = String.join(" ", words).trim().replaceAll("\\s+", " ");
        String[] cleanedWords = input.split(" ");

        try {
            return new SuccessResponse("", this.tasks.findKeyword(cleanedWords));
        } catch (ArrayIndexOutOfBoundsException e) {
            return new ErrorResponse(input, "⚠ Quack! What do you want to find?");
        }
    }

    /**
     * Handles the sort command by delegating to appropriate sorting methods.
     * Supports sorting by "earliest" or "latest" criteria.
     *
     * @param words the command words containing the sort direction
     * @return a formatted string with sorted tasks or an error message
     */
    public KatsuResponse handleSort(String... words) {
        String input = String.join(" ", words).trim().replaceAll("\\s+", " ");
        String[] cleanedWords = input.split(" ");

        try {
            if (cleanedWords[1].equalsIgnoreCase("earliest")) {
                return new SuccessResponse("", this.tasks.sortEarliest());
            } else if (cleanedWords[1].equalsIgnoreCase("latest")) {
                return new SuccessResponse("", this.tasks.sortLatest());
            } else {
                return new ErrorResponse(input,
                        "⚠ Quack! Which way do you want to sort? (earliest/latest)");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return new ErrorResponse(input,
                    "⚠ Quack! Which way do you want to sort? (earliest/latest)");
        }
    }
}
