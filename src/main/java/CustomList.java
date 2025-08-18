import java.util.ArrayList;
import java.util.List;

public class CustomList {
    List<String> list;

    public CustomList() {
        this.list = new ArrayList<>();
    }

    public void add(String task) {
        this.list.add(task);
        System.out.println(Katsu.INDENT + "added: " + task);
    }

    public void printList() {
        int size = this.list.size();

        System.out.println(Katsu.INDENT + "Here is all of your task, Quack!");
        for(int i = 0; i < size; i++) {
            int index = i + 1;
            System.out.println(Katsu.INDENT + index + ". " + this.list.get(i));
        }
    }
}
