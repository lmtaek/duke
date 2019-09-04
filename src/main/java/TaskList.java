import TaskPackage.Task;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> taskList;

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
