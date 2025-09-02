package katsu.tasks;

/**
 * Represents a todo task without any date or time constraints.
 * Extends the base Task class for basic task functionality.
 */
public class ToDo extends Task {
    private static final String label = "[T]";

    /**
     * Constructs a new <code>ToDo</code> object.
     *
     * @param task the description of the todo task
     */
    public ToDo(String task) {
        super(task);
    }

    /**
     * Returns a formatted string representation of the todo task for display purposes.
     * Includes the todo label along with the completion status and task description.
     *
     * @return a formatted string showing the todo task details
     */
    public String printTask() {
        return label + super.printTask();
    }

    /**
     * Returns a formatted string representation of the todo task for saving to storage.
     * Uses a simple format suitable for file storage and later parsing.
     *
     * @return a string in the format "T | completion_status | task_description"
     */
    public String formatSave() {
        return "T | " + super.formatSave();
    }
}
