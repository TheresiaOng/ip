import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private static final String label = "[D]";
    private LocalDate deadline;

    public Deadline(String task, LocalDate deadline) {
        super(task);
        this.deadline = deadline;
    }

    public String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return date.format(formatter);
    }

    public String printTask() {
        return label + super.printTask() + " (by: " + this.dateToString(deadline) + ")";
    }

    public String formatSave() {
        return "D | " + super.formatSave() + " | " + this.deadline;
    }
}
