package katsu.tasks;

public class Task {
    private String task;

    private boolean completed;
    public Task(String task) {
        this.completed = false;
        this.task = task;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public void markUncompleted() {
        this.completed = false;
    }

    public String printTask() {
        String mark;

        if(this.completed) {
            mark = "[X]";
        } else {
            mark = "[ ]";
        }

        return mark + " " + this.task;
    }

    public String formatSave() {
        int complete = this.completed ? 1 : 0;
        return complete + " | " + this.task;
    }

    @Override
    public String toString() {
        return this.task;
    }
}
