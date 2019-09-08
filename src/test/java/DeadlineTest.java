import TaskPackage.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    private String taskName = "Finish writing paper";
    private String taskBasicTime = "midnight";
    private String taskFormattableTime = "8/9/2019 2359";

    @Test
    public void testGetDeadlineName() {
        Deadline testDeadline = new Deadline(taskName, taskBasicTime);
        assertEquals(taskName, testDeadline.getTaskName());
    }

    @Test
    public void testGetDeadlineUnformattedTime() {
        Deadline testDeadline = new Deadline(taskName, taskBasicTime);
        assertEquals(false, testDeadline.getHasDate());
        assertEquals(" (by: " + taskBasicTime + ")", testDeadline.getTime());
    }

    @Test
    public void testGetDeadlineFormattedTime() {
        Deadline testDeadline = new Deadline(taskName, taskFormattableTime);
        assertEquals(true, testDeadline.getHasDate());
        assertEquals(" (by: September 8, 2019 11:59PM)", testDeadline.getFullDate());
    }

    @Test
    public void testMarkDeadlineAsDone() {
        Deadline testDeadline = new Deadline(taskName, taskBasicTime);
        assertEquals(false, testDeadline.isTaskDone());
        testDeadline.markAsDone();
        assertEquals(true, testDeadline.isTaskDone());
    }
}
