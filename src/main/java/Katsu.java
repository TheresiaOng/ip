import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Katsu {
    public static final String INDENT = "    ";
    public static final String NAME = "Katsu the Duck";
    public static final String SEPARATOR = Katsu.INDENT + "____________________________________________________________";
    private boolean active;
    CustomList list;

    public static void main(String[] args) {
        Katsu katsu = new Katsu();
        katsu.startingText();

        Scanner scanner = new Scanner(System.in);

        while (katsu.active) {
            String order = scanner.nextLine();

            if (order.isEmpty()) {
                System.out.println(Katsu.SEPARATOR);
                System.out.println(Katsu.INDENT + "Quack! You need to write something!\n");
                System.out.println(Katsu.INDENT + "Here is some commands to help you:");
                katsu.allCommands();
                System.out.println(Katsu.SEPARATOR + "\n");
                continue;
            }

            // split words by empty space
            String[] words = order.split(" ");
            System.out.println(Katsu.SEPARATOR);

            if (Objects.equals(words[0], "bye")) {
                katsu.diactivate();
            } else if (Objects.equals(words[0], "list") || Objects.equals(words[0], "ls")) {
                katsu.printList();
            } else if (Objects.equals(words[0], "mark") || (Objects.equals(words[0], "unmark"))) {
                String command = words[0];
                katsu.handleMarking(command, words);
            } else if (Objects.equals(words[0], "todo")) {
                katsu.addToDo(words);
            } else if ((Objects.equals(words[0], "deadline"))) {
                katsu.addDeadline(words);
            } else {
                System.out.println(Katsu.INDENT + "Quack! I'm not sure what you mean.");
            }

            System.out.println(Katsu.SEPARATOR + "\n");
        }
    }

    public Katsu() {
        this.active = true;
        this.list = new CustomList();
    }

    public void diactivate() {
        this.active = false;
        System.out.println(Katsu.INDENT + "Quack. Hope to see you again soon!");
    }

    public void printList() {
        this.list.printList();
    }

    public void addToDo(String[] words) {
        // join words from index 1 until array length to get task
        String newTask = String.join(" ", Arrays.stream(words).skip(1).toArray(String[]::new));

        if (newTask.isEmpty()) {
            System.out.println(Katsu.INDENT + "Quack! What todo would you like to add?");
            return;
        }

        this.list.add(new ToDo(newTask));
    }

    public void addDeadline(String[] words) {
        String newTask, newDeadline;
        int newTaskUntil = findTask(words);

        newTask = (newTaskUntil == -1)
                ? String.join(" ", Arrays.copyOfRange(words, 1, words.length))
                : String.join(" ", Arrays.copyOfRange(words, 1, newTaskUntil));

        if (newTask.isEmpty()) {
            System.out.println(Katsu.INDENT + "Quack! What deadline would you like to add?");
            return;
        }

        if (newTaskUntil == -1) {
            System.out.println(Katsu.INDENT + "Quack! Did you forget the deadline? (use /by followed by the deadline)");
            return;
        }

        if (newTaskUntil + 1 < words.length) {
            newDeadline = String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, words.length));
        } else {
            System.out.println(Katsu.INDENT + "Quack! Did you forget the deadline? (use /by followed by the deadline)");
            return;
        }

        this.list.add(new Deadline(newTask, newDeadline));
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

    public void handleMarking(String command, String[] words) {
        try {
            String taskNum = words[1];
            if (Objects.equals(command, "mark")) {
                this.list.markCompleted(taskNum);
            } else {
                this.list.markUncompleted(taskNum);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Katsu.INDENT + "Quack! Did you forget to give me a task number?");
        } catch (NumberFormatException e) {
            System.out.println(Katsu.INDENT + "Quack! That does not look like a number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(Katsu.INDENT + "Quack! Looks like you do not have that task number.");
        }
    }

    public int findTask(String[] words) {
        int stopIndex = -1;

        for (int i = 1; i < words.length; i++) {
            if (words[i].equals("/by")) {
                stopIndex = i;
                break;
            }
        }

         return stopIndex;
    }

    public int findBy(String[] words, int startIndex) {
        int stopIndex = -1;

        for (int i = startIndex; i < words.length; i++) {
            if (words[i].equals("/to")) {
                stopIndex = i;
                break;
            }
        }

        return stopIndex;
    }
}
