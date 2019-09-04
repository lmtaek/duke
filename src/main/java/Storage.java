import TaskPackage.Deadline;
import TaskPackage.Event;
import TaskPackage.Task;
import TaskPackage.ToDo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {

    private static TaskList tasks;

    public Storage() {
    }

    public ArrayList<Task> loadFile() throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            BufferedReader taskFile = new BufferedReader(new FileReader("./data/duke.txt"));
            String currentLine = taskFile.readLine();

            if (currentLine != null) {
                while (currentLine != null) {
                    String[] parsedCurrentLine = currentLine.split("//");
                    if (parsedCurrentLine.length < 3) {
                        currentLine = taskFile.readLine();
                        continue;
                    }
                    if (parsedCurrentLine[0].equals(Task.TaskType.TODO.toString())) {
                        ToDo readTask = new ToDo(parsedCurrentLine[2]);
                        if (parsedCurrentLine[1].equals("true")) {
                            readTask.markAsDone();
                        }
                        tasks.add(readTask);
                    } else if (parsedCurrentLine[0].equals(Task.TaskType.DEADLINE.toString())) {
                        Deadline readTask = new Deadline(parsedCurrentLine[2], parsedCurrentLine[3]);
                        if (parsedCurrentLine[1].equals("true")) {
                            readTask.markAsDone();
                        }
                        tasks.add(readTask);
                    } else if (parsedCurrentLine[0].equals(Task.TaskType.EVENT.toString())) {
                        Event readTask = new Event(parsedCurrentLine[2], parsedCurrentLine[3]);
                        if (parsedCurrentLine[1].equals("true")) {
                            readTask.markAsDone();
                        }
                        tasks.add(readTask);
                    }
                    currentLine = taskFile.readLine();
                }
            }
            taskFile.close();
        } catch (Exception e) {
            System.out.println(e);
            return tasks;
        }
        return tasks;
    }

    public void updateTasks(TaskList tasks) throws IOException {
        this.tasks = tasks;
        writeInFile(formatFileText());
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


    static String formatFileText() {
        String textToWrite = "";
        for (int i = 0; i < tasks.getNumberOfTasks(); i++) {
            textToWrite = textToWrite
                    + tasks.getTaskAt(i).taskType
                    + "//"
                    + tasks.getTaskAt(i).isTaskDone()
                    + "//"
                    + tasks.getTaskAt(i).getTaskName();

            if (tasks.getTaskAt(i).taskType.equals(Task.TaskType.DEADLINE)
                    || tasks.getTaskAt(i).taskType.equals(Task.TaskType.EVENT)) {
                textToWrite = textToWrite
                        + "//"
                        + tasks.getTaskAt(i).getBasicTime()
                        + "\n";
            } else {
                textToWrite = textToWrite + "\n";
            }
        }
        return textToWrite;
    }
}
