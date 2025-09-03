package katsu.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a specific start and end time.
 * Extends the base Task class to include date range information.
 */
public class Event extends Task {
    private static final String label = "[E]";
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructs a new <code>Event</code> object.
     *
     * @param task the description of the event task
     * @param startDate the starting time of the event
     * @param endDate the end time of the event
     */
    public Event(String task, LocalDate startDate, LocalDate endDate) {
        super(task);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Converts a <code>LocalDate</code> object to a formatted string representation.
     * The format used is "MMM dd yyyy" (e.g., "Jan 15 2024").
     *
     * @param date the <code>LocalDate</code> object to format
     * @return a formatted string representation of the date
     */
    public String convertDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return date.format(formatter);
    }

    /**
     * Returns a formatted string representation of the event task for display purposes.
     * Includes the event label, completion status, task description, and date range.
     *
     * @return a formatted string showing the event task details
     */
    @Override
    public String printTask() {
        return label + super.printTask() + " (from: " + this.convertDateToString(startDate)
                + " to: " + this.convertDateToString(endDate) + ")";
    }

    /**
     * Returns a formatted string representation of the event task for saving to storage.
     * Uses the default <code>LocalDate</code> toString format for machine-readable storage.
     *
     * @return a string in the format "E | completion_status | task_description | start_date | end_date"
     */
    @Override
    public String formatSave() {
        return "E | " + super.formatSave() + " | " + this.startDate + " | " + this.endDate;
    }
}
