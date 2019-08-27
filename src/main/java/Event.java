public class Event extends Task {

    private String startTime;
    private String endTime;

    public Event(String taskName) {
        super(taskName);
    }

    public String getTimeSpan() {
        return "at: " + startTime + "-" + endTime;
    }
}
