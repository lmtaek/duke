import TaskPackage.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {

    private TaskList tasks;

    private String filePath = "./duke.txt";
    private ToDo testToDo = new ToDo("go for a jog");

    @Test
    public void testLoadFile() {
        Storage testStorage = new Storage(filePath);
        try {
            testStorage.loadFile();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testUpdateFile() throws IOException {
        Storage testStorage = new Storage(filePath);
        this.tasks = new TaskList(new ArrayList<Task>());
        tasks.addTask(testToDo);
        try {
            testStorage.updateTasks(tasks);
        } catch (IOException e) { fail(); }

        try {
            ArrayList<Task> readList = testStorage.loadFile();
            assertEquals(tasks.getNumberOfTasks(), readList.size());
            assertEquals(tasks.getTaskAt(0).getTaskName(), readList.get(0).getTaskName());
        } catch (IOException e2) { fail(); }
    }
}
