import java.util.Objects;
import java.util.Scanner;

public class Katsu {
    public static final String INDENT = "    ";
    public static final String NAME = "Katsu the Duck";
    public static final String SEPARATOR = Katsu.INDENT + "____________________________________________________________";
    private boolean active;

    public static void main(String[] args) {
        Katsu katsu = new Katsu();
        CustomList list = new CustomList();

        Scanner scanner = new Scanner(System.in);
        katsu.startingText();

        while (katsu.active) {
            String order = scanner.nextLine();

            if (order.isEmpty()) {
                System.out.println(Katsu.SEPARATOR);
                System.out.println(Katsu.INDENT + "Quack! You need to write something!\n");
                System.out.println(Katsu.INDENT + "Here is some commands to help you!");
                katsu.allCommands();
                System.out.println(Katsu.SEPARATOR + "\n");
                continue;
            }

            String[] words = order.split(" ");
            System.out.println(Katsu.SEPARATOR);

            if (Objects.equals(words[0], "bye")) {
                System.out.println(Katsu.INDENT + "Quack. Hope to see you again soon!");
                katsu.setActive(false);
            } else if (Objects.equals(words[0], "list") || Objects.equals(words[0], "ls")) {
                list.printList();
            } else if (Objects.equals(words[0], "mark")) {
                try {
                    list.markCompleted(words[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(Katsu.INDENT + "Quack! Did you forget to give me a task number?");
                } catch (NumberFormatException e) {
                    System.out.println(Katsu.INDENT + "Quack! That does not look like a number.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(Katsu.INDENT + "Quack! Looks like you do not have that task number.");
                }
            } else if (Objects.equals(words[0], "unmark")) {
                try {
                    list.markUncompleted(words[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(Katsu.INDENT + "Quack! Did you forget to give me a task number?");
                } catch (NumberFormatException e) {
                    System.out.println(Katsu.INDENT + "Quack! That does not look like a number.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(Katsu.INDENT + "Quack! Looks like you do not have that task number.");
                }
            } else {
                list.add(order);
            }

            System.out.println(Katsu.SEPARATOR + "\n");
        }
    }

    public Katsu() {
        this.active = true;
    }

    public void setActive(boolean b) {
        this.active = b;
    }

    public void startingText() {
        String logo = Katsu.INDENT + " _  __     _             \n"
                + Katsu.INDENT + "| |/ /__ _| |_ ___ _   _ \n"
                + Katsu.INDENT + "| ' // _` | __/ __| | | |\n"
                + Katsu.INDENT + "| . \\ (_| | |_\\__ \\ |_| |\n"
                + Katsu.INDENT + "|_|\\_\\__,_|\\__|___/\\__,_|\n";

        System.out.println(Katsu.SEPARATOR);
        System.out.println(logo);
        System.out.println(Katsu.INDENT + "Hello! I'm " + Katsu.NAME);
        System.out.println(Katsu.INDENT + "What can I do for you?");
        System.out.println(Katsu.SEPARATOR +"\n");
    }

    public void allCommands() {
        System.out.println(Katsu.INDENT + "1. list (to show all of your tasks)");
        System.out.println(Katsu.INDENT + "2. bye (to end our chat)");
    }
}
