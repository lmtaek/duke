import TaskPackage.*;

import java.io.IOException;
import java.util.Scanner;

/**
 * A class meant to react to the Duke user's input. It receives commands, sends them to the Parser class
 * to make the input more usable, then uses the returned input in order to respond in a specific manner to
 * the given command.
 */
public class Ui implements ParseActionsInterface {

    private Parser parser;
    private TaskList tasks;
    private Storage storage;

    private String dukeGreeting = "Hello, I'm Duke.\nWhat can I do to help you?";
    private String logo = " ____        _\n"
            + "|  _ \\ _   _| | _____\n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private static String dukeNeedsValidInput = "\tI'm not sure that I understand. Could you reformat your request?";
    private static String dukeGoodbye = "\tBye. Hope to see you again soon!\n";
    private static String badFormat = "badFormat";
    private Boolean isUserFinished = false;

    public Ui(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;

    }

    /**
     * A looping method that will continue taking input until the user types "bye". Each line of
     * input is sent to be parsed. Once it is returned, Ui determines what to do, and how to respond.
     */
    public void handleInput() {
        System.out.println("Hello from\n" + logo);
        System.out.println(dukeGreeting + "\n");

        // The user will enter commands from this point onwards.
        Scanner input = new Scanner(System.in);

        while (input.hasNextLine()) {

            String userInput = input.nextLine();
            this.parser = new Parser(userInput);
            ActionType action = parser.determineAction();

            switch(action) {
                case LIST:
                    System.out.println(readTaskList());
                    break;
                case FIND:
                    System.out.println(findTask(parser.parseFind()));
                    break;
                case DELETE:
                    System.out.println(deleteTask(parser.parseDelete()));
                    break;
                case DONE:
                    System.out.println(markTaskAsDone(parser.parseDone()));
                    break;
                case TODO:
                    System.out.println(addTodo(parser.parseToDo()));
                    break;
                case DEADLINE:
                    System.out.println(addDeadline(parser.parseDeadline()));
                    break;
                case EVENT:
                    System.out.println(addEvent(parser.parseEvent()));
                    break;
                case EXIT:
                    this.isUserFinished = true;
                    System.out.println(dukeGoodbye);
                    break;
                default:
                    System.out.println(dukeNeedsValidInput);
                    break;
            }
            if (isUserFinished) {
                break;
            }
        }
    }

    /* ACTIONS */

    private String readTaskList() {
        String response = "\tHere are the tasks in your list:\n";

        for (int i = 0; i< tasks.getNumberOfTasks(); i++) {
            response = response
                    + "\t\t"
                    + (i+1)
                    + ". "
                    + taskTypeLabel(tasks.getTaskAt(i))
                    + taskCompletionStatus(tasks.getTaskAt(i).isTaskDone())
                    + tasks.getTaskAt(i).getTaskName()
                    + getTimeOrDate(tasks.getTaskAt(i))
                    + "\n";
        }
        return response;
    }

    private String findTask(String userInput) {
        String response = "\tHere are the matching tasks in your list:\n";

        if (userInput.equals(badFormat)) {
            return dukeNeedsValidInput;
        }

        for (int i = 0; i < tasks.getNumberOfTasks(); i++) {
            if (tasks.getTaskAt(i).getTaskName().toLowerCase().contains(userInput)) {
                response = response
                        +"\t"
                        + (i+1)
                        + ". "
                        + taskTypeLabel(tasks.getTaskAt(i))
                        + taskCompletionStatus(tasks.getTaskAt(i).isTaskDone())
                        + tasks.getTaskAt(i).getTaskName()
                        + getTimeOrDate(tasks.getTaskAt(i))
                        + "\n";
            }
            else {
                continue;
            }
        }
        return response;
    }

    private String deleteTask(int index) {
        String response = "\tNoted. I've removed this task:\n";
        if ((index <= 0) || index > tasks.getNumberOfTasks()) {
            return dukeNeedsValidInput;
        } else {
            index--;
            Task removedTask = tasks.removeTaskAt(index);
            response = response
                    + "\t\t"
                    + (index+1)
                    + ". "
                    + taskTypeLabel(removedTask)
                    + taskCompletionStatus(removedTask.isTaskDone())
                    + removedTask.getTaskName()
                    + getTimeOrDate(removedTask)
                    + "\n"
                    + "\tNow you have " + tasks.getNumberOfTasks() + " task(s) in the list.";
        }
        updateFile();
        return response;
    }

    private String markTaskAsDone(int index) {
        String response = "\tNice! I've marked this task as done:\n";
        if ((index <= 0) || (index > tasks.getNumberOfTasks())) {
            return dukeNeedsValidInput;
        } else {
            index--;
            Task task = tasks.getTaskAt(index);
            task.markAsDone();
            response = response
                    + "\t\t"
                    + taskTypeLabel(task)
                    + taskCompletionStatus(task.isTaskDone())
                    + task.getTaskName()
                    + getTimeOrDate(task);
            updateFile();
            return response;
        }
    }

    private void updateFile() {
        try {
            storage.updateTasks(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addTodo(String userInput) {
        if (userInput.equals(badFormat)) {
            return dukeNeedsValidInput;
        } else {
            ToDo newTask = new ToDo(userInput);
            return addToList(newTask);
        }
    }

    private String addDeadline(String[] userInput) {
        if (userInput.length <= 1) {
            return dukeNeedsValidInput;
        } else {
            String taskName = userInput[0];
            String taskDeadline = userInput[1];

            Deadline newTask = new Deadline(taskName, taskDeadline);
            return addToList(newTask);
        }
    }

    private String addEvent(String[] userInput) {
        if (userInput.length <= 1) {
            return dukeNeedsValidInput;
        } else {
            String taskName = userInput[0];
            String taskTime = userInput[1];

            Event newTask = new Event(taskName, taskTime);
            return addToList(newTask);
        }
    }

    private String addToList(Task task) {
        if (tasks.getNumberOfTasks() >= 100) {
            return "\tYou can't add any more tasks to your list!";
        }
        else {
            tasks.addTask(task);
            updateFile();

            String output = "\tGot it. I've added this task:"
                    + "\n\t\t"
                    + taskTypeLabel(task)
                    + taskCompletionStatus(task.isTaskDone())
                    + task.getTaskName()
                    + getTimeOrDate(task)
                    + "\n\tNow you have " + tasks.getNumberOfTasks() + " task(s) in the list.";
            return output;
        }
    }

    /* FOR FORMATTING */

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

    static String getTimeOrDate(Task task) {
        if (task.getHasDate()) {
            return task.getFullDate();
        } else {
            return task.getTime();
        }
    }

    static String taskCompletionStatus(Boolean isDone) {
        if (isDone) { return "[✓] ";}
        else { return "[✗] "; }
    }
}
