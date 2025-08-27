package katsu;

import katsu.parser.Parser;
import katsu.storage.Storage;
import katsu.tasks.CustomList;
import katsu.tasks.Deadline;
import katsu.tasks.Event;
import katsu.tasks.ToDo;
import katsu.ui.UI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Katsu {
    public static final String NAME = "katsu.Katsu the Duck";
    private boolean active;
    private CustomList list;
    private Storage storage;
    private UI ui;

    public static void main(String[] args) {
        Katsu katsu = new Katsu();
        katsu.run();
    }

    public enum TaskType {
        TODO, DEADLINE, EVENT
    }

    public Katsu() {
        this.active = false;
        this.list = new CustomList();
        this.storage = new Storage();
        this.ui = new UI();
    }

    public void run() {
        this.active = true;
        try {
            this.list = this.storage.loadSave();
        } catch (FileNotFoundException e) {
            System.out.println(UI.INDENT + "No save file found.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "Wrong task format in save file.");
        }

        this.ui.startingText();
        Scanner scanner = new Scanner(System.in);

        while (this.active) {
            String order = scanner.nextLine();
            Parser.handleCommand(order, this);
        }
    }

    public static LocalDate stringToDateConverter(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public static LocalDateTime stringToDateTimeConverter (String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public static void allCommands() {
        System.out.println(UI.INDENT + "1. list / ls (to show all of your katsu.tasks)");
        System.out.println(UI.INDENT + "2. bye (to end our chat)");
    }

    public void deactivate() {
        try {
            this.storage.save(this.list);
        } catch (IOException e) {
            System.out.println(UI.INDENT + "Error while saving file.");
        }

        this.active = false;
        System.out.println(UI.INDENT + "Quack. Hope to see you again soon!");
    }

    public void printList() {
        if (!this.list.isEmpty()) {
            this.list.printList();
        } else {
            System.out.println(UI.INDENT + "Quack! Your task list is empty.");
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
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the todo's description.");
            return;
        }

        this.list.add(new ToDo(newTask));
    }

    public void addDeadline(String[] words) {
        String newTask, newDeadline;
        int newTaskUntil = Parser.findWord(words, "/by", -1);

        newTask = (newTaskUntil == -1)
                ? String.join(" ", Arrays.copyOfRange(words, 1, words.length))
                : String.join(" ", Arrays.copyOfRange(words, 1, newTaskUntil));

        if (newTask.isEmpty()) {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the deadline's description.");
            return;
        }

        if (newTaskUntil == -1 || newTaskUntil + 1 >= words.length) {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the deadline.");
            System.out.println(UI.INDENT + "(use '/by' followed by the deadline).");
            return;
        }

        newDeadline = String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, words.length));
        LocalDateTime deadline = stringToDateTimeConverter(newDeadline);

        this.list.add(new Deadline(newTask, deadline));
    }

    public void addEvent(String[] words) {
        String newTask, newStartTime, newEndTime;

        int newTaskUntil = Parser.findWord(words, "/from", -1);

        newTask = (newTaskUntil == -1)
                ? String.join(" ", Arrays.copyOfRange(words, 1, words.length))
                : String.join(" ", Arrays.copyOfRange(words, 1, newTaskUntil));

        if (newTask.isEmpty()) {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's description.");
            return;
        }

        if (newTaskUntil == -1) {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's starting time.");
            System.out.println(UI.INDENT + "(use '/from' followed by the start time).");
            return;
        }

        int newStartUntil;

        if (newTaskUntil + 1 < words.length) {
            newStartUntil = Parser.findWord(words, "/to", newTaskUntil + 1);

            newStartTime = (newStartUntil == -1)
                    ? String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, words.length))
                    : String.join(" ", Arrays.copyOfRange(words, newTaskUntil + 1, newStartUntil));

            if (newStartTime.isEmpty()) {
                System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's starting time.");
                System.out.println(UI.INDENT + "(use '/from' followed by the start time).");
                return;
            }

            if (newStartUntil == -1) {
                System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's ending time.");
                System.out.println(UI.INDENT + "(use '/to' followed by the end time).");
                return;
            }
        } else {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's starting time.");
            System.out.println(UI.INDENT + "(use '/from' followed by the start time).");
            return;
        }

        if (newStartUntil + 1 < words.length) {
            newEndTime = String.join(" ", Arrays.copyOfRange(words, newStartUntil + 1, words.length));
        } else {
            System.out.println(UI.INDENT + "⚠ Quack! You're missing the event's ending time.");
            System.out.println(UI.INDENT + "(use '/to' followed by the end time).");
            return;
        }

        LocalDate startTime = stringToDateConverter(newStartTime);
        LocalDate endTime = stringToDateConverter(newEndTime);

        this.list.add(new Event(newTask, startTime, endTime));
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
            System.out.println(UI.INDENT + "⚠ Quack! You forgot the task number.");
        } catch (NumberFormatException e) {
            System.out.println(UI.INDENT + "⚠ Quack! That does not look like a number... •᷄ɞ•");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "⚠ Quack! You do not have that task number.");
        }
    }

    public void handleDelete(String[] words) {
        try {
            String taskNum = words[1];
            this.list.deleteTask(taskNum);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "⚠ Quack! You forgot the task number.");
        } catch (NumberFormatException e) {
            System.out.println(UI.INDENT + "⚠ Quack! That does not look like a number... •᷄ɞ•");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(UI.INDENT + "⚠ Quack! You do not have that task number.");
        }
    }
}
