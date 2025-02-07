/**
 * Class designated to take user input, and parse it into a format that can be used by the Ui.
 * This returned output is typically in the form of a String or integer, otherwise the class
 * will return an invalid output that is handled by the Ui accordingly.
 */
public class Parser implements ParseActionsInterface {

    private String input;
    private static String badFormat = "badFormat";

    /**
     * The constructor for the Parser class. It receives a specific user command and saves it to its input field
     * for future reference.
     * @param input
     */
    public Parser(String input) {
        this.input = input;
    }

    /**
     * This method detects keywords within the user's input, matches them to an enumerated action,
     * and returns the enumeration which tells the Ui class how to behave.
     * @return ActionType enumeration--one of nine instructions for the Ui.
     */
    public ParseActionsInterface.ActionType determineAction() {

        if (input.toLowerCase().equals("bye")) {
            return ActionType.EXIT;
        } else if (input.toLowerCase().equals("list")) {
            return ActionType.LIST;
        } else if (input.toLowerCase().contains("find")) {
            return ActionType.FIND;
        } else if (input.toLowerCase().contains("done")) {
            return ActionType.DONE;
        } else if (input.toLowerCase().contains("todo")) {
            return ActionType.TODO;
        } else if (input.toLowerCase().contains("deadline")) {
            return ActionType.DEADLINE;
        } else if (input.toLowerCase().contains("event")) {
            return ActionType.EVENT;
        } else if (input.toLowerCase().contains("delete")) {
            return ActionType.DELETE;
        }
        return ActionType.INVALID;
    }

    /**
     * A method to parse the user input when the command is specifically meant for finding a task.
     * @return the usable String to the Ui.
     */
    public String parseFind() {
        String userInput = input.trim();

        if (!userInput.contains("find ")) {
            return badFormat;
        } else {
            userInput = userInput.replaceFirst("find ", "").toLowerCase().trim();
        }
        if (userInput.isEmpty() || userInput.isBlank()) {
            return badFormat;
        }
        return userInput;
    }

    /**
     * A method to parse the user input when the command is specifically meant for deleting a task.
     * @return the usable integer as an index for the Ui.
     */
    public int parseDelete() {
        String userInput = input.trim();
        int index = -1;

        if (!userInput.contains("delete ")) {
            return index;
        }
        String task = userInput.replaceFirst("delete ", "").trim();
        if (task.isBlank() || task.isEmpty()) {
            return index;
        }
        try {
            index = Integer.parseInt(task);
        } catch (NumberFormatException e) {
            return index;
        }
        return index;
    }

    /**
     * A method to parse the user input when the command is specifically meant for marking a task as done.
     * @return the usable integer as an index for the Ui.
     */
    public int parseDone() {
        int badIndex = -1;
        String userInput = input.trim();

        if (!userInput.contains("done ")) {
            return badIndex;
        }

        String parsedInput[] = input.split(" ");
        if ((parsedInput.length <= 1)) {
            return badIndex;
        } else {
            try {
                Integer index = Integer.parseInt(parsedInput[1]);
                if (index <= 0
                        || index == null) {
                    return badIndex;
                }
                return index;
            } catch (NumberFormatException | NullPointerException nfe) {
                return badIndex;
            }
        }
    }

    /**
     * A method to parse the user input when the command is specifically meant for creating a ToDo task.
     * @return the usable String for the Ui.
     */
    public String parseToDo() {
        String userInput = input.trim();
        if (!userInput.contains("todo ")) {
            return badFormat;
        }
        String taskName = userInput.replace("todo ", "").trim();
        if (taskName.isEmpty() || taskName.isBlank()) {
            return badFormat;
        }
        return taskName;
    }

    /**
     * A method to parse the user input when the command is specifically meant for creating a Deadline task.
     * @return the usable String array for the Ui. The first element is the task name. The second is its time.
     */
    public String[] parseDeadline() {
        String[] badOutput = new String[0];
        String userInput = input.trim();
        if ((!userInput.contains("deadline ")) || (!userInput.contains(" /by "))) {
            return badOutput;
        }
        userInput = userInput.replaceFirst("deadline ", "").trim();
        String[] taskComponents = userInput.split(" /by ");
        for (int i = 0; i < taskComponents.length; i++) {
            taskComponents[i].trim();
        }

        if (taskComponents.length <= 1) {
            return badOutput;
        }

        String taskName = taskComponents[0];
        String taskDeadline = taskComponents[1];
        if ((taskName.isEmpty() || taskName.isBlank())
                || (taskDeadline.isEmpty() || taskDeadline.isBlank())) {
            return badOutput;
        }
        return taskComponents;
    }

    /**
     * A method to parse the user input when the command is specifically meant for creating an Event task.
     * @return the usable String array for the Ui. The first element is the task name. The second is its time.
     */
    public String[] parseEvent() {
        String[] badOutput = new String[0];
        String userInput = input.trim();
        if ((!userInput.contains("event ")) || (!userInput.contains(" /at "))) {
            return badOutput;
        }
        String task = userInput.replaceFirst("event ", "").trim();
        String[] taskComponents = task.split(" /at ");
        for (int i = 0; i < taskComponents.length; i++) {
            taskComponents[i].trim();
        }

        if (taskComponents.length <= 1) {
            return badOutput;
        }

        String taskName = taskComponents[0];
        String taskTime = taskComponents[1];
        if ((taskName.isEmpty() || taskName.isBlank())
                || (taskTime.isEmpty() || taskTime.isBlank())) {
            return badOutput;
        }
        return taskComponents;
    }
}
