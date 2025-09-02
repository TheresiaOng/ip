package katsu.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 * Extends the base Task class to include date and time information.
 */
public class Deadline extends Task {
    private static final String label = "[D]";
    private LocalDateTime deadline;

    /**
     * Constructs a new <code>Deadline</code>> task with the given description and deadline.
     *
     * @param task the description of the deadline task
     * @param deadline the date and time by which the task should be completed
     */
    public Deadline(String task, LocalDateTime deadline) {
        super(task);
        this.deadline = deadline;
    }

    /**
     * Converts a <code>LocalDateTime</code>> object to a formatted string representation.
     * The format used is "MMM dd yyyy hh:mm a" (e.g., "Jan 15 2024 02:30 PM").
     *
     * @param date the LocalDateTime object to format
     * @return a formatted string representation of the date and time
     */
    public String dateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
        return date.format(formatter);
    }

    /**
     * Returns a formatted string representation of the deadline task for display purposes.
     * Includes the deadline label, completion status, task description, and deadline date.
     *
     * @return a formatted string showing the deadline task details
     */
    public String printTask() {
        return label + super.printTask() + " (by: " + this.dateToString(deadline) + ")";
    }

    /**
     * Returns a formatted string representation of the deadline task for saving to storage.
     * Uses a machine-readable date format suitable for later parsing.
     *
     * @return a string in the format "D | completion_status | task_description | yyyy-MM-dd HH:mm"
     */
    public String formatSave() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "D | " + super.formatSave() + " | " + this.deadline.format(formatter);
    }
}
