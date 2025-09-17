package katsu.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import katsu.response.ErrorResponse;
import katsu.response.KatsuResponse;
import katsu.response.SuccessResponse;

/**
 * Represents a custom list implementation for managing Task objects.
 * Provides functionality to add, remove, mark, and search tasks with user feedback.
 */
public class CustomList {
    private List<Task> list;

    /**
     * Constructs a new empty <code>CustomList</code>.
     */
    public CustomList() {
        this.list = new ArrayList<>();
    }

    /**
     * Adds a task to the list with optional user feedback.
     *
     * @param task the task to be added to the list
     * @param isQuiet if true, suppresses user feedback messages; if false, displays addition confirmation
     */
    public String add(Task task, boolean isQuiet) {
        if (isQuiet) {
            this.list.add(task);
            return "";
        } else {
            this.list.add(task);
            int size = this.list.size();
            StringBuilder katsuResponse = new StringBuilder();

            katsuResponse.append("Quack! I've added the task below to your list:\n");
            katsuResponse.append(task.printTask()).append("\n");

            if (size == 1) {
                katsuResponse.append("You now have 1 task in the list.");
            } else {
                katsuResponse.append("You now have ").append(size).append(" tasks in the list.");
            }

            return katsuResponse.toString();
        }
    }

    /**
     * Marks a task as completed based on its position in the list.
     *
     * @param id the string representation of the task number (1-based index)
     */
    public KatsuResponse markCompleted(String id, String userInput) {
        int index;
        try {
            index = Integer.parseInt(id) - 1;
        } catch (NumberFormatException e) {
            return new ErrorResponse(userInput, "⚠ Quack! That does not look like a number... •᷄ɞ•");
        }

        if (index < 0 || index >= this.list.size()) {
            return new ErrorResponse(userInput, "⚠ Quack! You do not have that task number.");
        }

        Task currTask = this.list.get(index);

        if (currTask.isComplete()) {
            return new ErrorResponse(userInput,
                    "⚠ Quack! This task is already completed:\n"
                            + currTask.printTask()
                            + "\nPerhaps you want to use \"unmark\" or \"delete\" on this task?");
        }

        currTask.markCompleted();

        return new SuccessResponse(userInput,
                "Quack! I have marked this task as completed:\n"
                        + currTask.printTask());
    }


    /**
     * Marks a task as uncompleted based on its position in the list.
     *
     * @param id the string representation of the task number (1-based index)
     */
    public KatsuResponse markUncompleted(String id, String userInput) {
        int index;
        try {
            index = Integer.parseInt(id) - 1;
        } catch (NumberFormatException e) {
            return new ErrorResponse(userInput, "⚠ Quack! That does not look like a number... •᷄ɞ•");
        }

        if (index < 0 || index >= this.list.size()) {
            return new ErrorResponse(userInput, "⚠ Quack! You do not have that task number.");
        }

        Task currTask = this.list.get(index);

        if (!currTask.isComplete()) {
            return new ErrorResponse(userInput,
                    "⚠ Quack! This task is not completed yet:\n"
                            + currTask.printTask() + "\nPerhaps you want to use \"mark\" or \"delete\" on this task?");
        }

        currTask.markUncompleted();

        return new SuccessResponse(userInput,
                "Quack! I have marked this task as not done yet:\n"
                        + currTask.printTask());
    }


    /**
     * Removes a task from the list based on its position and provides user feedback.
     *
     * @param id the string representation of the task number (1-based index)
     */
    public String deleteTask(String id) {
        int index = Integer.parseInt(id) - 1;
        assert index >= 0 : "index should be >= 0";

        StringBuilder response = new StringBuilder();

        Task currTask = this.list.get(index);
        this.list.remove(index);
        int size = this.list.size();

        response.append("Quack! I've removed the task below from your list:\n");
        response.append(currTask.printTask()).append("\n");

        if (size == 0) {
            response.append("You have no more task in the list.");
        } else if (size == 1) {
            response.append("You now have 1 task in the list.");
        } else {
            response.append("You now have ").append(size).append(" tasks in the list.");
        }

        return response.toString();
    }

    /**
     * Displays all tasks in the list with their numbering and completion status.
     */
    public String printList() {
        int size = this.list.size();

        return IntStream.range(0, size)
                .mapToObj((index) -> (index + 1) + ". " + this.list.get(index).printTask())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Searches for tasks containing a specific keyword and displays matching results.
     *
     * @param words the keywords to search for in task descriptions
     */
    public String findKeyword(String... words) {
        if (this.list.isEmpty()) {
            return "Quack! You have no tasks in your list.";
        }

        Set<Task> matchedTasks = new LinkedHashSet<>(); // automatically removes duplicates

        Arrays.stream(words)
                .distinct()
                .forEach(word -> this.list.stream()
                        .filter(task -> task.hasKeyword(word))
                        .forEach(task -> matchedTasks.add(task)));

        if (matchedTasks.isEmpty()) {
            return "Quack! No task description matches.";
        }

        // Add unique tasks to the new list
        CustomList newList = new CustomList();
        matchedTasks.forEach(task -> newList.add(task, true));

        StringBuilder response = new StringBuilder();
        response.append("Quack! Here are the matching tasks in your list:\n");
        response.append(newList.printList());

        return response.toString();
    }


    /**
     * Sorts tasks from earliest to latest date and returns a formatted response.
     * Tasks that implement Schedulable are sorted by their comparable date,
     * while other tasks (like ToDos) are placed at the beginning.
     *
     * @return a formatted string showing tasks sorted from earliest to latest
     */
    public String sortEarliest() {
        if (this.list.isEmpty()) {
            return "Quack! You have no tasks in your list.";
        }

        StringBuilder response = new StringBuilder();

        this.list.sort(Comparator.comparing(
                task -> (task instanceof Schedulable)
                        ? ((Schedulable) task).getComparableDate()
                        : LocalDateTime.MIN));

        response.append("Quack! Here are your tasks sorted from the earliest:\n");
        response.append(this.printList());
        return response.toString();
    }

    /**
     * Sorts tasks from latest to earliest date and returns a formatted response.
     * Tasks that implement Schedulable are sorted by their comparable date in reverse order,
     * while other tasks (like ToDos) are placed at the end.
     *
     * @return a formatted string showing tasks sorted from latest to earliest
     */
    public String sortLatest() {
        if (this.list.isEmpty()) {
            return "Quack! You have no tasks in your list.";
        }

        StringBuilder response = new StringBuilder();

        this.list.sort(Comparator.comparing(
                task -> (task instanceof Schedulable)
                        ? ((Schedulable) task).getComparableDate()
                        : LocalDateTime.MIN, Comparator.reverseOrder()));

        response.append("Quack! Here are your tasks sorted from the latest:\n");
        response.append(this.printList());
        return response.toString();
    }

    /**
     * Returns the formatted save string for a specific task in the list.
     *
     * @param index the index of the task to format (0-based index)
     * @return the formatted string representation of the task for storage
     */
    public String formatSave(int index) {
        return this.list.get(index).formatSave();
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks in the list
     */
    public int size() {
        return this.list.size();
    }
}
