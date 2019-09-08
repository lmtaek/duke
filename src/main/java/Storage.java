import TaskPackage.*;

import java.io.*;
import java.util.ArrayList;

public class Storage {

    private static TaskList tasks;
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> loadFile() throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            BufferedReader taskFile = new BufferedReader(new FileReader(filePath));
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
            //System.out.println(e);
            System.out.println("File could not be found. Starting a new file and list...");
            File newFile = new File(filePath);
            return tasks;
        }
        return tasks;
    }

    public void updateTasks(TaskList tasks) throws IOException {
        this.tasks = tasks;
        writeInFile(formatFileText());
    }

    private Boolean writeInFile(String savedText) throws IOException {
        try {
            File file = new File(filePath);
            FileWriter taskFile = new FileWriter(file);
            taskFile.write(savedText);
            taskFile.close();
        } catch (Exception e) {
            System.out.println("I couldn't save your information to the file.");
            return false;
        }
        return true;
    }

    private String formatFileText() {
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
