package katsu.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific dueDate.
 * Extends the base Task class to include date and time information.
 */
public class Deadline extends Task {
    private static final String label = "[D]";
    private LocalDateTime dueDate;

    /**
     * Constructs a new <code>Deadline</code>> task with the given description and dueDate.
     *
     * @param task the description of the dueDate task
     * @param dueDate the date and time by which the task should be completed
     */
    public Deadline(String task, LocalDateTime dueDate) {
        super(task);
        this.dueDate = dueDate;
    }

    /**
     * Converts a <code>LocalDateTime</code>> object to a formatted string representation.
     * The format used is "MMM dd yyyy hh:mm a" (e.g., "Jan 15 2024 02:30 PM").
     *
     * @param date the LocalDateTime object to format
     * @return a formatted string representation of the date and time
     */
    public String convertDateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
        return date.format(formatter);
    }

    /**
     * Returns a formatted string representation of the dueDate task for display purposes.
     * Includes the dueDate label, completion status, task description, and dueDate date.
     *
     * @return a formatted string showing the dueDate task details
     */
    @Override
    public String printTask() {
        return label + super.printTask() + " (by: " + this.convertDateToString(dueDate) + ")";
    }

    /**
     * Returns a formatted string representation of the dueDate task for saving to storage.
     * Uses a machine-readable date format suitable for later parsing.
     *
     * @return a string in the format "D | completion_status | task_description | yyyy-MM-dd HH:mm"
     */
    @Override
    public String formatSave() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "D | " + super.formatSave() + " | " + this.dueDate.format(formatter);
    }
}
