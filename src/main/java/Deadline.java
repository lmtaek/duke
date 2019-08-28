public class Deadline extends Task {

    private String time;

    public Deadline(String taskName, String deadline) {
        super(taskName);
        this.time = deadline;
        this.taskType = TaskType.DEADLINE;
    }
}
