package TaskPackage;
/**
 * Child of Task class that does not require a time in order to create the Object.
 * All the ToDo class requires is a taskName.
 */
public class ToDo extends Task {

    public ToDo(String taskName) {
        super(taskName, "No time specified.");
        this.taskType = TaskType.TODO;
    }

    @Override
    public String getTime() {
        return "";
    }

}
