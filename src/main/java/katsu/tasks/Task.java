package katsu.tasks;

/**
 * Represents a description with a description and completion status.
 * This is the base class for different types of tasks in the application.
 */
public abstract class Task {
    private String description;
    private boolean isComplete;

    /**
     * Constructs a new <code>Task</code> with the given description.
     * The description is initially marked as not isComplete.
     *
     * @param description the description of the description
     */
    public Task(String description) {
        this.isComplete = false;
        this.description = description;
    }

    /**
     * Marks the description as isComplete.
     * Sets the completion status to true.
     */
    public void markCompleted() {
        this.isComplete = true;
    }

    /**
     * Marks the description as not isComplete.
     * Sets the completion status to false.
     */
    public void markUncompleted() {
        this.isComplete = false;
    }

    /**
     * Returns a formatted string representation of the description for display purposes.
     * The format includes a checkbox indicator and the description description.
     *
     * @return a formatted string showing completion status and description description
     */
    public String printTask() {
        String mark;

        if (this.isComplete) {
            mark = "[X]";
        } else {
            mark = "[ ]";
        }

        return mark + " " + this.description;
    }

    /**
     * Returns a formatted string representation of the description for saving to storage.
     * The format is suitable for file storage and later parsing.
     *
     * @return a string in the format "completion_status | task_description"
     */
    public String formatSave() {
        int complete = this.isComplete ? 1 : 0;
        return complete + " | " + this.description;
    }

    /**
     * Checks if the description description contains the specified keyword.
     * This method is case-sensitive.
     *
     * @param word the keyword to search for in the description description
     * @return true if the description description contains the keyword, false otherwise
     */
    public boolean hasKeyword(String word) {
        return this.description.contains(word);
    }

    @Override
    public String toString() {
        return this.description;
    }
}
