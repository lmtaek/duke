import TaskPackage.Task;

import java.util.ArrayList;

/**
 * A class for the storage of the user's current list of Tasks. As the user puts in more
 * commands affecting the list of Tasks, the TaskList designated to the session will update.
 */
public class TaskList {

    private ArrayList<Task> taskList;

    /**
     * The constructor method for the TaskList class. Upon being created, the TaskList will attempt
     * to use the saved data from Storage, though if it cannot find a prior list, it will
     * use a new, empty ArrayList.
     * @param savedList The ArrayList used to organize Tasks in TaskList. It may contain prior Tasks from
     *                  earlier sessions.
     */
    public TaskList(ArrayList<Task> savedList) {
        this.taskList = savedList;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public Task getTaskAt(int index) {
        return taskList.get(index);
    }

    public int getNumberOfTasks() {
        return taskList.size();
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public Task removeTaskAt(int index) {
        return taskList.remove(index);
    }

}
