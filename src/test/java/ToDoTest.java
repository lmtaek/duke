import TaskPackage.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {

    private String taskName = "wash car";

    @Test
    public void testGetTaskName() {
        ToDo testToDo = new ToDo(taskName);
        assertEquals(testToDo.getTaskName(), taskName);
    }

    @Test
    public void testGetTime() {
        ToDo testToDo = new ToDo(taskName);
        assertEquals(testToDo.getTime(), "");
    }

    @Test
    public void testIsDone() {
        ToDo testToDo = new ToDo(taskName);
        assertEquals(testToDo.isTaskDone(), false);

        testToDo.markAsDone();
        assertEquals(testToDo.isTaskDone(), true);
    }
}
