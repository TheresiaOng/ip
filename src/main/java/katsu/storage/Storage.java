package katsu.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import katsu.tasks.CustomList;
import katsu.tasks.Deadline;
import katsu.tasks.Event;
import katsu.tasks.ToDo;
import katsu.ui.Ui;
import katsu.util.DateUtils;

/**
 * Handles loading and saving of task data to persistent storage.
 * Manages file operations for reading from and writing to the save file.
 */
public class Storage {
    private String path;

    /**
     * Constructs a Storage object with the default save file path.
     */
    public Storage() {
        this.path = "data/katsuSave.txt";
    }

    /**
     * Loads task data from the save file and reconstructs the task list.
     *
     * @return a CustomList containing all loaded tasks
     * @throws FileNotFoundException if the save file does not exist
     */
    public CustomList loadSave() throws FileNotFoundException {
        System.out.println(Ui.INDENT + "Loading save file...");

        File save = new File(this.path);
        Scanner scanner = new Scanner(save);
        CustomList tasks = new CustomList();
        int index = 0;

        while (scanner.hasNext()) {
            String currLine = scanner.nextLine();
            String[] taskDetails = currLine.split("\\s*\\|\\s*");

            switch (taskDetails[0]) {
            case "T":
                tasks.add(new ToDo(taskDetails[2]), true);
                break;
            case "D":
                LocalDateTime dueDate = DateUtils.convertStringToDateTime(taskDetails[3]);
                tasks.add(new Deadline(taskDetails[2], dueDate), true);
                break;
            case "E":
                LocalDate startDate = DateUtils.convertStringToDate(taskDetails[3]);
                LocalDate endDate = DateUtils.convertStringToDate(taskDetails[4]);
                tasks.add(new Event(taskDetails[2], startDate, endDate), true);
                break;
            default:
                System.out.println(Ui.INDENT + "⚠ Unknown task type in save file: " + taskDetails[0]);
                break;
            }

            if (taskDetails[1].equals("1")) {
                tasks.markCompleted(String.valueOf(index + 1));
            }

            index++;
        }

        System.out.println(Ui.INDENT + "Save file loaded.");
        return tasks;
    }

    /**
     * Saves the current task list to the save file for persistent storage.
     *
     * @param data the CustomList containing tasks to be saved
     * @throws java.io.IOException if an I/O error occurs during file writing
     */
    public void save(CustomList data) throws IOException {
        System.out.println(Ui.INDENT + "Saving katsu.tasks...");

        File save = new File("data/katsuSave.txt");
        save.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(save);
        int size = data.size();
        StringBuilder taskDetails = new StringBuilder();

        for (int i = 0; i < size; i++) {
            taskDetails.append(data.formatSave(i));
            taskDetails.append("\n");
        }

        fw.write(taskDetails.toString());
        fw.close();
        System.out.println(Ui.INDENT + "Saved successfully.");
    }
}
