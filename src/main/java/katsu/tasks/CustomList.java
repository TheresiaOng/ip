package katsu.tasks;

import java.util.ArrayList;
import java.util.List;

import katsu.ui.Ui;

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
    public void add(Task task, boolean isQuiet) {
        if (isQuiet) {
            this.list.add(task);
        } else {
            this.list.add(task);
            int size = this.list.size();

            System.out.println(Ui.INDENT + "Quack! I've added the task below to your list:");
            System.out.println(Ui.INDENT + "  " + task.printTask());

            if (size == 1) {
                System.out.println(Ui.INDENT + "You now have 1 task in the list.");
            } else {
                System.out.println(Ui.INDENT + "You now have " + size + " katsu.tasks in the list.");
            }
        }
    }

    /**
     * Marks a task as completed based on its position in the list.
     *
     * @param id the string representation of the task number (1-based index)
     */
    public void markCompleted(String id) {
        int index = Integer.parseInt(id) - 1;

        Task currTask = this.list.get(index);
        currTask.markCompleted();

        System.out.println(Ui.INDENT + "Quack! I have  marked this task as done:");
        System.out.println(Ui.INDENT + "  " + currTask.printTask());
    }

    /**
     * Marks a task as uncompleted based on its position in the list.
     *
     * @param id the string representation of the task number (1-based index)
     */
    public void markUncompleted(String id) {
        int index = Integer.parseInt(id) - 1;

        Task currTask = this.list.get(index);
        currTask.markUncompleted();

        System.out.println(Ui.INDENT + "Quack! I have  marked this task as not done yet:");
        System.out.println(Ui.INDENT + "  " + currTask.printTask());
    }

    /**
     * Removes a task from the list based on its position and provides user feedback.
     *
     * @param id the string representation of the task number (1-based index)
     */
    public void deleteTask(String id) {
        int index = Integer.parseInt(id) - 1;

        Task currTask = this.list.get(index);
        this.list.remove(index);
        int size = this.list.size();

        System.out.println(Ui.INDENT + "Quack! I've removed the task below from your list:");
        System.out.println(Ui.INDENT + "  " + currTask.printTask());

        if (size == 0) {
            System.out.println(Ui.INDENT + "You have no more task in the list.");
        } else if (size == 1) {
            System.out.println(Ui.INDENT + "You now have 1 task in the list.");
        } else {
            System.out.println(Ui.INDENT + "You now have " + size + " tasks in the list.");
        }
    }

    /**
     * Displays all tasks in the list with their numbering and completion status.
     */
    public void printList() {
        int size = this.list.size();

        for (int i = 0; i < size; i++) {
            int index = i + 1;
            System.out.println(Ui.INDENT + index + "." + this.list.get(i).printTask());
        }
    }

    /**
     * Searches for tasks containing a specific keyword and displays matching results.
     *
     * @param word the keyword to search for in task descriptions
     */
    public void findKeyword(String word) {
        int size = this.list.size();
        CustomList newList = new CustomList();

        for (int i = 0; i < size; i++) {
            Task curr = this.list.get(i);

            if (curr.hasKeyword(word)) {
                newList.add(curr, true);
            }
        }

        if (newList.isEmpty()) {
            System.out.println(Ui.INDENT + "Quack! No task description matches.");
        } else {
            System.out.println(Ui.INDENT + "Quack! Here are the matching tasks in your list:");
            newList.printList();
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
