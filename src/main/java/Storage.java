import TaskPackage.*;

import java.io.*;
import java.util.ArrayList;

/**
 * A class meant to save the user's ongoing list by writing the data onto a file. When a new session begins,
 * the Storage class will attempt to read the referenced file to re-create the list from prior sessions. If it
 * cannot read the file, it will write a new file. As the user adds/deletes/completes tasks, the Storage class
 * will update its file.
 */
public class Storage {

    private static TaskList tasks;
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * A method used when a new Duke session begins. The Storage class will try to read the referenced
     * file to recreate the list from the earlier session, or it will create a new file to record the
     * new data.
     * @return It returns an ArrayList with all the tasks from prior sessions' task lists, or an empty
     * ArrayList if no earlier data is found.
     * @throws IOException When Storage is unable to locate/read the file.
     */
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

    /**
     * Standardizes the Task List and makes it more easily parsed when the file is being referenced in the future.
     * @return The returned String is printed into the save file.
     */
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
