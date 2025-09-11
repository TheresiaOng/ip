package katsu.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;

import katsu.util.DateUtils;

/**
 * Represents an event task with a specific start and end time.
 * Extends the base Task class to include date range information.
 */
public class Event extends Task implements Schedulable{
    private static final String LABEL = "[E]";
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Constructs a new <code>Event</code> object.
     *
     * @param task the description of the event task
     * @param startDate the starting time of the event
     * @param endDate the end time of the event
     */
    public Event(String task, LocalDateTime startDate, LocalDateTime endDate) {
        super(task);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns a formatted string representation of the event task for display purposes.
     * Includes the event label, completion status, task description, and date range.
     *
     * @return a formatted string showing the event task details
     */
    @Override
    public String printTask() {
        return LABEL + super.printTask() + " (from: " + DateUtils.convertDateTimeToString(startDate)
                + " to: " + DateUtils.convertDateTimeToString(endDate) + ")";
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

    public LocalDateTime getComparableDate() {
        return this.startDate;
    }
}
