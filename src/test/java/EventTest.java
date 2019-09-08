import TaskPackage.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    private String taskName = "Group project meeting";
    private String taskBasicTime = "some time this afternoon";
    private String taskFormattableTime = "11/8/2019 12:11PM";

    @Test
    public void testEventGetTaskName() {
        Event testEvent = new Event(taskName, taskBasicTime);
        assertEquals(taskName, testEvent.getTaskName());
    }

    @Test
    public void testGetEventUnformattedTime() {
        Event testEvent = new Event(taskName, taskBasicTime);
        assertEquals(false, testEvent.getHasDate());
        assertEquals(" (at: " + taskBasicTime + ")", testEvent.getTime());
    }

    @Test
    public void testGetEventFormattedTime() {
        Event testEvent = new Event(taskName, taskFormattableTime);
        assertEquals(true, testEvent.getHasDate());
        assertEquals(" (at: August 11, 2019 12:11PM)", testEvent.getFullDate());
    }

    @Test
    public void testMarkEventAsDone() {
        Event testEvent = new Event(taskName, taskBasicTime);
        assertEquals(false, testEvent.isTaskDone());
        testEvent.markAsDone();
        assertEquals(true, testEvent.isTaskDone());
    }
}
