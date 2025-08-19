import java.util.ArrayList;
import java.util.List;

public class CustomList {
    private List<Task> list;

    public CustomList() {
        this.list = new ArrayList<>();
    }

    public void add(Task task) {
        this.list.add(task);
        int size = this.list.size();

        System.out.println(Katsu.INDENT + "Quack! I've added the task below to your list:");
        System.out.println(Katsu.INDENT + "  " + task.printTask());

        if (size == 1) {
            System.out.println(Katsu.INDENT + "You now have 1 task in the list.");
        } else {
            System.out.println(Katsu.INDENT + "You now have " + size + " tasks in the list.");
        }
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

    public void deleteTask(String num) {
        int index = Integer.parseInt(num) - 1;

        Task currTask = this.list.get(index);
        this.list.remove(index);
        int size = this.list.size();

        System.out.println(Katsu.INDENT + "Quack! I've removed the task below from your list:");
        System.out.println(Katsu.INDENT + "  " + currTask.printTask());

        if (size == 0) {
            System.out.println(Katsu.INDENT + "You have no more task in the list.");
        } else if (size == 1){
            System.out.println(Katsu.INDENT + "You now have 1 task in the list.");
        } else {
            System.out.println(Katsu.INDENT + "You now have " + size + " tasks in the list.");
        }
    }

    public void printList() {
        int size = this.list.size();

        System.out.println(Katsu.INDENT + "Here is all of your task, Quack!");
        for(int i = 0; i < size; i++) {
            int index = i + 1;
            System.out.println(Katsu.INDENT + index + "." + this.list.get(i).printTask());
        }
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }
}
