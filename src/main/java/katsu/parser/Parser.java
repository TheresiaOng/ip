package katsu.parser;

import katsu.Katsu;
import katsu.response.ErrorResponse;
import katsu.response.KatsuResponse;

/**
 * Handles parsing and processing of user commands for the Katsu application.
 * Translates user input into appropriate actions for the bot.
 */
public class Parser {

    /**
     * Processes and executes the user command by delegating to appropriate bot methods.
     *
     * @param order the full command string entered by the user
     * @param bot the Katsu bot instance to execute commands on
     */
    public static KatsuResponse handleCommand(String order, Katsu bot) {
        if (order.isBlank()) {
            return null;
        }

        String[] words = order.strip().split(" ");

        return switch (words[0].toLowerCase()) {
        case "help" -> bot.printAllCommands();
        case "list", "ls" -> bot.printList();
        case "todo", "td" -> bot.addToDo(words);
        case "deadline", "dl" -> bot.addDeadline(words);
        case "event", "e" -> bot.addEvent(words);
        case "mark", "unmark" -> bot.handleMarking(words[0], words);
        case "find", "f" -> bot.handleFind(words);
        case "sort" -> bot.handleSort(words);
        case "delete", "del" -> bot.handleDelete(words);
        case "bye" -> bot.deactivate();
        default -> new ErrorResponse(order, "Quack, I don't know what that is... •᷄ɞ•");
        };
    }

    /**
     * Searches for a specific word in an array of words starting from a given index.
     *
     * @param words the array of words to search through
     * @param word the target word to find
     * @param startFrom the starting index for the search (-1 to start from index 1)
     * @return the index of the found word, or -1 if not found
     */
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
