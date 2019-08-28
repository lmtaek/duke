public abstract class Task {

    enum TaskType {
        TODO, DEADLINE, EVENT;
    }

    private String taskName;
    private Boolean isDone = false;
    private String time;

    public TaskType taskType;

    public Task(String taskName, String taskTime) {
        this.taskName = taskName;
        this.time = taskTime;
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

    public Boolean isTaskDone() {
        return isDone;
    }

    public void markAsDone() {
        if (this.isDone != true) {
            this.isDone = true;
        }
    }
}

