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

}
