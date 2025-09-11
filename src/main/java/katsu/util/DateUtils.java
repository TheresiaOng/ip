package katsu.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date and time formatting and conversion operations.
 * Provides static methods to convert between LocalDate/LocalDateTime objects
 * and formatted string representations for consistent date handling throughout the application.
 */
public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");

    /**
     * Converts a <code>LocalDate</code> object to a formatted string representation.
     * The format used is "MMM dd yyyy" (e.g., "Jan 15 2024").
     *
     * @param date the <code>LocalDate</code> object to format
     * @return a formatted string representation of the date
     */
    public static String convertDateToString(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Converts a <code>LocalDateTime</code>> object to a formatted string representation.
     * The format used is "MMM dd yyyy hh:mm a" (e.g., "Jan 15 2024 02:30 PM").
     *
     * @param date the LocalDateTime object to format
     * @return a formatted string representation of the date and time
     */
    public static String convertDateTimeToString(LocalDateTime date) {
        return date.format(DATE_TIME_FORMATTER);
    }

    /**
     * Converts a date string into a <code>LocalDate</code> object.
     *
     * @param dateString The date in "yyyy-MM-dd" format.
     * @return <code>LocalDate</code> representing the input string.
     */
    public static LocalDate convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * Converts a date and time string into a <code>LocalDateTime</code> object.
     *
     * @param dateTimeString The date in "yyyy-MM-dd HH:mm" format.
     * @return <code>LocalDateTime</code> representing the input string.
     */
    public static LocalDateTime convertStringToDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
