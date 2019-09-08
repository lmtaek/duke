import TaskPackage.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    public ArrayList<Task> makeArrayList() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new ToDo("take a hike"));
        tasks.add(new Event("Celebrate friend's birthday", "09/15/1998 3:45PM"));
        tasks.add(new ToDo("Nap"));
        tasks.add(new Deadline("FINISH PAPER", "midnight"));
        return tasks;
    }

    @Test
    public void testCreateList() {
        ArrayList<Task> tasks = makeArrayList();
        TaskList testTaskList = new TaskList(tasks);

        assertEquals(tasks.size(), testTaskList.getNumberOfTasks());

        for (int i=0; i<tasks.size(); i++) {
            assertEquals(tasks.get(i).getTaskName(), testTaskList.getTaskAt(i).getTaskName());
        }
    }

    @Test
    public void testRemoveTask() {
        ArrayList<Task> tasks = makeArrayList();
        TaskList testTaskList = new TaskList(tasks);

        int taskListSize = testTaskList.getNumberOfTasks();

        testTaskList.removeTaskAt(0);

        assertEquals(taskListSize-1, testTaskList.getNumberOfTasks());

    }
}
