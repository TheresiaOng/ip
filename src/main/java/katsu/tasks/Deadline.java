package katsu.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import katsu.util.DateUtils;

/**
 * Represents a task with a specific dueDate.
 * Extends the base Task class to include date and time information.
 */
public class Deadline extends Task {
    private static final String LABEL = "[D]";
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
     * Returns a formatted string representation of the dueDate task for display purposes.
     * Includes the dueDate label, completion status, task description, and dueDate date.
     *
     * @return a formatted string showing the dueDate task details
     */
    @Override
    public String printTask() {
        return LABEL + super.printTask() + " (by: " + DateUtils.convertDateTimeToString(dueDate) + ")";
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
