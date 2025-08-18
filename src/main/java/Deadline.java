public class Deadline extends Task {
    private static final String label = "[D]";
    private String deadline;

    public Deadline(String task, String deadline) {
        super(task);
        this.deadline = deadline;
    }

    public String printTask() {
        return label + super.printTask() + " (by: " + this.deadline + ")";
    }
}
