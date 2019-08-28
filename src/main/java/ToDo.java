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
