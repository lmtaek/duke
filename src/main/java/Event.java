public class Event extends Task {

    private String time;

    public Event(String taskName, String eventTime) {
        super(taskName);
        this.time = eventTime;
        this.taskType = TaskType.EVENT;
    }

}
