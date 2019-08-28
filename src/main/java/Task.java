import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Task {

    enum TaskType {
        TODO, DEADLINE, EVENT;
    }

    private String taskName;
    private Boolean isDone = false;
    private String time;
    private String datePattern = "d/MMM/yyyy";
    private LocalDateTime date;

    public TaskType taskType;

    public Task(String taskName, String taskTime) {
        this.taskName = taskName;
        this.time = taskTime;
        if (taskTime.matches("datePattern")) {
            convertTimeToDate();
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTime() {
        return time;
    }

    public String getBasicTime() { //Needed a function that wasn't overridden by subclasses.
        return time;
    }

    /*
    public Boolean isTimeStringValid(String timeString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(timeString);
        } catch (ParseException e) {
            System.out.println("Didn't work, chief.");
            System.out.println(timeString);
            return false;
        }
        return true;
    }
     */

    public void convertTimeToDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
            LocalDateTime reformattedDate = LocalDateTime.parse(this.time, formatter);

            System.out.println(reformattedDate);

            this.date = reformattedDate;
        } catch (DateTimeParseException e) {
            System.out.println("Time can't be converted to date.");
        }

    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public Boolean isTaskDone() {
        return isDone;
    }

    public void markAsDone() {
        if (this.isDone != true) {
            this.isDone = true;
        }
    }
}

