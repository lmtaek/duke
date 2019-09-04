public class Parser implements ParseActionsInterface {

    private String input;
    private static String badFormat = "badFormat";

    public Parser(String input) {
        this.input = input;
    }

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
