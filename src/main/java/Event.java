import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    private static final String label = "[E]";
    private LocalDate startTime;
    private LocalDate endTime;

    public Event(String task, LocalDate startTime, LocalDate endTime) {
        super(task);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return date.format(formatter);
    }

    public String printTask() {
        return label + super.printTask() + " (from: " + this.dateToString(startTime) + " to: " + this.dateToString(endTime) + ")";
    }

    public String formatSave() {
        return "E | " + super.formatSave() + " | " + this.startTime + " | " + this.endTime;
    }
}
