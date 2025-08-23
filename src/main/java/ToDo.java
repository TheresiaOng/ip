public class ToDo extends Task{
    private static final String label = "[T]";

    public ToDo(String task) {
        super(task);
    }

    public String printTask() {
        return label + super.printTask();
    }

    public String taskType() {
        return "T";
    }

    public String formatSave() {
        return "T | " + super.formatSave();
    }
}
