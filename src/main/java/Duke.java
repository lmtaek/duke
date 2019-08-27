import java.util.Scanner;

public class Duke {

    private static String dukeGreeting = "Hello, I'm Duke.\nWhat can I do to help you?";
    private static Task[] taskList = new Task[100];
    private static int listLength = 0;


    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        System.out.println(dukeGreeting + "\r\n");

        // The user will enter commands from this point onwards.
        Scanner input = new Scanner(System.in);

        while (input.hasNextLine()) {
            String userInput = input.nextLine();

            if (userInput.toLowerCase().equals("bye")) {
                closeApplication();
            }
            else if (userInput.toLowerCase().equals("list")) {
                System.out.println(readTaskList());
            } else if (userInput.toLowerCase().contains("done ")){
                System.out.println(markCompletedRequest(userInput));
            }
            else {
                System.out.println(addToList(userInput));
            }
            continue;
        }

    }

    static void closeApplication() {
        System.out.println("\tBye. Hope to see you again soon!\n");
        System.exit(0);
    }

    static String readTaskList() {
        String response = "\tHere are the tasks in your list:\n";
        for (int i=0; i<listLength; i++) {
            response = response
                    + "\t"
                    + (i+1)
                    + ". "
                    + taskCompletionStatus(taskList[i].isTaskDone())
                    + taskList[i].getTaskName()
                    + "\n";
        }
        return response;
    }

    static String addToList(String input) {
        if (listLength >= 100) {
            return "\tYou can't add any more tasks to your list!";
        }
        else {
            taskList[listLength] = new Task(input);
            listLength++;
            return "\tadded: " + input;
        }
    }

    private static String taskCompletionStatus(Boolean isDone) {
        if (isDone) { return "[✓] ";}
        else { return "[✗] "; }
    }

    public static String markCompletedRequest(String input) {
        String couldNotFindTask = "\n\tI couldn't find the task.";
        String parsedInput[] = input.split(" ");
        if ((parsedInput.length <= 1)) {
            return couldNotFindTask;
        } else {
            try {
                Integer index = Integer.parseInt(parsedInput[1]);
                if (index <= 0
                        || index == null) {
                    return couldNotFindTask;
                }
                    index--;
                    return markTaskAsDone(taskList[index]);
                } catch (NumberFormatException | NullPointerException nfe) {
                    return couldNotFindTask;
            }
        }
    }

    public static String markTaskAsDone(Task task) {
        task.markAsDone();
        return "\n\tNice! I've marked this task as done:\n"
                + "\t\t"
                + taskCompletionStatus(task.isTaskDone())
                + task.getTaskName()
                + "\n";
    }
}
