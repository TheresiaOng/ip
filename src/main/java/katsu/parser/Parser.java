package katsu.parser;
import katsu.ui.UI;
import katsu.Katsu;

public class Parser {
    public static void handleCommand(String order, Katsu bot) {
        if (order.isEmpty()) {
            System.out.println(UI.SEPARATOR);
            System.out.println(UI.INDENT + "Quack! You need to write something!\n");
            System.out.println(UI.INDENT + "Here is some commands to help you:");
            Katsu.allCommands();
            System.out.println(UI.SEPARATOR + "\n");
            return;
        }

        // split words by empty space
        String[] words = order.split(" ");
        System.out.println(UI.SEPARATOR);

        switch (words[0]) {
            case "bye":
                bot.deactivate();
                break;
            case "list":
            case "ls":
                bot.printList();
                break;
            case "delete":
            case "del":
                bot.handleDelete(words);
                break;
            case "mark":
            case "unmark":
                bot.handleMarking(words[0], words);
                break;
            case "todo":
                bot.addTask(words, Katsu.TaskType.TODO);
                break;
            case "deadline":
                bot.addTask(words, Katsu.TaskType.DEADLINE);
                break;
            case "event":
                bot.addTask(words, Katsu.TaskType.EVENT);
                break;
            case "find":
                bot.handleFind(words);
                break;
            default:
                System.out.println(UI.INDENT + "Quack! Sorry, I'm not sure what you meant... `•᷄ɞ•᷅");
        }

        System.out.println(UI.SEPARATOR + "\n");
    }

    public static int findWord(String[] words, String word, int startFrom) {
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
}
