package katsu.tasks;

import java.util.ArrayList;
import java.util.List;

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
                katsuResponse.append("You now have ").append(size).append(" katsu.tasks in the list.");
            }

            return katsuResponse.toString();
        }
    }

    /**
     * Marks a task as completed based on its position in the list.
     *
     * @param id the string representation of the task number (1-based index)
     */
    public String markCompleted(String id) {
        int index = Integer.parseInt(id) - 1;
        StringBuilder response = new StringBuilder();

        Task currTask = this.list.get(index);
        currTask.markCompleted();

        response.append("Quack! I have  marked this task as done:\n");
        response.append(currTask.printTask());
        return response.toString();
    }

    /**
     * Marks a task as uncompleted based on its position in the list.
     *
     * @param id the string representation of the task number (1-based index)
     */
    public String markUncompleted(String id) {
        int index = Integer.parseInt(id) - 1;
        StringBuilder response = new StringBuilder();

        Task currTask = this.list.get(index);
        currTask.markUncompleted();

        response.append("Quack! I have  marked this task as not done yet:\n");
        response.append(currTask.printTask());
        return response.toString();
    }

    /**
     * Removes a task from the list based on its position and provides user feedback.
     *
     * @param id the string representation of the task number (1-based index)
     */
    public String deleteTask(String id) {
        int index = Integer.parseInt(id) - 1;
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
        StringBuilder allTasksInList = new StringBuilder();

        for (int i = 0; i < size; i++) {
            int index = i + 1;
            allTasksInList.append(index).append(". ").append(this.list.get(i).printTask()).append("\n");
        }

        return allTasksInList.toString();
    }

    /**
     * Searches for tasks containing a specific keyword and displays matching results.
     *
     * @param word the keyword to search for in task descriptions
     */
    public String findKeyword(String word) {
        int size = this.list.size();
        CustomList newList = new CustomList();
        StringBuilder response = new StringBuilder();

        for (int i = 0; i < size; i++) {
            Task curr = this.list.get(i);

            if (curr.hasKeyword(word)) {
                newList.add(curr, true);
            }
        }

        if (newList.isEmpty()) {
            return "Quack! No task description matches.";
        } else {
            response.append("Quack! Here are the matching tasks in your list:\n");
            response.append(newList.printList());
            return response.toString();
        }
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
