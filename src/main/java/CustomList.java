import java.util.ArrayList;
import java.util.List;

public class CustomList {
    List<Task> list;

    public CustomList() {
        this.list = new ArrayList<>();
    }

    public void add(String task) {
        Task newTask = new Task(task);
        this.list.add(newTask);

        System.out.println(Katsu.INDENT + "added: " + task);
    }

    public void markCompleted(String num) {
        int index = Integer.parseInt(num) - 1;

        Task currTask = this.list.get(index);
        currTask.markCompleted();

        System.out.println(Katsu.INDENT + "Quack! I have  marked this task as done:");
        System.out.println(Katsu.INDENT + "  " + currTask.printTask());
    }

    public void markUncompleted(String num) {
        int index = Integer.parseInt(num) - 1;

        Task currTask = this.list.get(index);
        currTask.markUncompleted();

        System.out.println(Katsu.INDENT + "Quack! I have  marked this task as not done yet:");
        System.out.println(Katsu.INDENT + "  " + currTask.printTask());
    }

    public void printList() {
        int size = this.list.size();

        System.out.println(Katsu.INDENT + "Here is all of your task, Quack!");
        for(int i = 0; i < size; i++) {
            int index = i + 1;
            System.out.println(Katsu.INDENT + index + "." + this.list.get(i).printTask());
        }
    }
}
