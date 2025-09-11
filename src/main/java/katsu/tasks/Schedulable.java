package katsu.tasks;

import java.time.LocalDateTime;

/**
 * Interface for tasks that can be scheduled and sorted by date.
 * Implementing classes must provide a date/time for chronological comparison.
 */
public interface Schedulable {
    /**
     * Returns the date and time used for scheduling and sorting comparisons.
     *
     * @return the LocalDateTime representing the task's scheduled time
     */
    LocalDateTime getComparableDate();
}
