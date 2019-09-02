package TaskPackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Task {

    public enum TaskType {
        TODO, DEADLINE, EVENT;
    }

    private String taskName;
    private Boolean isDone = false;
    private String time;
    private String dateInputPattern = "d/M/yyyy HHmm";
    private String dateOutputPattern = "MMMM d, yyyy h:mma";
    private LocalDateTime date;
    private Boolean hasDate = false;

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
            //System.out.println("Time formatting does not align with Date formatting.");
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

