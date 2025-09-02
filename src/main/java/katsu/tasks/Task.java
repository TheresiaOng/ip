package katsu.tasks;

/**
 * Represents a task with a description and completion status.
 * This is the base class for different types of tasks in the application.
 */
public class Task {
    private String task;
    private boolean completed;

    /**
     * Constructs a new <code>Task</code> with the given description.
     * The task is initially marked as not completed.
     *
     * @param task the description of the task
     */
    public Task(String task) {
        this.completed = false;
        this.task = task;
    }

    /**
     * Marks the task as completed.
     * Sets the completion status to true.
     */
    public void markCompleted() {
        this.completed = true;
    }

    /**
     * Marks the task as not completed.
     * Sets the completion status to false.
     */
    public void markUncompleted() {
        this.completed = false;
    }

    /**
     * Returns a formatted string representation of the task for display purposes.
     * The format includes a checkbox indicator and the task description.
     *
     * @return a formatted string showing completion status and task description
     */
    public String printTask() {
        String mark;

        if (this.completed) {
            mark = "[X]";
        } else {
            mark = "[ ]";
        }

        return mark + " " + this.task;
    }

    /**
     * Returns a formatted string representation of the task for saving to storage.
     * The format is suitable for file storage and later parsing.
     *
     * @return a string in the format "completion_status | task_description"
     */
    public String formatSave() {
        int complete = this.completed ? 1 : 0;
        return complete + " | " + this.task;
    }

    /**
     * Checks if the task description contains the specified keyword.
     * This method is case-sensitive.
     *
     * @param word the keyword to search for in the task description
     * @return true if the task description contains the keyword, false otherwise
     */
    public boolean hasKeyword(String word) {
        return this.task.contains(word);
    }

    @Override
    public String toString() {
        return this.task;
    }
}
