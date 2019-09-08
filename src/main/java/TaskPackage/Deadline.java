package TaskPackage;

/**
 * Child of Task class, different in how the user must specify a '/by' time in order to
 * successfully create this task. If the time is formatted in a specific way, the time
 * will be converted to a formatted 'Date'.
 */
public class Deadline extends Task {

    public Deadline(String taskName, String deadline) {
        super(taskName, deadline);
        this.taskType = TaskType.DEADLINE;
        convertTimeToDate();
    }

    @Override
    public String getTime() {
        return " (by: " + super.getTime() + ")";
    }

    @Override
    public String getFullDate() {
        if (this.getHasDate()) {
            return " (by: " + super.getFullDate() + ")";
        }
        return "";
    }
}
