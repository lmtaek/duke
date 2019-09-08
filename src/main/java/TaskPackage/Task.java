package TaskPackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * An abstract class--parent to ToDo, Deadline, and Event classes. Users can create
 * a Task to add to their session's TaskList. The constructor takes a taskName at minimum, though
 * certain subclasses require a designated time as well.
 */
public abstract class Task {

    public enum TaskType { TODO, DEADLINE, EVENT;}

    private String taskName;
    private Boolean isDone = false;
    private String time;

    private String dateInputPattern = "d/M/yyyy HHmm";
    private String dateInputPattern2 = "d/M/yyyy h:mma";
    private String dateOutputPattern = "MMMM d, yyyy h:mma";
    private LocalDateTime date;
    private Boolean hasDate = false;

    public TaskType taskType;

    public Task(String taskName, String taskTime) {
        this.taskName = taskName;
        this.time = taskTime;
        convertTimeToDate();
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTime() {
        return time;
    }

    /**
     * Method with the same functionality as getTime, though it has not been overridden by subclasses.
     * Used mainly when saving a Task's time to a file.
     * @return Returns the time without additional strings added in the overridden getTime()
     * methods of subclasses.
     */
    public String getBasicTime() { //Needed a function that wasn't overridden by subclasses.
        return time;
    }

    public Boolean getHasDate() {
        return hasDate;
    }

    public void convertTimeToDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateInputPattern);
            LocalDateTime reformattedDate = LocalDateTime.parse(this.time, formatter);
            this.date = reformattedDate;
            this.hasDate = true;
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateInputPattern2);
                LocalDateTime reformattedDate2 = LocalDateTime.parse(this.time, formatter);
                this.date = reformattedDate2;
                this.hasDate = true;
            } catch (DateTimeParseException e2) {
                //No date matched, thus we will not give the field a value.
            }
        }

    }

    public String getFullDate() {
        if (this.date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateOutputPattern);
        String fullDate = this.date.format(formatter);
        return fullDate;
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

