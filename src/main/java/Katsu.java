import java.util.Objects;
import java.util.Scanner;

public class Katsu {
    public static final String INDENT = "    ";

    public static void main(String[] args) {
        CustomList list = new CustomList();

        boolean active = true;
        String name = "Katsu the Duck";
        String separator = Katsu.INDENT + "____________________________________________________________";
        String logo = Katsu.INDENT + " _  __     _             \n"
                + Katsu.INDENT + "| |/ /__ _| |_ ___ _   _ \n"
                + Katsu.INDENT + "| ' // _` | __/ __| | | |\n"
                + Katsu.INDENT + "| . \\ (_| | |_\\__ \\ |_| |\n"
                + Katsu.INDENT + "|_|\\_\\__,_|\\__|___/\\__,_|\n";

        System.out.println(separator);
        System.out.println(logo);
        System.out.println(Katsu.INDENT + "Hello! I'm " + name);
        System.out.println(Katsu.INDENT + "What can I do for you?");
        System.out.println(separator +"\n");

        Scanner scanner = new Scanner(System.in);
        while (active) {
            String order = scanner.nextLine();

            if (order.isEmpty()) {
                System.out.println(separator);
                System.out.println(Katsu.INDENT + "Quack! You need to write something!\n");
                System.out.println(Katsu.INDENT + "Here is some commands to help you!");
                System.out.println(Katsu.INDENT + "1. list (to show all of your tasks)");
                System.out.println(Katsu.INDENT + "2. bye (to end our chat)");
                System.out.println(separator + "\n");
                continue;
            }

            String[] words = order.split(" ");
            System.out.println(separator);

            if (Objects.equals(words[0], "bye")) {
                System.out.println(Katsu.INDENT + "Quack. Hope to see you again soon!");
                active = false;
            } else if (Objects.equals(words[0], "list")) {
                list.printList();
            } else if (Objects.equals(words[0], "mark")) {
                list.markCompleted(words[1]);
            } else if (Objects.equals(words[0], "unmark")) {
                list.markUncompleted(words[1]);
            } else {
                list.add(order);
            }

            System.out.println(separator + "\n");
        }
    }
}
