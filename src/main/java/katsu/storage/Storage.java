package katsu.storage;
import katsu.Katsu;
import katsu.ui.UI;

import katsu.tasks.CustomList;
import katsu.tasks.Deadline;
import katsu.tasks.Event;
import katsu.tasks.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Storage {
    private String path;

    public Storage() {
        this.path = "data/katsuSave.txt";
    }
    public CustomList loadSave() throws FileNotFoundException {
        System.out.println(UI.INDENT + "Loading save file...");

        File save = new File(this.path);
        Scanner scanner = new Scanner(save);
        CustomList data = new CustomList();
        int index = 0;

        while (scanner.hasNext()) {
            String currLine = scanner.nextLine();
            String[] taskDetails = currLine.split("\\s*\\|\\s*");

            switch (taskDetails[0]) {
                case "T":
                    data.add(new ToDo(taskDetails[2]), true);
                    break;
                case "D":
                    LocalDateTime deadline = Katsu.stringToDateTimeConverter(taskDetails[3]);
                    data.add(new Deadline(taskDetails[2], deadline), true);
                    break;
                case "E":
                    LocalDate startTime = Katsu.stringToDateConverter(taskDetails[3]);
                    LocalDate endTime = Katsu.stringToDateConverter(taskDetails[4]);
                    data.add(new Event(taskDetails[2], startTime, endTime), true);
                    break;
            }

            if (taskDetails[1].equals("1")) {
                data.markCompleted(String.valueOf(index + 1));
            }

            index++;
        }

        System.out.println(UI.INDENT + "Save file loaded.");
        return data;
    }

    public void save(CustomList data) throws java.io.IOException{
        System.out.println(UI.INDENT + "Saving katsu.tasks...");

        File save = new File("data/katsuSave.txt");
        save.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(save);
        int size = data.size();
        StringBuilder taskdetails = new StringBuilder();

        for (int i = 0; i < size; i++) {
            taskdetails.append(data.formatSave(i));
            taskdetails.append("\n");
        }

        fw.write(taskdetails.toString());
        fw.close();
        System.out.println(UI.INDENT + "Saved successfully.");
    }
}
