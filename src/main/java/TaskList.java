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
     * @param savedList ArrayList used to organize Tasks in TaskList. It may contain prior Tasks from
     *                  earlier sessions.
     */
    public TaskList(ArrayList<Task> savedList) {
        this.taskList = savedList;
    }

    /**
     * Returns the encapsulated ArrayList.
     * @return the ArrayList that was stored in the TaskList class.
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Will locate and provide the Task at a certain index in the TaskList's ArrayList.
     * @param index used in order to locate the desired Task.
     * @return the Task at the index.
     */
    public Task getTaskAt(int index) {
        return taskList.get(index);
    }

    /**
     * Will return the number of Tasks in the stored ArrayList.
     * @return the number of stored Tasks for the session.
     */
    public int getNumberOfTasks() {
        return taskList.size();
    }

    /**
     * Will insert a new Task into the stored ArrayList.
     * @param task the Task being inserted into the ArrayList.
     */
    public void addTask(Task task) {
        taskList.add(task);
    }

    /**
     * Will remove and return the requested Task from the stored ArrayList.
     * @param index used to locate the desired Task.
     * @return the removed Task.
     */
    public Task removeTaskAt(int index) {
        return taskList.remove(index);
    }

}
