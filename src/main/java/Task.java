public class Task {
    private boolean completed;
    private String task;

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

    @Override
    public String toString() {
        return this.task;
    }
}
