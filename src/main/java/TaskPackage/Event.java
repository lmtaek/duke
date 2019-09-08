package TaskPackage;

/**
 * Child of Task class, different in how the user must specify an '/at' time in order to
 * successfully create this task. If the time is formatted in a specific way, the time
 * will be converted to a formatted 'Date'.
 */
public class Event extends Task {

    private String time;

    public Event(String taskName, String eventTime) {
        super(taskName, eventTime);
        this.taskType = TaskType.EVENT;
        convertTimeToDate();
    }

    @Override
    public String getTime() {
        return " (at: " + super.getTime() + ")";
    }

    @Override
    public String getFullDate() {
        if (this.getHasDate()) {
            return " (at: " + super.getFullDate() + ")";
        }
        return "";
    }
}
