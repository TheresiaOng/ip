public class Event extends Task{
    private static final String label = "[E]";
    private String startTime;
    private String endTime;

    public Event(String task, String startTime, String endTime) {
        super(task);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String printTask() {
        return label + super.printTask() + " (from: " + this.startTime + " to: " + this.endTime + ")";
    }

    public String formatSave() {
        return "E | " + super.formatSave() + " | " + this.startTime + " | " + this.endTime;
    }
}
