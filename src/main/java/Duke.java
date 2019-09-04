import TaskPackage.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {

    private static String dukeGreeting = "Hello, I'm Duke.\nWhat can I do to help you?";
    private static String dukeNeedsValidInput = "\tI'm not sure that I understand. Sorry.";
    private static ArrayList<Task> taskArrayList = new ArrayList<Task>();
    private static int numberOfTasks = 0;


    public static void main(String[] args) {

        //When Duke starts up, it will check to see if a list has already been made.
        try {
            readFile();
        } catch (IOException e) {
            System.out.println("I was unable to read the saved data file.");
        }

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
            } else if (userInput.toLowerCase().contains("find")) {
                System.out.println(findTask(userInput));
            } else if (userInput.toLowerCase().contains("done")){
                System.out.println(markCompletedRequest(userInput));
            } else if (userInput.toLowerCase().contains("todo")) {
                System.out.println(addTodo(userInput));
            } else if (userInput.toLowerCase().contains("deadline")) {
                System.out.println(addDeadline(userInput));
            } else if (userInput.toLowerCase().contains("event")) {
                System.out.println(addEvent(userInput));
            } else if (userInput.toLowerCase().contains("delete")) {
                System.out.println(deleteTask(userInput));
            }
            else {
                System.out.println(dukeNeedsValidInput);
            }
            continue;
        }

    }

    static void closeApplication() {
        System.out.println("\tBye. Hope to see you again soon!\n");
        System.exit(0);
    }

    static Boolean writeInFile(String savedText) throws IOException {
        try {
            FileWriter taskFile = new FileWriter("./data/duke.txt");
            taskFile.write(savedText);
            taskFile.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    static Boolean readFile() throws IOException {
        try {
            BufferedReader taskFile = new BufferedReader(new FileReader("./data/duke.txt"));
            String currentLine = taskFile.readLine();
            if (currentLine != null) {
                int i = 0;
                while (currentLine != null) {
                    String[] parsedCurrentLine = currentLine.split("//");
                    if (parsedCurrentLine.length < 3) {
                        return false;
                    }
                    if (parsedCurrentLine[0].equals(Task.TaskType.TODO.toString())) {
                        ToDo readTask = new ToDo(parsedCurrentLine[2]);
                        if (parsedCurrentLine[1].equals("true")) {
                            readTask.markAsDone();
                        }
                        addToList(readTask, i);
                    } else if (parsedCurrentLine[0].equals(Task.TaskType.DEADLINE.toString())) {
                        Deadline readTask = new Deadline(parsedCurrentLine[2], parsedCurrentLine[3]);
                        if (parsedCurrentLine[1].equals("true")) {
                            readTask.markAsDone();
                        }
                        addToList(readTask, i);
                    } else if (parsedCurrentLine[0].equals(Task.TaskType.EVENT.toString())) {
                        Event readTask = new Event(parsedCurrentLine[2], parsedCurrentLine[3]);
                        if (parsedCurrentLine[1].equals("true")) {
                            readTask.markAsDone();
                        }
                        addToList(readTask, i);
                    }
                    currentLine = taskFile.readLine();
                    i++;
                }
            }
            taskFile.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    static String formatFileText() {
        String textToWrite = "";
        for (int i = 0; i < numberOfTasks; i++) {
            textToWrite = textToWrite
            + taskArrayList.get(i).taskType
            + "//"
            + taskArrayList.get(i).isTaskDone()
            + "//"
            + taskArrayList.get(i).getTaskName();

            if (taskArrayList.get(i).taskType.equals(Task.TaskType.DEADLINE)
            || taskArrayList.get(i).taskType.equals(Task.TaskType.EVENT)) {
                textToWrite = textToWrite
                        + "//"
                        + taskArrayList.get(i).getBasicTime()
                        + "\n";
            } else {
                textToWrite = textToWrite + "\n";
            }
        }
        return textToWrite;
    }

    static String deleteTask(String userInput) {
        String response = "\tNoted. I've removed this task:\n";
        int index = -1;
        String parseProblem = "\tI didn't catch that. Can you reformat your command?";
        String indexNotFound = "\tI couldn't find your task.";

        userInput = userInput.trim();
        if (!userInput.contains("delete ")) {
            return parseProblem;
        }
        String task = userInput.replaceFirst("delete ", "").trim();
        if (task.isBlank() || task.isEmpty()) {
            return parseProblem;
        }
        try {
            index = Integer.parseInt(task);
        } catch (NumberFormatException e) {
            return parseProblem;
        }

        if ((index <= 0) || index > numberOfTasks) {
            return indexNotFound;
        } else {
            index--;
            numberOfTasks--;
            response = response
                    + "\t\t"
                    + (index+1)
                    + ". "
                    + taskTypeLabel(taskArrayList.get(index))
                    + taskCompletionStatus(taskArrayList.get(index).isTaskDone())
                    + taskArrayList.get(index).getTaskName()
                    + getTimeOrDate(taskArrayList.get(index))
                    + "\n"
                    + "\tNow you have " + numberOfTasks + " task(s) in the list.";
        }
        taskArrayList.remove(index);
        try {
            writeInFile(formatFileText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    static String readTaskList() {
        String response = "\tHere are the tasks in your list:\n";

        for (int i = 0; i< numberOfTasks; i++) {

            response = response
                    + "\t\t"
                    + (i+1)
                    + ". "
                    + taskTypeLabel(taskArrayList.get(i))
                    + taskCompletionStatus(taskArrayList.get(i).isTaskDone())
                    + taskArrayList.get(i).getTaskName()
                    + getTimeOrDate(taskArrayList.get(i))
                    + "\n";
        }
        return response;
    }

    static String findTask(String userInput) {
        String response = "\n\t Here are the matching tasks in your list:\n";
        String badFormat = "\n\t I don't understand. Can you reformat your request?";
        userInput = userInput.trim();

        if (!userInput.contains("find ")) {
            return badFormat;
        } else {
            userInput = userInput.replaceFirst("find ", "").toLowerCase().trim();
        }
        if (userInput.isEmpty() || userInput.isBlank()) {
            return badFormat;
        }

        for (int i = 0; i < numberOfTasks; i++) {
            if (taskArrayList.get(i).getTaskName().toLowerCase().contains(userInput)) {
                response = response
                        +"\t"
                        + (i+1)
                        + ". "
                        + taskTypeLabel(taskArrayList.get(i))
                        + taskCompletionStatus(taskArrayList.get(i).isTaskDone())
                        + taskArrayList.get(i).getTaskName()
                        + getTimeOrDate(taskArrayList.get(i))
                        + "\n";
            }
            else {
                continue;
            }
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

    static String getTimeOrDate(Task task) {
        if (task.getHasDate()) {
            return task.getFullDate();
        } else {
            return task.getTime();
        }
    }

    static String addTodo(String userInput) {
        String specifyTask = "\tYou need to specify what the task is!";
        String parseProblem = "\tCan you reformat your command?";
        userInput = userInput.trim();
        if (!userInput.contains("todo ")) {
            return parseProblem;
        }
        String taskName = userInput.replace("todo ", "").trim();
        if (taskName.equals("") || taskName.isBlank()) {
            return specifyTask;
        }
        ToDo newTask = new ToDo(taskName);
        return addToList(newTask);
    }

    static String addDeadline(String userInput) {
        String parseProblem = "\tI didn't catch that. Can you reformat your command?";
        String needMoreDetails = "\tPlease provide more details for your deadline.";
        userInput = userInput.trim();
        if (!userInput.contains("deadline ")) {
            return parseProblem;
        }
        String task = userInput.replaceFirst("deadline ", "").trim();
        String[] taskComponents = task.split(" /by ");

        if (taskComponents.length <= 1) {
            return parseProblem;
        }

        String taskName = taskComponents[0].trim();
        String taskDeadline = taskComponents[1].trim();
        if ((taskName.isEmpty() || taskName.isBlank())
        || (taskDeadline.isEmpty() || taskDeadline.isBlank())) {
            return needMoreDetails;
        }

        Deadline newTask = new Deadline(taskName, taskDeadline);
        return addToList(newTask);
    }

    static String addEvent(String userInput) {
        String parseProblem = "\tI didn't catch that. Can you reformat your command?";
        String needMoreDetails = "\tPlease provide more details for your event.";
        userInput = userInput.trim();
        if (!userInput.contains("event ")) {
            return parseProblem;
        }
        String task = userInput.replaceFirst("event ", "").trim();
        String[] taskComponents = task.split(" /at ");

        if (taskComponents.length <= 1) {
            return parseProblem;
        }

        String taskName = taskComponents[0].trim();
        String taskTime = taskComponents[1].trim();
        if ((taskName.isEmpty() || taskName.isBlank())
                || (taskTime.isEmpty() || taskTime.isBlank())) {
            return needMoreDetails;
        }

        Event newTask = new Event(taskName, taskTime);
        return addToList(newTask);
    }

    static String addToList(Task task) {
        if (numberOfTasks >= 100) {
            return "\tYou can't add any more tasks to your list!";
        }
        else {
            taskArrayList.add(task);
            numberOfTasks++;
            try {
                writeInFile(formatFileText());
            } catch (IOException e) {
                e.printStackTrace();
            }

            String output = "\tGot it. I've added this task: "
                    + "\n\t\t"
                    + taskTypeLabel(task)
                    + taskCompletionStatus(task.isTaskDone())
                    + task.getTaskName()
                    + getTimeOrDate(task)
                    + "\n\tNow you have " + numberOfTasks + " task(s) in the list.";
            return output;
        }
    }

    static void addToList(Task task, int arrayIndex) {
        taskArrayList.add(task);
        numberOfTasks++;
    }

    static String taskCompletionStatus(Boolean isDone) {
        if (isDone) { return "[✓] ";}
        else { return "[✗] "; }
    }

    static String markCompletedRequest(String input) {
        String couldNotFindTask = "\tI couldn't find the task.";
        String parseProblem = "\tCan you put a space in your command?";
        input = input.trim();
        String parsedInput[] = input.split(" ");
        if ((parsedInput.length <= 1)) {
            return parseProblem;
        } else {
            try {
                Integer index = Integer.parseInt(parsedInput[1]);
                if (index <= 0
                        || index == null
                        || index > numberOfTasks) {
                    return couldNotFindTask;
                }
                    index--;
                    return markTaskAsDone(taskArrayList.get(index));
                } catch (NumberFormatException | NullPointerException nfe) {
                    return couldNotFindTask;
            }
        }
    }

    static String markTaskAsDone(Task task) {
        task.markAsDone();
        try {
            writeInFile(formatFileText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "\tNice! I've marked this task as done:\n"
                + "\t\t"
                + taskTypeLabel(task)
                + taskCompletionStatus(task.isTaskDone())
                + task.getTaskName()
                + getTimeOrDate(task);
    }
}
