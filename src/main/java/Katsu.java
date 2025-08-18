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
            System.out.println(separator);

            if (Objects.equals(order, "bye")) {
                System.out.println(Katsu.INDENT + "Quack. Hope to see you again soon!");
                active = false;
            } else if (Objects.equals(order, "list")) {
                list.printList();
            } else {
                list.add(order);
            }

            System.out.println(separator + "\n");
        }
    }
}
