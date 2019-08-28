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
        if (!super.getFullDate().equals("") || !(super.getFullDate() == null)) {
            return " (at: " + super.getFullDate() + ")";
        }
        return "";
    }
}
