import java.util.Objects;
import java.util.Scanner;

public class Katsu {
    public static void main(String[] args) {
        boolean active = true;
        String name = "Katsu the Duck";
        String indent = "    ";
        String seperator = indent + "____________________________________________________________";
        String logo = indent + " _  __     _             \n"
                + indent + "| |/ /__ _| |_ ___ _   _ \n"
                + indent + "| ' // _` | __/ __| | | |\n"
                + indent + "| . \\ (_| | |_\\__ \\ |_| |\n"
                + indent + "|_|\\_\\__,_|\\__|___/\\__,_|\n";

        System.out.println(seperator);
        System.out.println(logo);
        System.out.println(indent + "Hello! I'm " + name);
        System.out.println(indent + "What can I do for you?");
        System.out.println(seperator +"\n");

        Scanner scanner = new Scanner(System.in);
        while (active) {
            String order = scanner.nextLine();
            System.out.println(seperator);

            if (Objects.equals(order, "bye")) {
                System.out.println(indent + "Quack. Hope to see you again soon!");
                active = false;
            } else {
                System.out.println(indent + "Quack " + order);
            }

            System.out.println(seperator + "\n");
        }
    }
}
