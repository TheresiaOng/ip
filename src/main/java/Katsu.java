import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
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
        katsu.run();
    }

    public enum TaskType {
        TODO, DEADLINE, EVENT
    }

    public Katsu() {
        this.active = false;

        if (this.list == null) {
            this.list = new CustomList();
        }
    }

    public void run() {
        this.active = true;
        try {
            System.out.println(Katsu.INDENT + "Loading save file...");
            this.loadSave();
            System.out.println(Katsu.INDENT + "Save file loaded.");
        } catch (FileNotFoundException e) {
            System.out.println(Katsu.INDENT + "No save file found.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Katsu.INDENT + "Wrong task format in save file.");
        }

        this.startingText();
        Scanner scanner = new Scanner(System.in);

        while (this.active) {
            String order = scanner.nextLine();
            this.handleCommand(order);
        }
    }

    public void handleCommand(String order) {
        if (order.isEmpty()) {
            System.out.println(Katsu.SEPARATOR);
            System.out.println(Katsu.INDENT + "Quack! You need to write something!\n");
            System.out.println(Katsu.INDENT + "Here is some commands to help you:");
            this.allCommands();
            System.out.println(Katsu.SEPARATOR + "\n");
            return;
        }

        // split words by empty space
        String[] words = order.split(" ");
        System.out.println(Katsu.SEPARATOR);

        switch (words[0]) {
            case "bye":
                this.diactivate();
                break;
            case "list":
            case "ls":
                this.printList();
                break;
            case "delete":
            case "del":
                this.handleDelete(words);
                break;
            case "mark":
            case "unmark":
                this.handleMarking(words[0], words);
                break;
            case "todo":
                this.addTask(words, TaskType.TODO);
                break;
            case "deadline":
                this.addTask(words, TaskType.DEADLINE);
                break;
            case "event":
                this.addTask(words, TaskType.EVENT);
                break;
            default:
                System.out.println(Katsu.INDENT + "Quack! Sorry, I'm not sure what you meant... `•᷄ɞ•᷅");
        }

        System.out.println(Katsu.SEPARATOR + "\n");
    }

    public void diactivate() {
        try {
            System.out.println(Katsu.INDENT + "Saving tasks...");
            this.save();
            System.out.println(Katsu.INDENT + "Saved successfully.");
        } catch (IOException e) {
            System.out.println(Katsu.INDENT + "Error while saving file.");
        }

        this.active = false;
        System.out.println(Katsu.INDENT + "Quack. Hope to see you again soon!");
    }

    public void printList() {
        if (!this.list.isEmpty()) {
            this.list.printList();
        } else {
            System.out.println(Katsu.INDENT + "Quack! Your task list is empty.");
        }
    }

    public void addTask(String[] words, TaskType type) {
        switch (type) {
            case TODO:
                this.addToDo(words);
                break;
            case DEADLINE:
                this.addDeadline(words);
                break;
            case EVENT:
                this.addEvent(words);
                break;
        }
    }

    public void addToDo(String[] words) {
        // join words from index 1 until array length to get task
        String newTask = String.join(" ", Arrays.stream(words).skip(1).toArray(String[]::new));

        if (newTask.isEmpty()) {
            System.out.println(Katsu.INDENT + "⚠ Quack! You're missing the todo's description.");
            return;
        }

        this.list.add(new ToDo(newTask));
    }

    public void addDeadline(String[] words) {
        String newTask, newDeadline;
        int newTaskUntil = findWord(words, "/by", -1);

        newTask = (newTaskUntil == -1)
                ? String.join(" ", Arrays.copyOfRange(words, 1, words.length))
                : String.join(" ", Arrays.copyOfRange(words, 1, newTaskUntil));

        if (newTask.isEmpty()) {
            System.out.println(Katsu.INDENT + "⚠ Quack! You're missing the deadline's description.");
            return;
        }

        if (newTaskUntil == -1 || newTaskUntil + 1 >= words.length) {
            System.out.println(Katsu.INDENT + "⚠ Quack! You're missing the deadline.");
            System.out.println(Katsu.INDENT + "(use '/by' followed by the deadline).");
            return;
        }

        newDeadline = String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, words.length));

        this.list.add(new Deadline(newTask, newDeadline));
    }

    public void addEvent(String[] words) {
        String newTask, newStartTime, newEndTime;

        int newTaskUntil = findWord(words, "/from", -1);

        newTask = (newTaskUntil == -1)
                ? String.join(" ", Arrays.copyOfRange(words, 1, words.length))
                : String.join(" ", Arrays.copyOfRange(words, 1, newTaskUntil));

        if (newTask.isEmpty()) {
            System.out.println(Katsu.INDENT + "⚠ Quack! You're missing the event's description.");
            return;
        }

        if (newTaskUntil == -1) {
            System.out.println(Katsu.INDENT + "⚠ Quack! You're missing the event's starting time.");
            System.out.println(Katsu.INDENT + "(use '/from' followed by the start time).");
            return;
        }

        int newStartUntil;

        if (newTaskUntil + 1 < words.length) {
            newStartUntil = findWord(words, "/to", newTaskUntil + 1);

            newStartTime = (newStartUntil == -1)
                    ? String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, words.length))
                    : String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, newStartUntil));

            if (newStartTime.isEmpty()) {
                System.out.println(Katsu.INDENT + "⚠ Quack! You're missing the event's starting time.");
                System.out.println(Katsu.INDENT + "(use '/from' followed by the start time).");
                return;
            }

            if (newStartUntil == -1) {
                System.out.println(Katsu.INDENT + "⚠ Quack! You're missing the event's ending time.");
                System.out.println(Katsu.INDENT + "(use '/to' followed by the end time).");
                return;
            }
        } else {
            System.out.println(Katsu.INDENT + "⚠ Quack! You're missing the event's starting time.");
            System.out.println(Katsu.INDENT + "(use '/from' followed by the start time).");
            return;
        }

        if (newStartUntil + 1 < words.length) {
            newEndTime = String.join(" ", Arrays.copyOfRange(words, newStartUntil + 1, words.length));
        } else {
            System.out.println(Katsu.INDENT + "⚠ Quack! You're missing the event's ending time.");
            System.out.println(Katsu.INDENT + "(use '/to' followed by the end time).");
            return;
        }

        this.list.add(new Event(newTask, newStartTime, newEndTime));
    }

    public void startingText() {
        String logo = Katsu.INDENT + " _  __     _\n"
                + Katsu.INDENT + "| |/ /__ _| |_ ___ _   _\n"
                + Katsu.INDENT + "| ' // _` | __/ __| | | |\n"
                + Katsu.INDENT + "| . \\ (_| | |_\\__ \\ |_| |\n"
                + Katsu.INDENT + "|_|\\_\\__,_|\\__|___/\\__,_|\n";

        System.out.println(Katsu.SEPARATOR);
        System.out.println(logo);
        System.out.println(Katsu.INDENT + "Hello! I'm " + Katsu.NAME + " ꒰ঌ( •ө• )໒꒱");
        System.out.println(Katsu.INDENT + "What can I do for you?");
        System.out.println(Katsu.SEPARATOR +"\n");
    }

    public void allCommands() {
        System.out.println(Katsu.INDENT + "1. list / ls (to show all of your tasks)");
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
            System.out.println(Katsu.INDENT + "⚠ Quack! You forgot the task number.");
        } catch (NumberFormatException e) {
            System.out.println(Katsu.INDENT + "⚠ Quack! That does not look like a number... •᷄ɞ•");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(Katsu.INDENT + "⚠ Quack! You do not have that task number.");
        }
    }

    public void handleDelete(String[] words) {
        try {
            String taskNum = words[1];
            this.list.deleteTask(taskNum);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Katsu.INDENT + "⚠ Quack! You forgot the task number.");
        } catch (NumberFormatException e) {
            System.out.println(Katsu.INDENT + "⚠ Quack! That does not look like a number... •᷄ɞ•");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(Katsu.INDENT + "⚠ Quack! You do not have that task number.");
        }
    }

    public int findWord(String[] words, String word, int startFrom) {
        int stopIndex = -1;
        int index = (startFrom == -1) ? 1 : startFrom;

        for (int i = index; i < words.length; i++) {
            if (words[i].equals(word)) {
                stopIndex = i;
                break;
            }
        }

         return stopIndex;
    }

    public void loadSave() throws FileNotFoundException {
        File save = new File("data/katsuSave.txt");
        Scanner scanner = new Scanner(save);
        this.list = new CustomList();
        int index = 0;

        while (scanner.hasNext()) {
            String currLine = scanner.nextLine();
            String[] taskDetails = currLine.split("\\s*\\|\\s*");

            switch (taskDetails[0]) {
                case "T":
                    this.list.add(new ToDo(taskDetails[2]));
                    break;
                case "D":
                    this.list.add(new Deadline(taskDetails[2], taskDetails[3]));
                    break;
                case "E":
                    this.list.add(new Event(taskDetails[2], taskDetails[3], taskDetails[4]));
                    break;
            }

            if (taskDetails[1].equals("1")) {
                this.list.markCompleted(String.valueOf(index + 1));
            }

            index++;
        }
    }

    public void save() throws java.io.IOException{
        File save = new File("data/katsuSave.txt");
        save.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(save);
        int size = this.list.size();
        StringBuilder taskdetails = new StringBuilder();

        for (int i = 0; i < size; i++) {
            taskdetails.append(this.list.formatSave(i));
            taskdetails.append("\n");
        }

        fw.write(taskdetails.toString());
        fw.close();
    }
}
