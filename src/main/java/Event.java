public class Event extends Task {

    private String time;

    public Event(String taskName, String eventTime) {
        super(taskName, eventTime);
        this.taskType = TaskType.EVENT;
    }

}
