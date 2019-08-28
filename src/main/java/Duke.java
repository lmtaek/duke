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
            } else if (userInput.toLowerCase().equals("list")) {
                System.out.println(readTaskList());
            } else if (userInput.toLowerCase().contains("done ")){
                System.out.println(markCompletedRequest(userInput));
            } else if (userInput.toLowerCase().contains("todo ")) {
                System.out.println(addTodo(userInput));
            } else if (userInput.toLowerCase().contains("deadline ")) {
                System.out.println(addDeadline(userInput));
            } else if (userInput.toLowerCase().contains("event ")) {
                System.out.println(addEvent(userInput));
            }
            continue;
        }

    }

    static void closeApplication() {
        System.out.println("\tBye. Hope to see you again soon!\n");
        System.exit(0);
    }

    static String readTaskList() {
        String response = "\tHere are the tasks in your list:\n\n";

        for (int i=0; i<listLength; i++) {

            response = response
                    + "\t\t"
                    + (i+1)
                    + ". "
                    + taskTypeLabel(taskList[i])
                    + taskCompletionStatus(taskList[i].isTaskDone())
                    + taskList[i].getTaskName()
                    + taskList[i].getTime()
                    + "\n";
        }
        return response;
    }

    static String taskTypeLabel(Task task) {
        String taskLabel;
        if (task.taskType.equals(Task.TaskType.TODO)) {
            taskLabel = "[T] ";
        } else if (task.taskType.equals(Task.TaskType.DEADLINE)) {
            taskLabel = "[D] ";
        } else if (task.taskType.equals(Task.TaskType.EVENT)) {
            taskLabel = "[E] ";
        }
        else {
            taskLabel = "";
        }
        return taskLabel;
    }

    static String addTodo(String userInput) {
        userInput.trim();
        String taskName = userInput.replaceFirst("todo ", "");
        if (taskName.equals("")) {
            return "\tYou need to specify what the task is!";
        }
        ToDo newTask = new ToDo(taskName);
        return addToList(newTask);
    }

    static String addDeadline(String userInput) {
        userInput.trim();
        String task = userInput.replaceFirst("deadline ", "");
        String[] taskComponents = task.split(" /by ");

        if (taskComponents.length <= 1) {
            return "\tPlease provide more details for your deadline.";
        }

        String taskName = taskComponents[0];
        String taskDeadline = taskComponents[1];

        Deadline newTask = new Deadline(taskName, taskDeadline);
        return addToList(newTask);
    }

    static String addEvent(String userInput) {
        userInput.trim();
        String task = userInput.replaceFirst("event ", "");
        String[] taskComponents = task.split(" /at ");

        if (taskComponents.length <= 1) {
            return "\tPlease provide more details for your event.";
        }

        String taskName = taskComponents[0];
        String taskTime = taskComponents[1];

        Event newTask = new Event(taskName, taskTime);
        return addToList(newTask);
    }

    static String addToList(Task task) {
        if (listLength >= 100) {
            return "\tYou can't add any more tasks to your list!";
        }
        else {
            taskList[listLength] = task;
            listLength++;

            String output = "\n\tGot it. I've added this task: "
                    + "\n\t\t"
                    + taskTypeLabel(task)
                    + taskCompletionStatus(task.isTaskDone())
                    + task.getTaskName()
                    + task.getTime()
                    + "\n\t Now you have " + listLength + " task(s) in the list.";
            return output;

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
                + taskTypeLabel(task)
                + taskCompletionStatus(task.isTaskDone())
                + task.getTaskName()
                + task.getTime()
                + "\n";
    }
}
